import {Reservation} from "../../model/reservation/reservation";
import {Type} from "../../enums/type";
import {Image} from "../../model/image/image";
import {Hotel} from "../../model/hotel/hotel";


export class DtoRoom {
  id !: number;
  type !: Type;
  price !: number;
  available !: boolean;
  images! : string[] ;
  hotel ?: Hotel;
  reservations: Reservation[] = [];
}


