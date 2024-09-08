import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {DtoHotel} from "../../dto/hotelDTO/dto-hotel";
import {DtoFilterHotel} from "../../dto/HotelFilterDTO/dto-filter-hotel";

@Injectable({
  providedIn: 'root'
})
export class HotelServiceService {
  private apiUrl = 'http://localhost:8080/api/hotels'; // URL de l'API backend pour les hôtels
  private roomApiUrl = 'http://localhost:8080/api/rooms'; // URL de l'API backend pour les chambres

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

  addHotel(hotelDTO: DtoHotel): Observable<DtoHotel> {
    return this.http.post<DtoHotel>(this.apiUrl, hotelDTO, { headers: this.getHeaders() });
  }

  updateHotel(id: number, hotelDTO: DtoHotel): Observable<HotelDTO> {
    return this.http.put<DtoHotel>(`${this.apiUrl}/${id}`, hotelDTO, { headers: this.getHeaders() });
  }

  deleteHotel(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers: this.getHeaders() });
  }

  filterHotels(filterDTO: DtoFilterHotel): Observable<HotelDTO[]> {
    return this.http.post<DtoHotel[]>(`${this.apiUrl}/filter`, filterDTO, { headers: this.getHeaders() });
  }

  search(category: string, location: string): Observable<HotelDTO[]> {
    return this.http.get<HotelDTO[]>(`${this.apiUrl}/search?category=${category}&location=${location}`, { headers: this.getHeaders() });
  }


  addRoomToHotel(hotelId: number, roomDTO: RoomDTO, images: File[]): Observable<RoomDTO> {
    const formData = new FormData();
    formData.append('roomDTO', JSON.stringify(roomDTO));
    images.forEach(image => formData.append('images', image));

    return this.http.post<RoomDTO>(`${this.roomApiUrl}/hotels/${hotelId}`, formData, {
      headers: this.getHeaders(),
      reportProgress: true,
      observe: 'events'
    });
  }

  listRoomsByHotelId(hotelId: number): Observable<RoomDTO[]> {
    return this.http.get<RoomDTO[]>(`${this.roomApiUrl}/hotels/${hotelId}`, { headers: this.getHeaders() });
  }

  getRoomById(id: number): Observable<RoomDTO> {
    return this.http.get<RoomDTO>(`${this.roomApiUrl}/${id}`, { headers: this.getHeaders() });
  }

  updateRoom(id: number, roomDTO: RoomDTO): Observable<void> {
    return this.http.put<void>(`${this.roomApiUrl}/${id}`, roomDTO, { headers: this.getHeaders() });
  }

  deleteRoom(id: number): Observable<void> {
    return this.http.delete<void>(`${this.roomApiUrl}/${id}`, { headers: this.getHeaders() });
  }
}
