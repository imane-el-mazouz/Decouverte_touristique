import { CategoryEvent } from "../../enums/category-event";

export interface DtoEvent {
  idEvent: number;
  name: string;
  description: string;
  imgPath: string;
  date: Date;
  location: string;
  capacity: number;
  price: number;
  rating: number;
  distance: number;
  category: CategoryEvent;
}
