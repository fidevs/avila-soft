import { AuthService } from 'src/app/services/auth.service';
import { Injectable } from '@angular/core';
import { CanLoad, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map, take } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserLoggedGuard implements CanLoad {

  constructor(private authService: AuthService, private router: Router) {}

  canLoad(): Observable<boolean> {
    return this.authService.isAuthenticated.pipe(
      filter(val => val !== null),
      take(1),
      map(isAuthenticated => {
        if (!isAuthenticated) {
          this.router.navigateByUrl('');
          return false;
        }
        return true;
      })
    )
  }
}