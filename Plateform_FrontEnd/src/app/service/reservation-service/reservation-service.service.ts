import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {DtoReservation} from "../../dto/reservationDTO/dto-reservation";

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

  // listReservationsForEvent(eventId: number): Observable<DtoReservation[]> {
  //   return this.http.get<DtoReservation[]>(`${this.apiUrl}/event/${eventId}`, { headers: this.getHeaders() });
  // }
  //
  //
  // listReservationsForExcursion(excursionId: number): Observable<DtoReservation[]> {
  //   return this.http.get<DtoReservation[]>(`${this.apiUrl}/excursion/${excursionId}`, { headers: this.getHeaders() });
  // }
  //
  // listReservationsForHotel(roomId: number): Observable<DtoReservation[]> {
  //   return this.http.get<DtoReservation[]>(`${this.apiUrl}/hotel/${roomId}`, { headers: this.getHeaders() });
  // }

  deleteReservation(reservationId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/reservations/${reservationId}`, { headers: this.getHeaders() });
  }
  listReservationsForEvents(): Observable<DtoReservation[]> {
    return this.http.get<DtoReservation[]>(`${this.apiUrl}/events`, { headers: this.getHeaders() });
  }

  listReservationsForExcursions(): Observable<DtoReservation[]> {
    return this.http.get<DtoReservation[]>(`${this.apiUrl}/excursions`, { headers: this.getHeaders() });
  }

  listReservationsForHotels(): Observable<DtoReservation[]> {
    return this.http.get<DtoReservation[]>(`${this.apiUrl}/hotels`, { headers: this.getHeaders() });
  }
}
