
import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs';
import { CategoryEvent } from '../../enums/category-event';
import { DtoEvent } from '../../dto/eventDTO/dto-event';
import {EventFilterDTO} from "../../dto/event-filter.dto";
import {Reservation} from "../../model/reservation/reservation";

@Injectable({
  providedIn: 'root'
})
export class EventService {
  private apiUrl = 'http://localhost:8085/api/event';
  private baseUrl = 'http://localhost:8085/api/reservation';


  constructor(private http: HttpClient) {
  }

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  uploadImage(file: File): Observable<string> {
    const formData = new FormData();
    formData.append('file', file);

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    });

    return this.http.post<string>(`${this.apiUrl}/upload`, formData, {headers});
  }

  getAllEvents(): Observable<DtoEvent[]> {
    return this.http.get<DtoEvent[]>(`${this.apiUrl}/all`, {headers: this.getHeaders()});
  }

  getEventById(id: number): Observable<DtoEvent> {
    console.log("Fetching event with ID:", id);
    return this.http.get<DtoEvent>(`${this.apiUrl}/get/${id}`, {headers: this.getHeaders()});
  }


  saveEvent(eventDTO: DtoEvent): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    return this.http.post<DtoEvent>(this.apiUrl, eventDTO, {headers: this.getHeaders()});
  }


  updateEvent(eventId: number, updatedEvent: DtoEvent): Observable<DtoEvent> {
    return this.http.put<DtoEvent>(`${this.apiUrl}/${eventId}`, updatedEvent, {headers: this.getHeaders()});
  }

  deleteEvent(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, {headers: this.getHeaders()});
  }

  // searchEvents(category?: CategoryEvent, location?: string, date?: Date): Observable<DtoEvent[]> {
  //   let params: any = {};
  //   if (category) params.category = category;
  //   if (location) params.location = location;
  //   if (date) params.date = date.toISOString().split('T')[0];
  //
  //   return this.http.get<DtoEvent[]>(`${this.apiUrl}/search`, { headers: this.getHeaders(), params });
  // }
  searchEvents(name: string, category: string, distance: number): Observable<DtoEvent[]> {
    let params = new HttpParams();

    if (name) {
      params = params.set('name', name);
    }
    if (category) {
      params = params.set('category', category);
    }
    if (distance) {
      params = params.set('maxDistance', distance.toString());
    }

    return this.http.get<DtoEvent[]>(`${this.apiUrl}/search`, {
      headers: this.getHeaders(),
      params: params
    });
  }

  bookEvent(eventId: number, numberOfPerson: number, dateTime: string): Observable<Reservation> {
    if (!dateTime) {
      throw new Error('DateTime is undefined or null.');
    }

    const body = {
      eventId: eventId,
      numberOfPerson: numberOfPerson,
      dateTime: dateTime
    };

    return this.http.post<Reservation>(`${this.baseUrl}/event`,body, { headers: this.getHeaders() });
  }



  getFilteredEvents(filters: any): Observable<DtoEvent[]> {
    let params = new HttpParams();

    if (filters.minPrice != null) {
      params = params.set('minPrice', filters.minPrice);
    }
    if (filters.maxPrice != null) {
      params = params.set('maxPrice', filters.maxPrice);
    }
    if (filters.minRating != null) {
      params = params.set('minRating', filters.minRating);
    }
    if (filters.maxRating != null) {
      params = params.set('maxRating', filters.maxRating);
    }
    if (filters.maxDistance != null) {
      params = params.set('maxDistance', filters.maxDistance);
    }

    return this.http.get<DtoEvent[]>(`${this.apiUrl}/filter`, {
      params,
      headers: this.getHeaders()
    });
  }

}
