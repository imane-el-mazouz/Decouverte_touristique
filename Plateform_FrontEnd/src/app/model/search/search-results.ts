import {Hotel} from "../hotel/hotel";
import {Excursion} from "../excursion/excursion";
import {DtoEvent} from "../../dto/eventDTO/dto-event";
import {DtoExcursion} from "../../dto/excursionDTO/dto-excursion";
import {DtoHotel} from "../../dto/hotelDTO/dto-hotel";

export class SearchResults {
  events: DtoEvent[] = [];
  excursions: DtoExcursion[] = [];
  hotels: DtoHotel[] = [];
}
