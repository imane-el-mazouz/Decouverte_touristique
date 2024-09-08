import {Blog} from "../blog/blog";

export class Tradition {
  id!: number;
  city!: string;
  description!: string;

  blogs : Blog[] = [] ;
}
