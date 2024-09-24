import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { HotelServiceService } from '../../service/hotel-service/hotel-service.service';
import { ReservationService } from '../../service/reservation-service/reservation-service.service';
import { ReviewService } from '../../service/review-service/review-service.service';
import { Hotel } from '../../model/hotel/hotel';
import { DtoRoom } from '../../dto/roomDTO/dto-room';
import {CurrencyPipe, NgForOf, NgIf} from "@angular/common";
import {NavBarComponent} from "../shared/nav-bar/nav-bar.component";
import {FooterComponent} from "../shared/footer/footer.component";

@Component({
  selector: 'app-hotel-page',
  templateUrl: './hotel-page.component.html',
  standalone: true,
  imports: [
    CurrencyPipe,
    NgForOf,
    NavBarComponent,
    FooterComponent,
    NgIf
  ],
  styleUrls: ['./hotel-page.component.scss']
})
export class HotelPageComponent implements OnInit {
  stars = [1, 2, 3, 4, 5];
  hotels: Hotel[] = [];
  hotel: Hotel = new Hotel();
  reservationId?: number;
  idHotel?: number;
  bookingForm: FormGroup;
  review = { rating: 0, comment: '' };
  confirmationMessage: string = '';
  errorMessage: string = '';
  bookingConfirmed: boolean = false;
  reviewMessage: string = '';
  rooms: DtoRoom[] = [];

  constructor(
    private fb: FormBuilder,
    private hotelService: HotelServiceService,
    private reservationService: ReservationService,
    private reviewService: ReviewService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.bookingForm = this.fb.group({
      roomId: [0, Validators.required],
      numberOfPerson: [0, [Validators.required, Validators.min(1)]],
      checkInDate: ['', Validators.required],
      checkOutDate: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadHotels();
    console.log('Hotels:', this.hotels);
  }

  loadHotels(): void {
    this.hotelService.getAllHotels().subscribe(
      data => {
        this.hotels = data;
      },
      error => {
        console.error('Error loading hotels', error);
      }
    );
  }

  // Example of logging and validation in viewRooms method
  viewRooms(hotel: Hotel): void {
    console.log('Hotel object:', hotel);
    if (hotel && hotel.idHotel) {
      this.hotelService.listRoomsByHotelId(hotel.idHotel).subscribe(
        rooms => {
          this.rooms = rooms;
          console.log('Rooms:', this.rooms);
        },
        error => {
          console.error('Error loading rooms', error);
        }
      );
    } else {
      console.warn('No hotel ID found');
    }
  }



  bookRoom(): void {
    if (this.bookingForm.valid) {
      const formData = this.bookingForm.value;
      this.reservationService.reserveHotel(
        formData.roomId,
        formData.numberOfPerson,
        formData.checkInDate,
        formData.checkOutDate
      ).subscribe(
        response => {
          console.log('Booking successful', response);
          this.reservationId = response.id;
          this.bookingConfirmed = true;
          this.confirmationMessage = 'Your booking was successful!';
        },
        error => {
          console.error('Error booking hotel', error);
          this.errorMessage = 'There was an error processing your booking. Please try again.';
        }
      );
    } else {
      this.errorMessage = 'Please fill out the form correctly.';
    }
  }

  saveReview(): void {
    if (this.reservationId) {
      this.reviewService.addReview(this.reservationId, this.review).subscribe(
        response => {
          console.log('Review submitted successfully', response);
          this.reviewMessage = 'Your review was submitted successfully!';
        },
        error => {
          console.error('Error submitting review', error);
          this.reviewMessage = 'There was an error submitting your review. Please try again.';
        }
      );
    } else {
      this.errorMessage = 'Reservation ID is missing';
    }
  }

  setRating(number: number): void {
    this.review.rating = number;
  }
}
