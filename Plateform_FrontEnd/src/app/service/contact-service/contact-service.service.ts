import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ContactDto } from '../../dto/contactDTO/contact.dto.interface';

@Injectable({
  providedIn: 'root'
})
export class ContactService {
  private apiUrl = 'http://localhost:8085/api/contacts';

  constructor(private http: HttpClient) { }

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  getAllContacts(): Observable<ContactDto[]> {
    return this.http.get<ContactDto[]>(this.apiUrl, { headers: this.getHeaders() })
      .pipe(catchError(this.handleError));
  }

  getContactById(id: number): Observable<ContactDto> {
    return this.http.get<ContactDto>(`${this.apiUrl}/${id}`, { headers: this.getHeaders() })
      .pipe(catchError(this.handleError));
  }

  createContact(contactDTO: ContactDto): Observable<ContactDto> {
    return this.http.post<ContactDto>(this.apiUrl, contactDTO, { headers: this.getHeaders() })
      .pipe(catchError(this.handleError));
  }

  updateContact(id: number | undefined, contactDTO: ContactDto): Observable<ContactDto> {
    return this.http.put<ContactDto>(`${this.apiUrl}/${id}`, contactDTO, { headers: this.getHeaders() })
      .pipe(catchError(this.handleError));
  }

  deleteContact(id: number | undefined): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers: this.getHeaders() })
      .pipe(catchError(this.handleError));
  }

  private handleError(error: any): Observable<never> {
    console.error('An error occurred', error);
    return throwError(() => new Error('Error during this action'));
  }
}
