import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { DtoHotel } from "../../dto/hotelDTO/dto-hotel";
import { DtoFilterHotel } from "../../dto/HotelFilterDTO/dto-filter-hotel";
import { DtoRoom } from "../../dto/roomDTO/dto-room";

@Injectable({
  providedIn: 'root'
})
export class HotelServiceService {
  private apiUrl = 'http://localhost:8085/api/hotel';
  private roomApiUrl = 'http://localhost:8085/api/room';

  constructor(private http: HttpClient) { }

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  getAllHotels(): Observable<DtoHotel[]> {
    return this.http.get<DtoHotel[]>(this.apiUrl, { headers: this.getHeaders() });
  }

  getHotelById(id: number): Observable<DtoHotel> {
    return this.http.get<DtoHotel>(`${this.apiUrl}/${id}`, { headers: this.getHeaders() });
  }

  addHotel(hotelDTO: {
    distance: number;
    price: number;
    averageRating: number;
    name: string;
    description: string;
    location: string
  }): Observable<DtoHotel> {
    return this.http.post<DtoHotel>(this.apiUrl, hotelDTO, { headers: this.getHeaders() });
  }

  updateHotel(id: number, hotelDTO: DtoHotel): Observable<DtoHotel> {
    return this.http.put<DtoHotel>(`${this.apiUrl}/${id}`, hotelDTO, { headers: this.getHeaders() });
  }

  deleteHotel(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers: this.getHeaders() });
  }

  filterHotels(filterDTO: DtoFilterHotel): Observable<DtoHotel[]> {
    return this.http.post<DtoHotel[]>(`${this.apiUrl}/filter`, filterDTO, { headers: this.getHeaders() });
  }

  search(category: string, location: string): Observable<DtoHotel[]> {
    return this.http.get<DtoHotel[]>(`${this.apiUrl}/search?category=${category}&location=${location}`, { headers: this.getHeaders() });
  }

  addRoomToHotel(hotelId: number, roomDTO: { price: number; available: boolean }, images: File[]): Observable<HttpEvent<HttpEvent<DtoHotel>>> {
    const formData = new FormData();
    formData.append('roomDTO', JSON.stringify(roomDTO));
    images.forEach(image => formData.append('images', image));

    return this.http.post<HttpEvent<DtoHotel>>(`${this.roomApiUrl}/hotels/${hotelId}`, formData, {
      reportProgress: true,
      observe: 'events'
    });
  }


  listRoomsByHotelId(hotelId: number): Observable<DtoRoom[]> {
    return this.http.get<DtoRoom[]>(`${this.roomApiUrl}/hotels/${hotelId}`, { headers: this.getHeaders() });
  }

  getRoomById(id: number): Observable<DtoRoom> {
    return this.http.get<DtoRoom>(`${this.roomApiUrl}/${id}`, { headers: this.getHeaders() });
  }

  updateRoom(id: number, roomDTO: { price: number; available: boolean }): Observable<void> {
    return this.http.put<void>(`${this.roomApiUrl}/${id}`, roomDTO, { headers: this.getHeaders() });
  }

  deleteRoom(id: number): Observable<void> {
    return this.http.delete<void>(`${this.roomApiUrl}/${id}`, { headers: this.getHeaders() });
  }
}
