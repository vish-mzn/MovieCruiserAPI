import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthenticationService } from './modules/authentication/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  constructor(private authService: AuthenticationService, private router: Router) { }

  canActivate() {
    if(!this.authService.isTokenExpired()) {
      console.log(("token not expired"));
      
      return true;
    }
    this.router.navigate(['/login']);
    return false;
  }

}
