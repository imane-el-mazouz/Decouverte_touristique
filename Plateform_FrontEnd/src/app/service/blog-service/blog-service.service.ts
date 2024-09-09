import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Blog } from '../../model/blog/blog';
import { Tradition } from '../../model/tradition/tradition';

@Injectable({
  providedIn: 'root'
})
export class TraditionBlogService {

  private apiUrl = 'http://localhost:8085/api/tradition';

  constructor(private http: HttpClient) { }


  createTraditionWithBlogs(tradition: Tradition): Observable<Tradition> {
    return this.http.post<Tradition>(`${this.apiUrl}/traditions`, tradition);
  }

  getAllTraditions(): Observable<Tradition[]> {
    return this.http.get<Tradition[]>(`${this.apiUrl}/traditions`);
  }


  getBlogsByTraditionId(traditionId: number | null): Observable<Blog[]> {
    return this.http.get<Blog[]>(`${this.apiUrl}/blogs/tradition/${traditionId}`);
  }

  addBlogToTradition(traditionId: number, blog: Blog): Observable<Blog> {
    return this.http.post<Blog>(`${this.apiUrl}/blogs/tradition/${traditionId}`, blog);
  }
}
