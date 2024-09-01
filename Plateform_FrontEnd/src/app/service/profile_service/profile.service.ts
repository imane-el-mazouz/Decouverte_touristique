import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import {User} from "../../model/User/user";

@Injectable({
  providedIn: 'root'
})
export class ProfileService {
  private apiUrl = 'http://localhost:8080/api/user';

  constructor(private http: HttpClient) {}


  getUserProfile(userId: number): Observable<User> {
    const url = `${this.apiUrl}/user/${userId}`;
    return this.http.get<User>(url, { headers: this.getAuthHeaders() }).pipe(
      catchError(error => {
        console.error('Error fetching user profile:', error);
        return throwError(() => new Error('Error fetching user profile'));
      })
    );
  }



  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  updateUserProfile(userId: number | null, updatedProfile: Partial<User>): Observable<User> {
    const url = `${this.apiUrl}/${userId}`;
    return this.http.put<User>(url, updatedProfile, { headers: this.getAuthHeaders() }).pipe(
      catchError(error => {
        console.error('Error updating user profile:', error);
        return throwError(() => new Error('Error updating user profile'));
      })
    );
  }
}
