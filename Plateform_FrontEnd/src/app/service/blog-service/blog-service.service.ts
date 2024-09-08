import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class BlogServiceService {

  private apiUrl = 'http://localhost:8080/api';  // Adjust the URL according to your API endpoint

  constructor(private http: HttpClient) { }

  getBlogsByTraditionId(traditionId: number): Observable<Blog[]> {
    return this.http.get<Blog[]>(`${this.apiUrl}/blogs/tradition/${traditionId}`);
  }

  addBlogToTradition(traditionId: number, blog: Blog): Observable<Blog> {
    return this.http.post<Blog>(`${this.apiUrl}/blogs/tradition/${traditionId}`, blog);
  }

  createTraditionWithBlogs(tradition: Tradition): Observable<Tradition> {
    return this.http.post<Tradition>(`${this.apiUrl}/traditions`, tradition);
  }

  getAllTraditions(): Observable<Tradition[]> {
    return this.http.get<Tradition[]>(`${this.apiUrl}/traditions`);
  }
}
