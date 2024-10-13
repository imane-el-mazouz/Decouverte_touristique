import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import {DtoReservation} from "../../dto/reservationDTO/dto-reservation";
import {ReservationService} from "../../service/reservation-service/reservation-service.service";
import {DatePipe, NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-booking',
  standalone: true,
  templateUrl: './booking.component.html',
  imports: [
    NgForOf,
    DatePipe,
    NgIf
  ],
  styleUrls: ['./booking.component.scss']
})
export class BookingComponent implements OnInit {
  eventReservations: DtoReservation[] = [];
  excursionReservations: DtoReservation[] = [];
  hotelRoomReservations: DtoReservation[] = [];
  private eventId!: number;
  private excursionId!: number;
  private roomId!: number;

  constructor(private reservationService: ReservationService) {}

  ngOnInit(): void {
    this.loadReservations();
  }

  loadReservations(): void {
    this.reservationService.listReservationsForEvents().subscribe(reservations => this.eventReservations = reservations);
    this.reservationService.listReservationsForExcursions().subscribe(reservations => this.excursionReservations = reservations);
    this.reservationService.listReservationsForHotels().subscribe(reservations => this.hotelRoomReservations = reservations);
  }

  removeReservation(reservationId: number): void {
    this.reservationService.deleteReservation(reservationId).subscribe(() => {
      this.loadReservations();
    });
  }

}
