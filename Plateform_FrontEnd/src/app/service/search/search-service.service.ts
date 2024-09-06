import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import {SearchResults} from "../../model/search/search-results";

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  private baseUrl = 'http://localhost:8080/api/search';

  constructor(private http: HttpClient) { }

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

    return this.http.get<SearchResults>(this.baseUrl, { params });
  }
}
