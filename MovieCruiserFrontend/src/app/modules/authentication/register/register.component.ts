import { Component, OnInit } from '@angular/core';
import { User } from '../User';
import { AuthenticationService } from '../authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  newUser: User;

  constructor(private authService: AuthenticationService, private router: Router) {
    this.newUser = new User();
  }

  ngOnInit() {
  }

  registerUser() {
    console.log("Register User data:", this.newUser);
    this.authService.registerUser(this.newUser).subscribe(data =>{
      console.log("User registered", data);
      this.router.navigate(['/login']);
    });
  }

}
