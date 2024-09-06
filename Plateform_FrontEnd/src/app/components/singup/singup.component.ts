
import { Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthService } from '../../service/auth_service/auth-service.service';
import {Person} from "../../model/person/person";


@Component({
  selector: 'app-signup',
  templateUrl: './singup.component.html',
  standalone: true,
  imports: [
    ReactiveFormsModule
  ],
  styleUrls: ['./singup.component.scss']
})
export class SignupComponent {
  signupForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private authService: AuthService,
    private router: Router
  ) {
    this.signupForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', Validators.required],
      password: ['', Validators.required],
      role: ['Client', Validators.required]

    });
  }

  signup(): void {
    const { name, email, password, role } = this.signupForm.value;

    this.http.post<{ accessToken: string, person: any }>('http://localhost:8080/api/auth/signup', {
      name,
      email,
      password,
      role
    }).subscribe(
      response => {
        this.authService.setToken(response.accessToken);
        this.router.navigate(['/login']);
      },
      error => {
        console.error('Error during sign-up', error);
      }
    );
  }

}
