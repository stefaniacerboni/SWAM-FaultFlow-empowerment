import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  errorMessage: string;

  constructor(private authService: AuthService, private router: Router) {
    this.errorMessage = '';
  }

  onSubmit(loginForm: NgForm) {
    const username = loginForm.value.username;
    const password = loginForm.value.password;

    this.authService.login(username, password).subscribe(
      response => {
        if (response && response.token) {
          // Handle successful login
          localStorage.setItem('authToken', response.token);
          this.router.navigate(['/home']);
        } else {
          // Handle login failure
          this.errorMessage = 'Invalid username or password';
        }
      },
      error => {
        this.errorMessage = 'An error occurred. Please try again later.';
        console.error('Login error', error);
      }
    );
  }
}
