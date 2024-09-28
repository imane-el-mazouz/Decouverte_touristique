import {Excursion} from "../../model/excursion/excursion";

export class DtoExcursion {
  id !: number ;
  name !: string ;
  description !: string ;
  imgPath !: string ;
  dateTime !: Date;
  location !: string ;
  capacity !: number;
  rating !: number ;

  items: Excursion[] = [] ;
  total: number = 0;

}
