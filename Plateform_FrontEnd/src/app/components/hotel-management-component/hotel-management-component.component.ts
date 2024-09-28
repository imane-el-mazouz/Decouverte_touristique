// import { Component, OnInit } from '@angular/core';
// import { DtoHotel } from "../../dto/hotelDTO/dto-hotel";
// import { DtoRoom } from "../../dto/roomDTO/dto-room";
// import { HotelServiceService } from "../../service/hotel-service/hotel-service.service";
// import { DtoFilterHotel } from "../../dto/HotelFilterDTO/dto-filter-hotel";
// import {FormsModule} from "@angular/forms";
// import {NgForOf, NgIf} from "@angular/common";
//
// @Component({
//   selector: 'app-hotel-management',
//   templateUrl: './hotel-management-component.component.html',
//   standalone: true,
//   imports: [
//     FormsModule,
//     NgForOf,
//     NgIf
//   ],
//   styleUrls: ['./hotel-management-component.component.css']
// })
// export class HotelManagementComponent implements OnInit {
//   hotels: DtoHotel[] = [];
//   selectedHotel?: DtoHotel;
//   rooms: DtoRoom[] = [];
//   newHotel = {
//     distance: 0,
//     price: 0,
//     averageRating: 0,
//     name: '',
//     description: '',
//     location: ''
//   };
//   newRoom = {
//     price: 0,
//     available: true,
//     type: ''  // Added type field here
//   };
//   searchQuery: string = '';
//   minRating?: number;
//   maxRating?: number;
//   showRoomForm: boolean = false;
//   showHotelForm: boolean = false;
//   editRoomId?: number;
//   categoryFilter: string = '';
//   locationFilter: string = '';
//   private roomImages: File[] = [];
//
//   constructor(private hotelService: HotelServiceService) { }
//
//   ngOnInit(): void {
//     this.getAllHotels();
//   }
//
//   getAllHotels(): void {
//     this.hotelService.getAllHotels().subscribe(data => {
//       this.hotels = data;
//     });
//   }
//
//   viewHotelDetails(hotelId: number): void {
//     this.hotelService.getHotelById(hotelId).subscribe(data => {
//       this.selectedHotel = data;
//       this.getRooms(hotelId);
//     });
//   }
//
//   getRooms(hotelId: number | undefined): void {
//     if (hotelId) {
//       this.hotelService.listRoomsByHotelId(hotelId).subscribe(data => {
//         this.rooms = data;
//       });
//     }
//   }
//
//   showHotelList(): void {
//     this.selectedHotel = undefined;
//   }
//
//   showAddRoomForm(): void {
//     this.showRoomForm = true;
//     this.editRoomId = undefined;
//   }
//
//   addRoom(): void {
//     if (this.selectedHotel) {
//       if (this.editRoomId) {
//         // Update existing room
//         this.hotelService.updateRoom(this.editRoomId, this.newRoom).subscribe(() => {
//           this.refreshRoomData();
//           this.resetRoomForm();
//         });
//       } else {
//         // Add new room with images
//         this.hotelService.addRoomToHotel(this.selectedHotel.id, this.newRoom, this.roomImages).subscribe(() => {
//           this.refreshRoomData();
//           this.resetRoomForm();
//         });
//       }
//     }
//   }
//
// // Helper functions to refresh data and reset form
//   refreshRoomData(): void {
//     this.getRooms(this.selectedHotel?.id);
//   }
//
//   resetRoomForm(): void {
//     this.showRoomForm = false;
//     this.newRoom = { price: 0, available: true, type: '' };
//     this.editRoomId = undefined;
//     this.roomImages = []; // Reset the images if you're handling them
//   }
//
//   deleteRoom(roomId: number): void {
//     if (this.selectedHotel) {
//       this.hotelService.deleteRoom(roomId).subscribe(() => {
//         this.getRooms(this.selectedHotel?.id);
//       });
//     }
//   }
//
//   editRoom(roomId: number): void {
//     this.hotelService.getRoomById(roomId).subscribe(data => {
//       this.newRoom = data;
//       this.showRoomForm = true;
//       this.editRoomId = roomId;
//     });
//   }
//
//   addHotel(): void {
//     this.hotelService.addHotel(this.newHotel).subscribe(() => {
//       this.getAllHotels();
//       this.showHotelForm = false;
//       this.newHotel = { name: '', description: '', location: '', averageRating: 0, price: 0, distance: 0 };
//     });
//   }
//
//   searchHotels(): void {
//     if (this.searchQuery.trim()) {
//       const filter: DtoFilterHotel = {
//         search: this.searchQuery.trim(),
//         minRating: this.minRating,
//         maxRating: this.maxRating
//       };
//       this.hotelService.filterHotels(filter).subscribe(data => {
//         this.hotels = data;
//       });
//     } else {
//       this.getAllHotels();
//     }
//   }
//
//   filterHotels(): void {
//     const filter: DtoFilterHotel = {
//       category: this.categoryFilter.trim(),
//       location: this.locationFilter.trim(),
//       minRating: this.minRating,
//       maxRating: this.maxRating
//     };
//
//     if (filter.category || filter.location || filter.minRating !== undefined || filter.maxRating !== undefined) {
//       this.hotelService.filterHotels(filter).subscribe(data => {
//         this.hotels = data;
//       });
//     } else {
//       this.getAllHotels();
//     }
//   }
// }




import { Component, OnInit } from '@angular/core';
import { HotelServiceService } from '../../service/hotel-service/hotel-service.service';
import { DtoHotel } from '../../dto/hotelDTO/dto-hotel';
import { DtoRoom } from '../../dto/roomDTO/dto-room';
import { Type } from '../../enums/type';
import { CategoryHotel } from '../../enums/category-hotel';
import {FormsModule} from "@angular/forms";
import {KeyValuePipe, NgForOf, NgIf} from "@angular/common";
import {HttpEvent, HttpEventType} from "@angular/common/http";

@Component({
  selector: 'app-hotel-management',
  templateUrl: './hotel-management-component.component.html',
  standalone: true,
  imports: [
    FormsModule,
    NgIf,
    NgForOf,
    KeyValuePipe
  ],
  styleUrls: ['./hotel-management-component.component.css']
})
export class HotelManagementComponent implements OnInit {
  hotels: DtoHotel[] = [];
  selectedHotel: DtoHotel | null = null;
  rooms: DtoRoom[] = [];
  newRoom: DtoRoom = {
    id: 0,
    type: Type.individual,
    price: 0,
    available: true,
    images: [], // Change from single File to an array for multiple images
    hotel: {} as DtoHotel,
    reservations: []
  };
  editRoomId: number | null = null;
  showRoomForm = false;
  showHotelForm = false;
  newHotel: DtoHotel = { idHotel: 0, name: '', description: '', img: '', location: '', categoryHotel: CategoryHotel.traditional, price: 0, averageRating: 0, distance: 0, rooms: [] };


  readonly roomTypes = Type;
  private roomImages: File[] = [];

  searchQuery: string = '';
  minRating?: number;
  maxRating?: number;
  categoryFilter: string = '';
  locationFilter: string = '';
  private roomImage?: File;

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
    this.hotelService.listRoomsByHotelId(hotelId).subscribe(rooms => {
      this.rooms = rooms;
    });
  }

  showAddRoomForm() {
    this.showRoomForm = true;
    this.newRoom = {
      id: 0,
      type: Type.individual,
      price: 0,
      available: true,
      images: [], // Initialize as an empty array
      hotel: {} as DtoHotel,
      reservations: []
    };
    this.editRoomId = null;
  }

  addRoom() {
    if (this.selectedHotel) {
      const roomDTO: DtoRoom = {
        id: 0, // or another suitable default value
        price: this.newRoom.price,
        available: this.newRoom.available,
        type: this.newRoom.type,
        images: this.roomImages.map(file => URL.createObjectURL(file)), // Convert files to URLs
        hotel: this.selectedHotel, // Assuming the hotel object is needed
        reservations: [] // Initialize as an empty array or fetch the reservations if necessary
      };


      this.hotelService.addRoomToHotel(this.selectedHotel.idHotel, roomDTO, this.roomImages).subscribe({
        next: (event: HttpEvent<any>) => {
          if (event.type === HttpEventType.UploadProgress && event.total) {
            const progress = Math.round((100 * event.loaded) / event.total);
            console.log(`Room upload progress: ${progress}%`);
          } else if (event.type === HttpEventType.Response) {
            console.log('Room added successfully:', event.body);
            this.loadRooms(this.selectedHotel?.idHotel); // Refresh the room list
            this.resetRoomForm(); // Reset form after submission
          }
        },
        error: (error) => {
          console.error('Error adding room:', error);
          if (error.error) {
            console.error('Backend Error:', error.error.message || error.error);
          }
        }
      });
    } else {
      console.warn('No selected hotel to add room to');
    }
  }

  editRoom(id: number) {
    this.hotelService.getRoomById(id).subscribe(room => {
      this.newRoom = room;
      this.editRoomId = id;
      this.showRoomForm = true;
    });
  }

  deleteRoom(id: number) {
    this.hotelService.deleteRoom(id).subscribe(() => {
      this.loadRooms(this.selectedHotel!.idHotel);
    });
  }

  showHotelList() {
    this.selectedHotel = null;
  }

  addHotel() {
    this.hotelService.addHotel(this.newHotel).subscribe(() => {
      this.loadHotels();
      this.showHotelForm = false;
    });
  }

  onFileChange(event: any) {
    if (event.target.files) {
      this.roomImages = Array.from<File>(event.target.files); // Store all selected files
    }
  }


  filterHotels() {

  }

  private resetRoomForm() {
    this.newRoom = {
      id: 0,
      type: Type.individual,
      price: 0,
      available: true,
      images: [], // Initialize as an empty array
      hotel: {} as DtoHotel,
      reservations: []
    };
    this.showRoomForm = false;
    this.editRoomId = null;
  }
}
