import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Review } from '../../model/review/review';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  private reviewUrl = 'http://localhost:8085/api/review';

  constructor(private http: HttpClient) {}

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  getAllReviews(): Observable<Review[]> {
    return this.http.get<Review[]>(`${this.reviewUrl}/all`, { headers: this.getHeaders() })
      .pipe(catchError(this.handleError));
  }

  getReviewById(id: number): Observable<Review> {
    return this.http.get<Review>(`${this.reviewUrl}/${id}`, { headers: this.getHeaders() })
      .pipe(catchError(this.handleError));
  }

  addReview(reservationId: number, review: Review): Observable<Review> {
    return this.http.post<Review>(`${this.reviewUrl}/reservation/${reservationId}`, review, { headers: this.getHeaders() })
      .pipe(catchError(this.handleError));
  }

  deleteReview(id: number | undefined): Observable<void> {
    return this.http.delete<void>(`${this.reviewUrl}/delete/${id}`, { headers: this.getHeaders() })
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'An unknown error occurred!';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Error: ${error.error.message}`;
    } else {
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(() => new Error(errorMessage));
  }


}
