import { Component } from '@angular/core';
import { AuthenticationService } from './modules/authentication/authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'MovieCruiserFrontend';

  constructor(private authService: AuthenticationService, private router: Router) { }

  logout() {
    this.authService.deleteToken();
    this.router.navigate(['/login']);
  }

}
