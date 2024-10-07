import {Reservation} from "../reservation/reservation";

export class Excursion {
    idExcursion !: number ;
    name !: string ;
    description !: string ;
    imgPath !: string ;
    dateTime !: Date;
    location !: string ;
    capacity !: number;
    rating !: number ;

    reservations : Reservation[] = [] ;
}
