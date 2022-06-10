import { HttpErrorResponse, HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { from, Observable, throwError } from "rxjs";
import { AuthService } from "../services/auth.service";
import { API_URL } from "src/environments/environment";
import { catchError, switchMap } from "rxjs/operators";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (this.isProtectedUrl(request.url)) {
      from(this.authService.getAccessToken()).pipe(
        switchMap((accessToken) => {
          return next.handle(
            request.clone({
              url: `${API_URL}${request.url}`,
              headers: new HttpHeaders({
                Authorization: `Bearer ${accessToken}`,
                'Content-Type': 'application/json',
              }),
            })
          ).pipe(catchError(this.handleError));
        })
      );
    }
    return next.handle(request.clone({
      url: `${API_URL}${request.url}`,
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    })).pipe(catchError(this.handleError));
  }

  handleError(httpError: HttpErrorResponse) {
    let toastProps: any = { duartion: 2000, position: 'top', message: 'Ocurrió un error inesperado', color: 'warning' };
    if (httpError.status === 0) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', httpError.error);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.log({ httpError });
      if (httpError instanceof HttpErrorResponse) {
        switch (httpError.status) {
          case 400:
            if (httpError.error.error === 'invalid_grant') {
              toastProps = {
                ...toastProps,
                message: httpError.error.error_description,
                color: 'danger'
              };
            } else {
              toastProps = {
                ...toastProps,
                message: 'Ocurrió un error con la petición',
                color: 'danger'
              };
            }
            break;

          case 401:
            toastProps = {
              ...toastProps,
              message: 'Tu sesión ha expirado',
              color: 'danger'
            };
            break;
          case 409:
            toastProps = {
              ...toastProps,
              header: httpError.error.code,
              message: httpError.error.message,
              color: 'danger'
            };
            break;
          default:
            return throwError(httpError);
            break;
        }
      }
    }
    // this.authService.toast.create({ ...toastProps }).then(toast => toast.present().then(() => console.log('SHOW')).catch(() => console.log('ERROR')));
    // Return an observable with a user-facing error message.
    // return throwError(() => new Error('Something bad happened; please try again later.'));
    return throwError(httpError);
  }

  private isProtectedUrl(url: string): Boolean {
    return !(['/oauth/token', '/public/account'].includes(url))
  }

}
