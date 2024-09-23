
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { Blog } from '../../model/blog/blog';
import { Tradition } from '../../model/tradition/tradition';

@Injectable({
  providedIn: 'root'
})
export class TraditionBlogService {

  private apiUrl = 'http://localhost:8085/api/tradition';
  private baseUrl = 'http://localhost:8085/api/blog';

  constructor(private http: HttpClient) { }

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }
  createTraditionWithBlogs(tradition: Tradition): Observable<Tradition> {
    console.log('Sending create tradition request with data:', tradition);
    const headers = this.getHeaders();
    return this.http.post<Tradition>(`${this.apiUrl}/add`, tradition, { headers }).pipe(
      tap(response => console.log('Received response for create tradition:', response))
    );
  }

  getAllTraditions(): Observable<Tradition[]> {
    const headers = this.getHeaders();
    return this.http.get<Tradition[]>(`${this.apiUrl}/list`, { headers });
  }

  getBlogsByTraditionId(traditionId: number | null): Observable<Blog[]> {
    const headers = this.getHeaders();
    return this.http.get<Blog[]>(`${this.baseUrl}/tradition/${traditionId}`, { headers });
  }

  addBlogToTradition(traditionId: number, blog: Blog): Observable<Blog> {
    console.log('Sending add blog request with data:', blog, 'for tradition ID:', traditionId);
    const headers = this.getHeaders();
    return this.http.post<Blog>(`${this.baseUrl}/add/${traditionId}`, blog, { headers }).pipe(
      tap(response => console.log('Received response for add blog:', response))
    );
  }

  searchTraditionsByCity(city: string): Observable<Tradition[]> {
    const headers = this.getHeaders();
    return this.http.get<Tradition[]>(`${this.apiUrl}/search?city=${city}`, { headers });
  }
}


// import { Injectable } from '@angular/core';
// import { HttpClient } from '@angular/common/http';
// import { Observable, tap } from 'rxjs';
// import { Blog } from '../../model/blog/blog';
// import { Tradition } from '../../model/tradition/tradition';
//
// @Injectable({
//   providedIn: 'root'
// })
// export class TraditionBlogService {
//
//   private apiUrl = 'http://localhost:8085/api/tradition';
//   private baseUrl = 'http://localhost:8085/api/blog';
//
//   constructor(private http: HttpClient) { }
//
//   createTraditionWithBlogs(tradition: Tradition): Observable<Tradition> {
//     console.log('Sending create tradition request with data:', tradition);
//     return this.http.post<Tradition>(`${this.apiUrl}/add`, tradition).pipe(
//       tap(response => console.log('Received response for create tradition:', response))
//     );
//   }
//
//   getAllTraditions(): Observable<Tradition[]> {
//     return this.http.get<Tradition[]>(`${this.apiUrl}/list`);
//   }
//
//   getBlogsByTraditionId(traditionId: number | null): Observable<Blog[]> {
//     return this.http.get<Blog[]>(`${this.baseUrl}/tradition/${traditionId}`);
//   }
//
//   addBlogToTradition(traditionId: number, blog: Blog): Observable<Blog> {
//     console.log('Sending add blog request with data:', blog, 'for tradition ID:', traditionId);
//     return this.http.post<Blog>(`${this.baseUrl}/add/${traditionId}`, blog).pipe(
//       tap(response => console.log('Received response for add blog:', response))
//     );
//   }
//
//   searchTraditionsByCity(city: string): Observable<Tradition[]> {
//     return this.http.get<Tradition[]>(`${this.apiUrl}/search?city=${city}`);
//   }
// }
