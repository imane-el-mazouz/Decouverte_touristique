import {CategoryEvent} from "../../enums/category-event";
import {Reservation} from "../reservation/reservation";

export class Event {
  idEvent !: number ;
  name !: string ;
  description !: string ;
  imgPath !: string ;
  date !: Date;
  location !: string;
  capacity !: number ;
  price !: number;
  rating !: number;
  distance !: number ;

  category !: CategoryEvent;
  reservations : Reservation[] = [] ;


}
