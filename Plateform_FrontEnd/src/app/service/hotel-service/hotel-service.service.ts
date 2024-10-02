import { Injectable } from '@angular/core';
import {HttpClient, HttpEvent, HttpEventType, HttpHeaders} from "@angular/common/http";
import {Observable, tap, throwError} from "rxjs";
import { Type } from "../../enums/type";
import {DtoHotel} from "../../dto/hotelDTO/dto-hotel";
import {DtoFilterHotel} from "../../dto/HotelFilterDTO/dto-filter-hotel";
import {DtoRoom} from "../../dto/roomDTO/dto-room";
import {AuthService} from "../auth_service/auth-service.service";
import {catchError} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class HotelServiceService {
  private apiUrl = 'http://localhost:8085/api/hotel';
  private roomApiUrl = 'http://localhost:8085/api/room';

  constructor(private http: HttpClient , private authService : AuthService) { }

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

  addHotel(hotelDTO: { name: string; description: string }): Observable<DtoHotel> {
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


  // addRoomToHotel(hotelId: number, roomDTO: { price: number; available: boolean; type: string }, images: File[]): Observable<HttpEvent<any>> {
  //   const formData = new FormData();
  //   formData.append('roomDTO', JSON.stringify(roomDTO));
  //   images.forEach(image => formData.append('images', image));
  //
  //   return this.http.post<HttpEvent<any>>(`${this.roomApiUrl}/add/${hotelId}`, formData, {
  //     headers: this.getHeaders(),
  //     reportProgress: true,
  //     observe: 'events'
  //   });
  // }

  // addRoomToHotel(hotelId: number, room: DtoRoom, images: File[]): Observable<HttpEvent<any>> {
  //   const formData = new FormData();
  //   formData.append('room', new Blob([JSON.stringify(room)], { type: 'application/json' }));
  //
  //   images.forEach(file => formData.append('images', file, file.name));
  //
  //   return this.http.post<HttpEvent<any>>(`${this.roomApiUrl}/create/${hotelId}`, formData, {
  //     observe: 'events'
  //   }).pipe(
  //     catchError(error => {
  //       console.error('Error during API call:', error);
  //       return throwError(error);
  //     })
  //   );
  // }

  createRoomRequest(hotelId: number, room: DtoRoom, roomImages: File[]): Observable<any> {
    const formData = new FormData();
    formData.append('room', JSON.stringify(room));

    // Append each image file to the form data
    roomImages.forEach((image, index) => {
      formData.append(`image${index}`, image);
    });

    return this.http.post(`${this.roomApiUrl}/create/${hotelId}`, formData, {
      reportProgress: true,
      observe: 'events'
    });
  }



  private getToken(): string | null {
    return localStorage.getItem('token');
  }


  // const formData: FormData = new FormData();
    // formData.append('roomDTO', JSON.stringify(room)); // Make sure the key matches what the backend expects
    //
    // images.forEach(image => {
    //   formData.append('images', image, image.name);
    // });
    // const headers = new HttpHeaders({
    //   'Authorization': `Bearer ${localStorage.getItem('token')}`
    // });
    //
    // return this.http.post<HttpEvent<any>>(`${this.roomApiUrl}/create/${hotelId}`, formData, {
    //   headers,
    //   reportProgress: true,
    //   observe: 'events'
    // }).pipe(
    //   catchError(error => {
    //     console.error('Error occurred:', error); // Log the error
    //     return throwError(error); // Rethrow the error for further handling
    //   })
    // );





  listRoomsByHotelId(idHotel: number | undefined): Observable<DtoRoom[]> {
    return this.http.get<DtoRoom[]>(`${this.roomApiUrl}/hotel/${idHotel}`, { headers: this.getHeaders() });
  }


  getRoomById(id: number): Observable<DtoRoom> {
    return this.http.get<DtoRoom>(`${this.roomApiUrl}/get/${id}`, { headers: this.getHeaders() });
  }

  updateRoom(id: number, roomDTO: { price: number; available: boolean }): Observable<void> {
    return this.http.put<void>(`${this.roomApiUrl}/${id}`, roomDTO, { headers: this.getHeaders() });
  }

  deleteRoom(id: number): Observable<void> {
    return this.http.delete<void>(`${this.roomApiUrl}/${id}`, { headers: this.getHeaders() });
  }

  // getHotelById(id: number): Observable<DtoHotel> {
  //   return this.http.get<DtoHotel>(`${this.apiUrl}/${id}`, { headers: this.getHeaders() });
  // }
  //
  // listRoomsByHotelId(hotelId: number): Observable<DtoRoom[]> {
  //   return this.http.get<DtoRoom[]>(`${this.roomApiUrl}/hotels/${hotelId}`, { headers: this.getHeaders() });
  // }
  reserveHotel(roomId: number, numberOfPerson: number, checkInDate: string, checkOutDate: string): Observable<any> {
    const body = {
      roomId,
      numberOfPerson,
      checkInDate,
      checkOutDate
    };
    return this.http.post(`${this.apiUrl}/hotel`, body, { headers: this.getHeaders() });
  }

  // addRoomToHotel(hotelId: number, room: string, images: File[]): Observable<any> {
  //   const formData = new FormData();
  //
  //   formData.append('room', JSON.stringify(room));
  //
  //   images.forEach(image => {
  //     formData.append('images', image, image.name);
  //   });
  //
  //   const headers = new HttpHeaders({
  //     'Authorization': `Bearer ${this.authService.getToken()}`
  //   });
  //
  //   return this.http.post<any>(`${this.roomApiUrl}/create/${hotelId}`, formData, { headers })
  //
  // }
  addRoomToHotel(hotelId: number, formData: FormData): Observable<any> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.authService.getToken()}`
    });

    return this.http.post<any>(`${this.roomApiUrl}/create/${hotelId}`, formData, { headers });
  }

}
