import {Reservation} from "../reservation/reservation";
import {Type} from "../../enums/type";
import {Image} from "../image/image";
import {Hotel} from "../hotel/hotel";

export class Room {
  id !: number;
  type!: Type;
  price!: number;
  available!: boolean;
  images: Image[] = [];
  hotel: Hotel;
  reservations: Reservation[];


}
