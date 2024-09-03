import {Person} from "../person/person";
import {Role} from "../../enums/role";

export class Client extends Person{


  constructor() {
    super();
    this.role = Role.Client
  }

}
