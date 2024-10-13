import {DtoEvent} from "./eventDTO/dto-event";
import {DtoExcursion} from "./excursionDTO/dto-excursion";
import {DtoHotel} from "./hotelDTO/dto-hotel";

export interface SearchResults {
  events: DtoEvent[];
  excursions: DtoExcursion[];
  hotels: DtoHotel[];
}
