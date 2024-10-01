import {Component, OnInit} from '@angular/core';
import {HotelServiceService} from '../../service/hotel-service/hotel-service.service';
import {DtoHotel} from '../../dto/hotelDTO/dto-hotel';
import {DtoRoom} from '../../dto/roomDTO/dto-room';
import {Type} from '../../enums/type';
import {CategoryHotel} from '../../enums/category-hotel';
import {FormsModule} from "@angular/forms";
import {DatePipe, KeyValuePipe, NgForOf, NgIf} from "@angular/common";
import {DtoFilterHotel} from "../../dto/HotelFilterDTO/dto-filter-hotel";

@Component({
  selector: 'app-hotel-management',
  templateUrl: './hotel-management-component.component.html',
  standalone: true,
  imports: [
    FormsModule,
    NgIf,
    NgForOf,
    KeyValuePipe,
    DatePipe
  ],
  styleUrls: ['./hotel-management-component.component.css']
})
export class HotelManagementComponent implements OnInit {
  hotels: DtoHotel[] = [];
  selectedHotel: DtoHotel | null = null;
  rooms: DtoRoom[] = [];
  images: string[] = [];
  roomImages: File[] = [];
  newRoom: DtoRoom = {
    id: 0,
    type: Type.individual,
    price: 0,
    available: true,
    images: [],
    hotel: {} as DtoHotel,
    reservations: []
  };
  editRoomId: number | null = null;
  showRoomForm = false;
  showHotelForm = false;
  newHotel: DtoHotel = {
    idHotel: 0,
    name: '',
    description: '',
    img: '',
    location: '',
    categoryHotel: CategoryHotel.traditional,
    price: 0,
    averageRating: 0,
    distance: 0,
    rooms: []
  };

  hotelId: number | undefined;
  roomTypes = {
    individual: 'individual',
    couple: 'couple',
    familial: 'familial'
  };

  searchQuery: string = '';
  minRating?: number;
  maxRating?: number;
  categoryFilter: string = '';
  locationFilter: string = '';

  constructor(private hotelService: HotelServiceService) { }

  ngOnInit() {
    this.loadHotels();
  }

  loadHotels() {
    this.hotelService.getAllHotels().subscribe(hotels => {
      this.hotels = hotels;
    });
  }

  viewHotelDetails(id: number) {
    this.hotelService.getHotelById(id).subscribe(hotel => {
      this.selectedHotel = hotel;
      this.loadRooms(id);
    });
  }

  loadRooms(hotelId: number | undefined) {
    this.hotelService.listRoomsByHotelId(hotelId!).subscribe(rooms => {
      this.rooms = rooms;
    });
  }


  createRoom() {
    if (!this.selectedHotel) {
      console.error('No hotel selected');
      return;
    }

    const formData = new FormData();
    formData.append('room', JSON.stringify(this.newRoom));
    formData.append('type', this.newRoom.type);
    formData.append('price', this.newRoom.price.toString());
    formData.append('available', this.newRoom.available.toString());

    if (this.roomImages && this.roomImages.length > 0) { // Ensure roomImages is not undefined
      this.roomImages.forEach(image => {
        formData.append('images', image, image.name);
      });
    } else {
      console.warn('No images uploaded');
    }

    this.hotelService.addRoomToHotel(this.selectedHotel.idHotel, JSON.stringify(this.newRoom), this.roomImages)
      .subscribe({
        next: (response) => {
          console.log('Room created successfully', response);
          this.loadRooms(this.selectedHotel?.idHotel);
          this.resetRoomForm();
        },
      });
  }

  showAddRoomForm() {
    this.showRoomForm = true;
    this.newRoom = {
      id: 0,
      type: Type.individual,
      price: 0,
      available: true,
      images: [],
      hotel: {} as DtoHotel,
      reservations: []
    };
    this.editRoomId = null;
  }
  editRoom(id: number) {
    this.hotelService.getRoomById(id).subscribe(room => {
      this.newRoom = room;
      this.editRoomId = id;
      this.showRoomForm = true;
    });
  }

  updateRoom() {
    if (this.editRoomId !== null) {
      this.hotelService.updateRoom(this.editRoomId, this.newRoom).subscribe({
        next: () => {
          this.loadHotels();
          this.showRoomForm = false;
          this.editRoomId = null;
          this.resetRoomForm();
        },
        error: (error) => {
          console.error('Error updating room', error);
        }
      });
    }
  }

  deleteRoom(id: number): void {
    this.hotelService.deleteRoom(id).subscribe({
      next: () => {
        this.loadRooms(this.selectedHotel!.idHotel);
      },
      error: (error) => {
        console.error('Error deleting room', error);
      }
    });
  }

  deleteHotel(id: number): void {
    this.hotelService.deleteHotel(id).subscribe({
      next: () => {
        this.loadHotels();
      }
    });
  }

  showHotelList() {
    this.selectedHotel = null;
  }

  addHotel() {
    this.hotelService.addHotel(this.newHotel).subscribe({
      next: () => {
        this.loadHotels();
        this.showHotelForm = false;
        this.resetHotel();
      },
      error: (error) => {
        console.error('Error adding hotel', error);
      }
    });
  }

  onFileChange(event: any) {
    if (event.target.files) {
      this.roomImages = Array.from<File>(event.target.files);
    }
  }

  filterHotels() {
    const filterDTO: DtoFilterHotel = {
      category: this.categoryFilter.trim(),
      location: this.locationFilter.trim(),
      minRating: this.minRating,
      maxRating: this.maxRating
    };

    this.hotelService.filterHotels(filterDTO).subscribe(data => {
      this.hotels = data;
    });
  }

  searchHotels() {
    this.hotelService.search(this.categoryFilter, this.locationFilter).subscribe(data => {
      this.hotels = data;
    });
  }

  private resetRoomForm() {
    this.newRoom = {
      id: 0,
      type: Type.individual,
      price: 0,
      available: true,
      images: [],
      hotel: {} as DtoHotel,
      reservations: []
    };
    this.showRoomForm = false;
    this.editRoomId = null;
    this.roomImages = [];
  }

  private resetHotel() {
    this.newHotel = {
      idHotel: 0,
      name: '',
      description: '',
      img: '',
      location: '',
      categoryHotel: CategoryHotel.traditional,
      price: 0,
      averageRating: 0,
      distance: 0,
      rooms: []
    };
  }

  editHotel(id: number) {
    this.hotelService.getHotelById(id).subscribe(hotel => {
      this.newHotel = hotel;
      this.showHotelForm = true;
    });
  }

  updateHotel() {
    this.hotelService.updateHotel(this.newHotel.idHotel, this.newHotel).subscribe({
      next: () => {
        this.loadHotels();
        this.showHotelForm = false;
        this.resetHotel();
      },
      error: (error) => {
        console.error('Error updating hotel', error);
      }
    });
  }

  protected readonly CategoryHotel = CategoryHotel;
}
