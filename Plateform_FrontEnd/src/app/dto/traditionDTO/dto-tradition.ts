import {Blog} from "../../model/blog/blog";

export class DtoTradition {
  id!: number;
  city!: string;
  description!: string;

  blogs : Blog[] = [];
}
