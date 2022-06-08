import { OauthSessionI } from './../models/oauth.interface';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';

import { StorageService } from './storage.service';

const SESSION_KEY = "auth_session";

@Injectable({
  providedIn: 'root'
})
export class AuthService { // TODO: (LOGIN) [invalid_grant] show message
  isAuthenticated: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);
  currentSession: OauthSessionI = null;

  constructor(private storage: StorageService, private http: HttpClient, private router: Router) {
    this.loadSession();
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
  getAccessToken(): string {
    if (this.sessionIsValid(this.currentSession)) return this.currentSession.access_token;
    else {
      const accessToken = this.refreshToken();
      return accessToken;
    }
  }

  // Refresh access token
  refreshToken(): string {
    // Api RefreshToken
    // Save new session
    // Or logout
    return null;
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
    return this.storage.saveObject(SESSION_KEY, session);
  }

}