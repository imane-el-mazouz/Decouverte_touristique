import {Role} from "../../enums/role";

export abstract class Person {
  id!: number;
  name!: string;
  email!: string;
  password!: string;
  role!: Role;
}
