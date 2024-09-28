import {Tradition} from "../../model/tradition/tradition";

export class DtoBlog {
  id!: number;
  name!: string;
  description!: string;
  tradition !: Tradition;
}
