import {CategoryHotel} from "../../enums/category-hotel";
import {Room} from "../../model/room/room";

export class DtoHotel {
  idHotel!: number;
  name!: string;
  description!: string;
  img!: string;
  location!: string;
  categoryHotel!: CategoryHotel;
  rooms: Room[] = [];
  price!: number;
  averageRating!: number;
  distance!: number;
}
