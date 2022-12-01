import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { ToastController } from '@ionic/angular';
import { Observable } from 'rxjs';
import { first, tap } from 'rxjs/operators';
import { NewAccountI } from '../models/account.model';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private toastCtrl: ToastController, private storage: StorageService, private http: HttpClient, private router: Router) {}

  saveAccount(email, username, password): Observable<NewAccountI> {
    const body: NewAccountI = { email, user: username, pswd: password };
    return this.http.post<NewAccountI>('/public/account', body).pipe(
      tap(() => {}, error => this.handleError(error))
    );
  }

  async handleError(httpError: HttpErrorResponse) {
    let toastProps: any = { duration: 2000, position: 'top', message: 'Ocurrió un error inesperado', color: 'warning', buttons: [
      {
        icon: 'close-outline',
        role: 'cancel',
      }
    ], };
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
            this.router.navigateByUrl('/');
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
            toastProps = { ...toastProps,  message: 'Error desconocido' }
            break;
        }
      }
    }
    // this.authService.toast.create({ ...toastProps }).then(toast => toast.present().then(() => console.log('SHOW')).catch(() => console.log('ERROR')));
    // Return an observable with a user-facing error message.
    // return throwError(() => new Error('Something bad happened; please try again later.'));
    const toast = await this.toastCtrl.create({ ...toastProps });
    toast.present();
  }

}
