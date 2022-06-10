import { OauthSessionI } from './../models/oauth.interface';
import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, throwError } from 'rxjs';

import { StorageService } from './storage.service';

import { AUTH_CLIENT, AUTH_PSWD } from '../../environments/environment';
import { ToastController } from '@ionic/angular';
import { switchMap } from 'rxjs/operators';

const SESSION_KEY = "auth_session";

@Injectable({
  providedIn: 'root'
})
export class AuthService { // TODO: (LOGIN) [invalid_grant] show message
  isAuthenticated: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);
  currentSession: OauthSessionI = null;

  constructor(private toastCtrl: ToastController, private storage: StorageService, private http: HttpClient, private router: Router) {
    this.loadSession();
  }

  // Login
  login(username: string, password: string): Observable<HttpResponse<OauthSessionI>> {
    const request = this.http.post<OauthSessionI>(
      '/oauth/token',
      null, {
      params: {
        client_id: AUTH_CLIENT,
        client_secret: AUTH_PSWD,
        grant_type: "password",
        username, password,
      },
      observe: 'response'
    })

    request.subscribe({
      next: res => this.saveNewSession(res.body),
      error: async error => this.handleError(error)
    });

    return request;
  }

  // Load session on startup
  async loadSession() {
    const session: OauthSessionI = await this.storage.getObject(SESSION_KEY);
    if (this.sessionIsValid(session)) {
      this.isAuthenticated.next(true);
      this.currentSession = session;
    } else this.isAuthenticated.next(false);
  }

  // Consult or refresh access token
  async getAccessToken(): Promise<string> {
    if (this.sessionIsValid(this.currentSession)) return this.currentSession.access_token;
    else return this.refreshToken(this.currentSession.refresh_token).toPromise();
  }

  // Refresh access token
  refreshToken(refreshToken: string): Observable<string> {
    const response = this.http.post<OauthSessionI>(
      '/oauth/token',
      null, {
      params: {
        client_id: AUTH_CLIENT,
        client_secret: AUTH_PSWD,
        grant_type: "refresh_token",
        refresh_token: refreshToken,
      },
      observe: 'response'
    });

    response.subscribe({
      next: res => this.saveNewSession(res.body),
      error: async err => this.handleError(err)
    });

    return response.pipe(switchMap(res => res.body.access_token));
  }

  // Check session validity
  sessionIsValid(session: OauthSessionI): boolean {
    if (session != null && session.access_token && session.refresh_token && session.expires_in) {
      const current = new Date().getTime();
      return session.expires_in > current;
    } else return false;
  }

  // Save session
  saveNewSession(session: OauthSessionI) {
    this.currentSession = session;
    this.isAuthenticated.next(true);
    return this.storage.saveObject(SESSION_KEY, session);
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
