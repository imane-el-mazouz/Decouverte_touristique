import {Status} from "../../enums/status";
import {Excursion} from "../excursion/excursion";
import {Client} from "../client/client";
import {Room} from "../room/room";
import {Event} from "../event/event";

export class Reservation {

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
