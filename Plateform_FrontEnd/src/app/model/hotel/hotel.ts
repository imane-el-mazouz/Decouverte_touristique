
import {CategoryHotel} from "../../enums/category-hotel";
import {Room} from "../room/room";

export class Hotel {
  idHotel !: number ;
  name !: string ;
  description !: string ;
  img !: string ;
  location !: string ;

  categoryHotel !: CategoryHotel;
  rooms : Room[] = [] ;

  price !: number;
  averageRating !: number;
  distance !: number ;

}
