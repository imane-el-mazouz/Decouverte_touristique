import { Component, OnInit } from '@angular/core';
import { HotelService } from '../services/hotel.service';
import { HotelDTO } from '../model/hotel.dto';
import { RoomDTO } from '../model/room.dto';
import { HotelFilterDTO } from '../model/hotel-filter.dto';

@Component({
  selector: 'app-hotel-management',
  templateUrl: './hotel-management.component.html',
  styleUrls: ['./hotel-management.component.scss']
})
export class HotelManagementComponent implements OnInit {
  hotels: HotelDTO[] = [];
  selectedHotel?: HotelDTO;
  rooms: RoomDTO[] = [];
  newHotel: HotelDTO = { name: '', description: '', location: '', categoryHotel: '', averageRating: 0, price: 0, distance: 0 };
  newRoom: RoomDTO = { type: '', price: 0, available: true };
  searchQuery: string = '';
  showRoomForm: boolean = false;
  showHotelForm: boolean = false;
  editRoomId?: number;

  constructor(private hotelService: HotelService) { }

  ngOnInit(): void {
    this.getAllHotels();
  }

  getAllHotels(): void {
    this.hotelService.getAllHotels().subscribe(data => {
      this.hotels = data;
    });
  }

  viewHotelDetails(hotelId: number): void {
    this.hotelService.getHotelById(hotelId).subscribe(data => {
      this.selectedHotel = data;
      this.getRooms(hotelId);
    });
  }

  getRooms(hotelId: number): void {
    this.hotelService.listRoomsByHotelId(hotelId).subscribe(data => {
      this.rooms = data;
    });
  }

  showHotelList(): void {
    this.selectedHotel = undefined;
  }

  showAddRoomForm(): void {
    this.showRoomForm = true;
    this.editRoomId = undefined; // Clear edit mode
  }

  addRoom(): void {
    if (this.selectedHotel) {
      if (this.editRoomId) {
        this.hotelService.updateRoom(this.editRoomId, this.newRoom).subscribe(() => {
          this.getRooms(this.selectedHotel.idHotel);
          this.showRoomForm = false;
          this.newRoom = { type: '', price: 0, available: true };
          this.editRoomId = undefined; // Clear edit mode
        });
      } else {
        this.hotelService.addRoomToHotel(this.selectedHotel.idHotel, this.newRoom).subscribe(() => {
          this.getRooms(this.selectedHotel.idHotel);
          this.showRoomForm = false;
          this.newRoom = { type: '', price: 0, available: true };
        });
      }
    }
  }

  deleteRoom(roomId: number): void {
    if (this.selectedHotel) {
      this.hotelService.deleteRoom(roomId).subscribe(() => {
        this.getRooms(this.selectedHotel.idHotel);
      });
    }
  }

  editRoom(roomId: number): void {
    this.hotelService.getRoomById(roomId).subscribe(data => {
      this.newRoom = data;
      this.showRoomForm = true;
      this.editRoomId = roomId;
    });
  }

  addHotel(): void {
    this.hotelService.addHotel(this.newHotel).subscribe(() => {
      this.getAllHotels();
      this.showHotelForm = false;
      this.newHotel = { name: '', description: '', location: '', categoryHotel: '', averageRating: 0, price: 0, distance: 0 };
    });
  }

  searchHotels(): void {
    if (this.searchQuery) {
      const filter: HotelFilterDTO = { search: this.searchQuery };
      this.hotelService.filterHotels(filter).subscribe(data => {
        this.hotels = data;
      });
    } else {
      this.getAllHotels();
    }
  }

  filterHotels(): void {
    if (this.categoryFilter || this.locationFilter) {
      const filter: HotelFilterDTO = {
        category: this.categoryFilter,
        location: this.locationFilter
      };
      this.hotelService.filterHotels(filter).subscribe(data => {
        this.hotels = data;
      });
    } else {
      this.getAllHotels();
    }
  }
}
