import {Component, ElementRef, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {FooterComponent} from "../shared/footer/footer.component";
import {NavBarComponent} from "../shared/nav-bar/nav-bar.component";
import {CurrencyPipe, DatePipe, NgForOf, NgIf} from "@angular/common";
import {MatFormField, MatFormFieldModule} from "@angular/material/form-field";
import {MatInput, MatInputModule} from "@angular/material/input";
import {MatButton, MatButtonModule} from "@angular/material/button";
import {FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {ReservationService} from "../../service/reservation-service/reservation-service.service";
import {ReviewService} from "../../service/review-service/review-service.service";
import {Router} from "@angular/router";
import {Review} from "../../model/review/review";
import {DtoEvent} from "../../dto/eventDTO/dto-event";
import {CategoryEvent} from "../../enums/category-event";
import {EventService} from "../../service/event-service/event-service.service";
import {PaginatorModule} from "primeng/paginator";
import {EventListComponentComponent} from "../event/event-list-component/event-list-component.component";


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
    NgIf,
    CurrencyPipe,
    DatePipe,
    PaginatorModule,
    EventListComponentComponent
  ],
  styleUrls: ['./event-page.component.scss']
})
export class EventPageComponent implements OnInit{
  @Output() searchResults = new EventEmitter<DtoEvent[]>();
  @ViewChild('bookingSection') bookingSection!: ElementRef;

  events: DtoEvent[] = [];
  event! : DtoEvent;
  categories = Object.values(CategoryEvent);
  searchForm: FormGroup ;
  filterForm!: FormGroup;


  constructor(private bookingService: ReservationService,
              private reviewService: ReviewService,
              private router: Router ,
              private eventService: EventService,
              private fb: FormBuilder,
  ) {

    this.bookingForm = new FormGroup({
      numberOfPerson: new FormControl('', Validators.required),
      dateTime: new FormControl('', Validators.required)
    });

    this.searchForm = this.fb.group({
      name: [''],
      category: [''],
      distance: ['']
    });
    this.searchForm = this.fb.group({
      name: [''],
      category: [''],
      distance: ['']
    });
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


  bookingForm!: FormGroup;
  bookingFormVisible = false;
  bookingSuccess = false;
  bookingError = false;
  selectedEvent?: DtoEvent;

  stars = [1, 2, 3, 4, 5];
  reservationId: number = 1;
  isEditMode: boolean = false;

  confirmationMessage: string = '';
  errorMessage: string = '';
  isLoading: boolean = true;

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

  bookEvent(): void {
    const bookingData = {
      eventId: this.bookingData.eventId,
      numberOfPerson: this.bookingData.numberOfPerson,
      dateTime: this.bookingData.dateTime
    };

    console.log('Booking Data:', bookingData);

    this.eventService.bookEvent(bookingData).subscribe(
      (response) => {
        console.log('Booking Response:', response);
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

  ngOnInit(): void {

    this.filterForm = this.fb.group({
        minPrice: [null],
        maxPrice: [null],
        minRating: [null],
        maxRating: [null],
        maxDistance: [null]
      }

    )
    this.eventService.getAllEvents().subscribe(
      (data) => {
        this.events = data;
        this.isLoading = false;
        console.log(this.events);
      },
      (error) => {
        console.error('Erreur lors de la récupération des événements', error);
        this.isLoading = false;
      }
    );

  }

  errorOccurred: boolean = false;

  loadEvents(): void {
    this.eventService.getAllEvents().subscribe({
      next: (events) => {
        this.events = events;
        this.isLoading = false;
        console.log('Events loaded:', this.events);
        console.log(events)
      },
      error: (err) => {
        this.isLoading = false;
        console.error('Error loading events:', err);
      }
    });
  }


  searchEvents(): void {
    const name = this.searchForm!.get('name')?.value;
    const category = this.searchForm!.get('category')?.value;
    const distance = this.searchForm!.get('distance')?.value;

    this.eventService.searchEvents(name, category, distance).subscribe({
      next: (events) => {
        this.events = events;
        console.log('Filtered events:', this.events);
      },
      error: (err) => {
        console.error('Error searching events', err);
      }
    });
  }

  onFilter(): void {
    const filterValues = this.filterForm.value;

    this.eventService.getFilteredEvents(filterValues).subscribe(
      (events: DtoEvent[]) => {
        this.events = events;
        console.log('Filtered events:', this.events);
      },
      (error) => {
        console.error('Error fetching filtered events', error);
      }
    );
  }

  showBookingForm(event: DtoEvent): void {
    this.selectedEvent = event;
    this.bookingFormVisible = true;
    this.bookingSuccess = false;
    this.bookingError = false;
  }

  prepareBooking(event: any): void {
    this.bookingData.eventId = event.id;
    this.bookingData.numberOfPerson = 1;
    this.bookingData.dateTime = new Date().toISOString();
    this.scrollToBookingSection();
  }

  scrollToBookingSection(): void {
    if (this.bookingSection) {
      this.bookingSection.nativeElement.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
  }


}



