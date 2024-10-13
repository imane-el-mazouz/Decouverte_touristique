import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SearchResults } from "../../model/search/search-results";

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  private apiUrl = 'http://localhost:8085/api/search';

  constructor(private http: HttpClient) { }

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  search(location: string, date: string, category: string): Observable<SearchResults> {
    let params = new HttpParams();

    if (location) {
      params = params.set('location', location);
    }

    if (date) {
      params = params.set('date', date);
    }

    if (category) {
      params = params.set('category', category);
    }

    return this.http.get<SearchResults>(this.apiUrl, { params, headers: this.getHeaders() });
  }


}
