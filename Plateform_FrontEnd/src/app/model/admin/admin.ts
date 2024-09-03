import {Person} from "../person/person";
import {Role} from "../../enums/role";

export class Admin extends Person {
  constructor() {
    super();
    this.role = Role.Admin;
  }
}
