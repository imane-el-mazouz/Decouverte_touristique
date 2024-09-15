import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  private apiUrl = 'http://localhost:8085/api/reservation';

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  constructor(private http: HttpClient) { }

  reserveEvent(eventId: number, numberOfPerson: number, dateTime: string): Observable<any> {
    const body = {
      eventId,
      numberOfPerson,
      dateTime
    };
    return this.http.post(`${this.apiUrl}/event`, body, { headers: this.getHeaders() });
  }

  reserveExcursion(excursionId: number, numberOfPerson: number, dateTime: string): Observable<any> {
    const body = {
      excursionId,
      numberOfPerson,
      dateTime
    };
    return this.http.post(`${this.apiUrl}/excursion`, body, { headers: this.getHeaders() });
  }

  reserveHotel(roomId: number, numberOfPerson: number, checkInDate: string, checkOutDate: string): Observable<any> {
    const body = {
      roomId,
      numberOfPerson,
      checkInDate,
      checkOutDate
    };
    return this.http.post(`${this.apiUrl}/hotel`, body, { headers: this.getHeaders() });
  }

  listReservationsForEvent(eventId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/event/${eventId}`, { headers: this.getHeaders() });
  }

  listReservationsForExcursion(excursionId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/excursion/${excursionId}`, { headers: this.getHeaders() });
  }


  listReservationsForHotel(roomId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/hotel/${roomId}`, { headers: this.getHeaders() });
  }


  deleteReservation(reservationId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${reservationId}`, { headers: this.getHeaders() });
  }
}
