import { Component } from '@angular/core';
import {FooterComponent} from "../shared/footer/footer.component";
import {NavBarComponent} from "../shared/nav-bar/nav-bar.component";
import {NgForOf, NgIf} from "@angular/common";
import {MatFormField, MatFormFieldModule} from "@angular/material/form-field";
import {MatInput, MatInputModule} from "@angular/material/input";
import {MatButton, MatButtonModule} from "@angular/material/button";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ReservationService} from "../../service/reservation-service/reservation-service.service";
import {ReviewService} from "../../service/review-service/review-service.service";
import {Router} from "@angular/router";
import {Review} from "../../model/review/review";

@Component({
  selector: 'app-event-page',
  standalone: true,
  templateUrl: './event-page.component.html',
  imports: [
    FooterComponent,
    NavBarComponent,
    NgForOf,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    FormsModule,
    ReactiveFormsModule,
    NgIf
  ],
  styleUrls: ['./event-page.component.scss']
})
export class EventPageComponent {
  constructor(private bookingService: ReservationService, private reviewService: ReviewService, private router: Router) {
  }

  bookingData = {
    eventId: 0,
    numberOfPerson: 1,
    dateTime: new Date().toISOString()
  };


  review: Review = {
    id: 0,
    rating: 0,
    comment: '',
    date: new Date()
  };

  stars = [1, 2, 3, 4, 5];
  reservationId: number = 1;
  isEditMode: boolean = false;

  confirmationMessage: string = '';
  errorMessage: string = '';

  currentPage = 1;
  iconsPerPage = 2;
  icons = [
    'https://cdn.builder.io/api/v1/image/assets/TEMP/0be4f5ae517fb455396a4b0d7af4bc7bf793de43906db4c47433085ebb2d9941?placeholderIfAbsent=true&apiKey=af169940446d48718e0b8026890b04d0',
    'https://cdn.builder.io/api/v1/image/assets/TEMP/363ec222dcaa41986982fe7d4fe42c6754d8b686330570a3039ec56d3f6faf61?placeholderIfAbsent=true&apiKey=af169940446d48718e0b8026890b04d0',
    'https://cdn.builder.io/api/v1/image/assets/TEMP/b52887050f70a579d63d67c8a54c32bfc221f1888c33da90bb32617a157b0c51?placeholderIfAbsent=true&apiKey=af169940446d48718e0b8026890b04d0',
    'https://cdn.builder.io/api/v1/image/assets/TEMP/1aa94dd5dbd1c62c4dcbe90535e72c08d66364c14f02b9335869d28aabcc38cb?placeholderIfAbsent=true&apiKey=af169940446d48718e0b8026890b04d0'
  ];
  bookingConfirmed: boolean = false;
  reviewMessage: string = '';
  get totalPages(): number {
    return Math.ceil(this.icons.length / this.iconsPerPage);
  }

  getVisibleIcons(): string[] {
    const startIndex = (this.currentPage - 1) * this.iconsPerPage;
    return this.icons.slice(startIndex, startIndex + this.iconsPerPage);
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
    }
  }

  prevPage(): void {
    if (this.currentPage > 1) {
      this.currentPage--;
    }
  }

  isNextDisabled(): boolean {
    return this.currentPage >= this.totalPages;
  }

  isPrevDisabled(): boolean {
    return this.currentPage <= 1;
  }


  bookEvent(): void {
    this.bookingService.reserveEvent(this.bookingData.eventId, this.bookingData.numberOfPerson, this.bookingData.dateTime).subscribe(
      (response) => {
        this.confirmationMessage = 'Your booking was successful!';
        this.errorMessage = '';
      },
      (error) => {
        console.error('Error booking event', error);
        this.errorMessage = 'There was an error processing your booking. Please try again.';
        this.confirmationMessage = '';
      }
    );
  }

  setRating(rating: number): void {
    this.review.rating = rating;
  }

  saveReview(): void {
    this.reviewService.addReview(this.bookingData.eventId, this.review).subscribe(
      () => {
        this.reviewMessage = 'Thank you for your review!';
        this.review.comment = '';
        this.review.rating = 0;
      },
      (error) => {
        console.error('Error adding review', error);
        this.reviewMessage = 'There was an error submitting your review. Please try again.';
      }
    );
  }
}



