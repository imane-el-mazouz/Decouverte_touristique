import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import { FooterComponent } from "../../shared/footer/footer.component";
import { FormsModule } from "@angular/forms";
import { NavBarComponent } from "../../shared/nav-bar/nav-bar.component";
import { NgForOf, NgIf } from "@angular/common";
import { Review } from "../../../model/review/review";
import {ExcursionResponse, ExcursionService} from "../../../service/excursion-service/excursion-service.service";
import { ReviewService } from "../../../service/review-service/review-service.service";
import { Router } from "@angular/router";
import { Excursion } from "../../../model/excursion/excursion";
import { DtoExcursion } from "../../../dto/excursionDTO/dto-excursion";
import {map} from "rxjs";
import * as L from 'leaflet';

@Component({
  selector: 'app-excursion-page',
  standalone: true,
  imports: [
    FooterComponent,
    FormsModule,
    NavBarComponent,
    NgForOf,
    NgIf
  ],
  templateUrl: './excursion-page.component.html',
  styleUrls: ['./excursion-page.component.scss']
})
export class ExcursionPageComponent implements OnInit {
  excursions: Excursion[] = [];
  currentPage = 1;
  itemsPerPage = 6;
  totalItems = 0;
  totalPages = 0;
  bookingData = {
    excursionId: 0,
    numberOfPerson: 1
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
  bookingConfirmed: boolean = false;
  reviewMessage: string = '';
  card: any;
  allExcursions: Excursion[] = [];

  constructor(
    private excursionService: ExcursionService,
    private reviewService: ReviewService,
    private router: Router
  ) {}

  transformDtoToExcursion(dto: DtoExcursion): Excursion {
    return {
      ...dto,
      reservations: [] // Default or fetch reservations separately if needed
    } as Excursion;
  }

  ngOnInit() {
    this.excursionService.getAllExcursions().pipe(
      map((data: DtoExcursion[]) => data.map(this.transformDtoToExcursion))
    ).subscribe(
      (excursions: Excursion[]) => {
        this.excursions = excursions;
      },
      error => console.error('Error loading excursions', error)
    );

    this.initMap();
  }

  private map: L.Map | undefined;

  hotels = [
    {
      name: "CVK Park Bosphorus Hotel Istanbul",
      coords: [41.0381, 28.9901],
      price: "$240/night",
    },
    {
      name: "Eresin Hotels Sultanahmet",
      coords: [41.0065, 28.9767],
      price: "$104/night",
    },
    {
      name: "Eresin Hotels Sultanahmet Boutique",
      coords: [41.007, 28.978],
      price: "$104/night",
    }
  ];




  updatePage(): void {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    this.excursions = this.allExcursions.slice(startIndex, endIndex);
    this.totalPages = Math.ceil(this.totalItems / this.itemsPerPage);
  }

  previousPage(): void {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.updatePage();
    }
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
      this.updatePage();
    }
  }

  // bookExcursion(excursionId: number): void {
  //   const numberOfPerson = 1;
  //   this.bookingData = { excursionId, numberOfPerson };
  //
  //   this.excursionService.bookExcursion(this.bookingData.excursionId, this.bookingData.numberOfPerson).subscribe(
  //     (response) => {
  //       this.confirmationMessage = 'Your booking was successful!';
  //       this.errorMessage = '';
  //     },
  //     (error) => {
  //       console.error('Error booking excursion', error);
  //       this.errorMessage = 'There was an error processing your booking. Please try again.';
  //       this.confirmationMessage = '';
  //     }
  //   );
  // }
  @ViewChild('bookingForm') bookingForm: ElementRef | undefined;


  bookExcursion(excursionId: number): void {
    this.bookingData = { excursionId, numberOfPerson: 1 };

    // Automatically submit the form after setting the booking data
    if (this.bookingForm && this.bookingForm.nativeElement) {
      this.bookingForm.nativeElement.submit();
    }
  }

  setRating(rating: number): void {
    this.review.rating = rating;
  }

  saveReview(): void {
    this.reviewService.addReview(this.bookingData.excursionId, this.review).subscribe(
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

  private initMap(): void {
    this.map = L.map('map').setView([41.015137, 28.979530], 13); // Coordonnées d'Istanbul

    // Ajouter les tuiles (tiles) OpenStreetMap
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '© OpenStreetMap contributors'
    }).addTo(this.map);


  }
}
