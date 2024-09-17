import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import {DtoExcursion} from "../../dto/excursionDTO/dto-excursion";

@Injectable({
  providedIn: 'root'
})
export class ExcursionService {
  private apiUrl = 'http://localhost:8085/api/excursion';
  private baseUrl = 'http://localhost:8085/api/reservation';

  constructor(private http: HttpClient) {}

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  private handleError(error: any): Observable<never> {
    console.error('An error occurred', error);
    return throwError(() => new Error('Something went wrong'));
  }

  // addExcursion(excursion: FormData): Observable<DtoExcursion> {
  //   const headers = this.getHeaders();
  //   return this.http.post<DtoExcursion>(`${this.apiUrl}`, excursion, { headers })
  //     .pipe(catchError(this.handleError));
  // }
  addExcursion(excursion: FormData): Observable<DtoExcursion> {
    const headers = this.getHeaders();
    return this.http.post<DtoExcursion>(`${this.apiUrl}`, excursion, { headers })
      .pipe(catchError(this.handleError));
  }

  getAllExcursions(): Observable<DtoExcursion[]> {
    const headers = this.getHeaders();
    return this.http.get<DtoExcursion[]>(`${this.apiUrl}/all`, { headers })
      .pipe(catchError(this.handleError));
  }

  updateExcursion(id: number, excursion: DtoExcursion): Observable<DtoExcursion> {
    const headers = this.getHeaders();
    return this.http.put<DtoExcursion>(`${this.apiUrl}/update/${id}`, excursion, { headers })
      .pipe(catchError(this.handleError));
  }

  searchExcursions(date?: string, location?: string): Observable<DtoExcursion[]> {
    const headers = this.getHeaders();
    let params = {};
    if (date) params = { ...params, date };
    if (location) params = { ...params, location };

    return this.http.get<DtoExcursion[]>(`${this.apiUrl}/search`, { headers, params })
      .pipe(catchError(this.handleError));
  }

  filterExcursions(minRating: number, maxRating: number): Observable<DtoExcursion[]> {
    const headers = this.getHeaders();
    return this.http.get<DtoExcursion[]>(`${this.apiUrl}/filter?minRating=${minRating}&maxRating=${maxRating}`, { headers })
      .pipe(catchError(this.handleError));
  }

  deleteExcursion(id: number): Observable<void> {
    const headers = this.getHeaders();
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`, { headers })
      .pipe(catchError(this.handleError));
  }

  bookExcursion(excursionId: number, userId: number): Observable<any> {
    const headers = this.getHeaders();
    return this.http.post(`${this.baseUrl}/excursion/${excursionId}`, { userId }, { headers });
  }
}
