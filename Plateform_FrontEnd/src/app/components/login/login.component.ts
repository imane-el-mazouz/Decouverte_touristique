import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import {Router, RouterLink} from '@angular/router';
import { CommonModule } from '@angular/common';
import {AuthService} from "../../service/auth_service/auth-service.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterLink
  ],
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginForm: FormGroup;
  errorMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      email: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  login(): void {
    const { email, password } = this.loginForm.value;

    if (this.loginForm.invalid) {
      return;
    }

    this.http.post<{ accessToken: string, person: { role: string } }>('http://localhost:8080/api/auth/login', { email, password })
      .subscribe(
        response => {
          this.authService.setToken(response.accessToken);

          if (response.person.role === 'Admin') {
            this.router.navigate(['/dashboard']);
          } else if (response.person.role === 'Client') {
            this.router.navigate(['/client'])
          }else if (response.person.role === 'Visitor'){
            this.router.navigate(['/home'])
          } else {
            this.errorMessage = 'role undefined: ' + response.person.role;
          }
        },
        error => {
          console.error('Error during login', error);
          this.errorMessage = 'Login failed. Please check your credentials and try again.';
        }
      );
  }
}
