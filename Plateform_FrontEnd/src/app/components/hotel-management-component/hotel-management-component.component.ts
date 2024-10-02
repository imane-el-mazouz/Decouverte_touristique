import {Component, OnInit} from '@angular/core';
import {HotelServiceService} from '../../service/hotel-service/hotel-service.service';
import {DtoHotel} from '../../dto/hotelDTO/dto-hotel';
import {DtoRoom} from '../../dto/roomDTO/dto-room';
import {Type} from '../../enums/type';
import {CategoryHotel} from '../../enums/category-hotel';
import {FormsModule} from "@angular/forms";
import {CurrencyPipe, DatePipe, KeyValuePipe, NgClass, NgForOf, NgIf} from "@angular/common";
import {DtoFilterHotel} from "../../dto/HotelFilterDTO/dto-filter-hotel";
import {TableModule} from "primeng/table";
import {Button} from "primeng/button";
import {TagModule} from "primeng/tag";

@Component({
  selector: 'app-hotel-management',
  templateUrl: './hotel-management-component.component.html',
  standalone: true,
  imports: [
    FormsModule,
    NgIf,
    NgForOf,
    KeyValuePipe,
    DatePipe,
    TableModule,
    Button,
    CurrencyPipe,
    TagModule,
    NgClass
  ],
  styleUrls: ['./hotel-management-component.component.css']
})
export class HotelManagementComponent implements OnInit {
  hotels: DtoHotel[] = [];
  selectedHotel: DtoHotel | null = null;
  rooms: DtoRoom[] = [];
  images: string[] = [];
  roomImages: File[] = [];
  roomTypes = Object.values(Type);
  newRoom: DtoRoom = {
    id: 0,
    type: Type.individual,
    price: 0,
    available: true,
    images: [],
    hotel: {} as DtoHotel,
    reservations: []
  };
  editRoom: DtoRoom | null = null;
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

  searchQuery: string = '';
  minRating?: number;
  maxRating?: number;
  categoryFilter: string = '';
  locationFilter: string = '';
  protected selectedHotelId !: number;

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
      this.getRoomsByHotelId(id);
    });
  }


  // getRoomsByHotelId(hotelId: number) {
  //   this.hotelService.listRoomsByHotelId(hotelId).subscribe((data: DtoRoom[]) => {
  //     this.rooms = data;
  //   });
  // }

  getRoomsByHotelId(hotelId: number): void {
    this.selectedHotelId = hotelId;
    this.hotelService.listRoomsByHotelId(hotelId).subscribe(
      (response: any) => {
        this.rooms = response;
      },
      (error) => {
        console.error("eror", error);
      }
    );
  }


  clearRooms(): void {
    this.selectedHotelId =0;
    this.rooms = [];
  }
  loadRooms(hotelId: number) {
    this.hotelService.listRoomsByHotelId(hotelId).subscribe({
      next: (rooms) => {
        this.rooms = rooms;
      },
      error: (error) => {
        console.error('Error loading rooms', error);
      }
    });
  }


  createRoom() {
    if (!this.selectedHotelId) {
      console.error('No hotel selected');
      return;
    }

    const formData = new FormData();
    formData.append('room', JSON.stringify(this.newRoom));
    formData.append('type', this.newRoom.type);
    formData.append('price', this.newRoom.price.toString());
    formData.append('available', this.newRoom.available.toString());

    if (this.roomImages.length > 0) {
      this.roomImages.forEach(image => {
        formData.append('images', image, image.name);
      });
    } else {
      console.warn('No images uploaded');
    }

    this.hotelService.addRoomToHotel(this.selectedHotelId, formData)
      .subscribe({
        next: (response) => {
          console.log('Room created successfully', response);
          this.loadRooms(this.selectedHotelId!);  // Reload rooms for the hotel
          this.resetRoomForm();
        },
      });
  }

  // Function to handle file selection
  onRoomImagesSelected(event: any) {
    this.roomImages = Array.from(event.target.files);
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

  deleteRoom(roomId: number): void {
    this.hotelService.deleteRoom(roomId).subscribe({
      next: () => {
        console.log('Room deleted successfully');
        this.loadRooms(this.selectedHotelId!);
      },
      error: (error) => {
        console.error('Error deleting room', error);
      }
    });
  }

  getRoomById(roomId: number): void {
    this.hotelService.getRoomById(roomId).subscribe({
      next: (room) => {
        this.editRoom = room; // Set the room to edit
        this.showRoomForm = true; // Show the form for editing
      },
      error: (error) => {
        console.error('Error fetching room', error);
      }
    });
  }

  updateRoom(): void {
    if (this.editRoom) {
      this.hotelService.updateRoom(this.editRoom.id, {
        price: this.editRoom.price,
        available: this.editRoom.available
      }).subscribe({
        next: () => {
          console.log('Room updated successfully');
          this.loadRooms(this.selectedHotelId!); // Reload rooms for the hotel
          this.resetRoomForm(); // Reset form after updating
        },
        error: (error) => {
          console.error('Error updating room', error);
        }
      });
    }
  }

  // private resetRoomForm() {
  //   this.newRoom = {
  //     id: 0,
  //     type: Type.individual,
  //     price: 0,
  //     available: true,
  //     images: [],
  //     hotel: {} as DtoHotel,
  //     reservations: []
  //   };
  //   this.showRoomForm = false;
  //   this.editRoomId = null;
  //   this.roomImages = [];
  // }

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

  showAddRoomForm(hotelId: number) {
    this.selectedHotelId = hotelId;
    this.showRoomForm = true;
    this.newRoom = new DtoRoom();
  }

  cancelRoomForm() {
    this.resetRoomForm();
  }
  resetRoomForm() {
    this.newRoom = new DtoRoom();
    this.editRoom = null;
    this.roomImages = [];
    this.showRoomForm = false;
    this.selectedHotelId! = 0;
  }



}
