import {Room} from "../room/room";


export class Image {
  id !: number;
  imageUrl !: string;
  cloudinaryImageId !: string;
  rooms: Room[] = [];
}
