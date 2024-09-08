import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CategoryEvent } from '../../enums/category-event';
import { DtoEvent } from '../../dto/eventDTO/dto-event';

@Injectable({
  providedIn: 'root'
})
export class EventService {
  private apiUrl = 'http://localhost:8085/api/event';

  constructor(private http: HttpClient) {}

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  // uploadImage(file: File): Observable<string> {
  //   const formData = new FormData();
  //   formData.append('file', file);
  //
  //   return this.http.post<string>(`${this.apiUrl}/upload`, formData, { headers: this.getHeaders() });
  // }
  uploadImage(file: File): Observable<string> {
    const formData = new FormData();
    formData.append('file', file);

    return this.http.post<string>(`${this.apiUrl}/upload`, formData);
  }


  getAllEvents(): Observable<DtoEvent[]> {
    return this.http.get<DtoEvent[]>(`${this.apiUrl}/all`, { headers: this.getHeaders() });
  }

  getEventById(id: number): Observable<DtoEvent> {
    return this.http.get<DtoEvent>(`${this.apiUrl}/get/${id}`, { headers: this.getHeaders() });
  }

  createEvent(event: DtoEvent, img: File | null): Observable<DtoEvent> {
    const formData: FormData = new FormData();
    formData.append('name', event.name);
    formData.append('description', event.description);
    formData.append('imgPath', event.imgPath || '');
    formData.append('date', new Date(event.date).toISOString());
    formData.append('location', event.location);
    formData.append('capacity', event.capacity.toString());
    formData.append('price', event.price.toString());
    formData.append('rating', event.rating.toString());
    formData.append('distance', event.distance.toString());
    formData.append('category', event.category.toString());
    if (img) formData.append('img', img);

    return this.http.post<DtoEvent>(this.apiUrl, formData);
  }

  updateEvent(id: number, formData: FormData): Observable<DtoEvent> {
    return this.http.put<DtoEvent>(`${this.apiUrl}/${id}`, formData, { headers: this.getHeaders() });
  }

  deleteEvent(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers: this.getHeaders() });
  }

  filterEvents(filterData: any): Observable<DtoEvent[]> {
    return this.http.get<DtoEvent[]>(`${this.apiUrl}/filter`, { headers: this.getHeaders(), params: filterData });
  }

  searchEvents(category?: CategoryEvent, location?: string, date?: Date): Observable<DtoEvent[]> {
    let params: any = {};
    if (category) params.category = category;
    if (location) params.location = location;
    if (date) params.date = date.toISOString().split('T')[0];

    return this.http.get<DtoEvent[]>(`${this.apiUrl}/search`, { headers: this.getHeaders(), params });
  }
}
