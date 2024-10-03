import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, Validators} from '@angular/forms';
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
    MatPaginator
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
  bookingForm: FormGroup;
  review = { rating: 0, comment: '' };
  confirmationMessage: string = '';
  errorMessage: string = '';
  bookingConfirmed: boolean = false;
  reviewMessage: string = '';
  location: string = '';
  category: string = '';
  categories: string[] = ['bohemian', 'traditional'];
  rooms: any[] = [];
  selectedRoomId: number | null = null;
  bookingData = {
    numberOfPersons: 1,
    checkInDate: '',
    checkOutDate: ''
  };

  displayedColumns: string[] = ['position', 'name', 'weight', 'symbol'];


  showRooms: boolean = false;
  minRating?: number;
  maxRating?: number;
  private showBookingForm: boolean | undefined;

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
      roomId: [0, Validators.required],
      numberOfPerson: [0, [Validators.required, Validators.min(1)]],
      checkInDate: ['', Validators.required],
      checkOutDate: ['', Validators.required]
    }
    );
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
  bookRoom() {
    if (!this.selectedRoomId) {
      this.errorMessage = 'Please select a room.';
      return;
    }

    if (this.bookingData.checkInDate >= this.bookingData.checkOutDate) {
      this.errorMessage = 'Check-out date must be after check-in date.';
      return;
    }

    const { numberOfPersons, checkInDate, checkOutDate } = this.bookingData;

    this.hotelService.reserveHotel(this.selectedRoomId, numberOfPersons, checkInDate, checkOutDate)
      .subscribe(response => {
        this.bookingConfirmed = true;
        this.confirmationMessage = 'Booking confirmed!';
        this.resetBookingForm(); // Call method to reset form
      }, error => {
        this.errorMessage = 'Failed to book room: ' + error.message;
      });
  }

  resetBookingForm() {
    this.bookingData = {
      numberOfPersons: 0,
      checkInDate: '',
      checkOutDate: ''
    };
    this.selectedRoomId = null; // Reset the selected room
    this.showBookingForm = false; // Hide the booking form if needed
  }

  confirmBooking() {
    const { numberOfPersons, checkInDate, checkOutDate } = this.bookingData;

    if (this.selectedRoomId) {
      this.hotelService.reserveHotel(this.selectedRoomId, numberOfPersons, checkInDate, checkOutDate)
        .subscribe(
          (response) => {
            this.bookingConfirmed = true;
            this.confirmationMessage = 'Booking successful!';
          },
          (error) => {
            this.errorMessage = 'Booking failed. Please try again.';
          }
        );
    } else {
      this.errorMessage = 'Room ID is missing.';
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
    console.log("Viewing reviews for room with ID:", roomId);
  }

  addReview(roomId: number) {
    console.log("Adding review for room with ID:", roomId);
  }


  pageChanged(event: PageEvent) {
    this.currentPage = event.pageIndex + 1; // pageIndex is 0-based
    this.itemsPerPage = event.pageSize;
    this.loadHotels(); // or your method to fetch data
  }
}
