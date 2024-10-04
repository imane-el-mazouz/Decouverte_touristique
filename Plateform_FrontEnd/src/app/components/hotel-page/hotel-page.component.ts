import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { HotelServiceService } from '../../service/hotel-service/hotel-service.service';
import { ReservationService } from '../../service/reservation-service/reservation-service.service';
import { ReviewService } from '../../service/review-service/review-service.service';
import { Hotel } from '../../model/hotel/hotel';
import { DtoRoom } from '../../dto/roomDTO/dto-room';
import {CurrencyPipe, JsonPipe, NgClass, NgForOf, NgIf} from "@angular/common";
import {NavBarComponent} from "../shared/nav-bar/nav-bar.component";
import {FooterComponent} from "../shared/footer/footer.component";
import {DtoHotel} from "../../dto/hotelDTO/dto-hotel";
import {HttpClient} from "@angular/common/http";
import {DtoFilterHotel} from "../../dto/HotelFilterDTO/dto-filter-hotel";
import {TableModule} from "primeng/table";
import {ButtonDirective} from "primeng/button";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {MatTableDataSource} from "@angular/material/table";
import {Review} from "../../model/review/review";

@Component({
  selector: 'app-hotel-page',
  templateUrl: './hotel-page.component.html',
  standalone: true,
  imports: [
    CurrencyPipe,
    NgForOf,
    NavBarComponent,
    FooterComponent,
    NgIf,
    NgClass,
    FormsModule,
    TableModule,
    ButtonDirective,
    JsonPipe,
    MatPaginator,
    ReactiveFormsModule
  ],
  styleUrls: ['./hotel-page.component.scss']
})
export class HotelPageComponent implements OnInit {
  totalItems: number = 0; // Total number of items to be paginated
  pageSize = 5; // Default page size
  currentPage = 0; // Start with the first page
  dataSource = new MatTableDataSource<Hotel>([]); // Initialize the data source
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  totalRooms: number = 0;
  totalPages: number = 0;
  itemsPerPage = 5;
  stars = [1, 2, 3, 4, 5];
  hotels: Hotel[] = [];
  hotel: Hotel = new Hotel();
  reservationId?: number;
  idHotel?: number;
  review = { rating: 0, comment: '' };
  reviews: Review[] = []; // Un tableau d'avis vides
  newReview: { rating: number; comment: string } = { rating: 0, comment: '' }; // Nouvelle structure pour ajouter un avis

  errorMessage: string | null = null;  reviewMessage: string = '';
  location: string = '';
  category: string = '';
  categories: string[] = ['bohemian', 'traditional'];
  rooms: any[] = [];
  bookingData = {
    numberOfPersons: 1,
    checkInDate: '',
    checkOutDate: ''
  };


  displayedColumns: string[] = ['position', 'name', 'weight', 'symbol'];

  confirmationMessage: string | null = null;
  bookingConfirmed: boolean = false;
  showRooms: boolean = false;
  minRating?: number;
  maxRating?: number;
  bookingForm: FormGroup;
  selectedReservationId!: number;

  constructor(
    private fb: FormBuilder,
    private hotelService: HotelServiceService,
    private reservationService: ReservationService,
    private reviewService: ReviewService,
    private router: Router,
    private route: ActivatedRoute ,
    private http: HttpClient
  ) {
    this.bookingForm = this.fb.group({
      roomId: [null, Validators.required],
      numberOfPersons: [0, [Validators.required, Validators.min(1)]],
      checkInDate: ['', Validators.required],
      checkOutDate: ['', Validators.required]
    });
  }
  searchHotels() {
    console.log('Category:', this.category);
    console.log('Location:', this.location);

    const params = {
      category: this.category,
      location: this.location
    };

    this.hotelService.search(params.category, params.location).subscribe(response => {
      console.log('Hotels found:', response);
      this.hotels = response;
    }, error => {
      console.error('Error occurred while searching for hotels:', error);
    });
  }


  ngOnInit(): void {
    this.loadHotels();
    console.log('Hotels:', this.hotels);
    this.route.paramMap.subscribe(params => {});
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

  loadRooms(hotelId: number) {
    this.hotelService.listRoomsByHotelId(hotelId)
        .subscribe((data) => {
        this.rooms = data;
      }, (error) => {
        console.error('Error loading rooms', error);
      });
  }

  // showBookingForm(roomId: number) {
  //   this.selectedRoomId = roomId;
  // }


  viewRooms(hotel: Hotel): void {
    console.log('Hotel object:', hotel);
    if (hotel && hotel) {
      this.hotelService.listRoomsByHotelId(hotel.idHotel).subscribe(
        rooms => {
          this.rooms = rooms;
          console.log('Rooms data:', this.rooms);
          this.showRooms = true;
        },
        error => {
          console.error('Error loading rooms', error);
        }
      );
    } else {
      console.warn('No hotel ID found');
    }
  }



  // bookRoom(): void {
  //   if (this.bookingForm.valid) {
  //     const formData = this.bookingForm.value;
  //     this.reservationService.reserveHotel(
  //       formData.roomId,
  //       formData.numberOfPerson,
  //       formData.checkInDate,
  //       formData.checkOutDate
  //     ).subscribe(
  //       response => {
  //         console.log('Booking successful', response);
  //         this.reservationId = response.id;
  //         this.bookingConfirmed = true;
  //         this.confirmationMessage = 'Your booking was successful!';
  //       },
  //       error => {
  //         console.error('Error booking hotel', error);
  //         this.errorMessage = 'There was an error processing your booking. Please try again.';
  //       }
  //     );
  //   } else {
  //     this.errorMessage = 'Please fill out the form correctly.';
  //   }
  // }
  showBookingForm: boolean = false;
  selectedRoomId: number = 1;
  showAddReviewForm: any;
  roomId!: number;

  showBookingFormForRoom(roomId: number) {
    this.selectedRoomId = roomId;
    this.showBookingForm = true;
  }

  bookRoom() {
    if (this.selectedRoomId !== null) {
      console.log('Booking room:', this.selectedRoomId, this.bookingData);

      this.confirmationMessage = `Reservation confirmed for room ID ${this.selectedRoomId}`;
      this.errorMessage = null;
      this.showBookingForm = false;
      alert('Booking successful! Your reservation has been confirmed.');
    } else {
      this.errorMessage = 'No room selected for booking!';
      this.confirmationMessage = null;
    }
  }



  setRating(number: number): void {
    this.review.rating = number;
  }

  filterHotels(): void {
    const filterDTO: DtoFilterHotel = {
      minRating: this.minRating,
      maxRating: this.maxRating
    };

    this.hotelService.filterHotels(filterDTO)
      .subscribe((data: DtoHotel[]) => {
        this.hotels = data;
      });
  }

  viewReviews(roomId: number) {
    this.reviewService.getAllReviews(roomId).subscribe(
      (reviews) => {
        console.log("Reviews for room:", reviews);
        this.reviews = reviews;
      },
      (error) => {
        console.error("Error fetching reviews:", error);
      }
    );
  }


  addReview(roomId: number) {
    const reviewData = {
      rating: this.newReview.rating,
      comment: this.newReview.comment
    };

    this.reviewService.addReview(roomId, reviewData).subscribe(
      (response) => {
        console.log("Review added successfully:", response);
        alert("Review added successfully!");
        this.viewReviews(roomId);
      },
      (error) => {
        console.error("Error adding review:", error);
        alert("Error adding review.");
      }
    );
  }


  pageChanged(event: PageEvent) {
    this.currentPage = event.pageIndex + 1;
    this.itemsPerPage = event.pageSize;
    this.loadHotels();
  }



}
