import {Person} from "../person/person";
import {Role} from "../../enums/role";

export class Visitor extends Person{
  constructor() {
    super();
    this.role= Role.Visitor;
  }
}
