import {Reservation} from "../../model/reservation/reservation";
import {Type} from "../../enums/type";
import {Image} from "../../model/image/image";
import {Hotel} from "../../model/hotel/hotel";


export class DtoFilterHotel {
  minRating !: number;
   maxRating !: number;
}
