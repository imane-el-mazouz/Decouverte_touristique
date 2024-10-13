import {Status} from "../../enums/status";
import {Excursion} from "../../model/excursion/excursion";
import {Client} from "../../model/client/client";
import {Event} from "../../model/event/event";
import {Room} from "../../model/room/room";

export class DtoReservation {
  idReservation !: number;
  numberOfPerson !: number;
  dateTime !: Date;
  checkInDate !: Date;
  checkOutDate !: Date;
  reservedRoom !: Boolean;

  status !: Status;
  excursion !: Excursion;
  client !: Client;
  event !: Event;
  room !: Room ;
}
