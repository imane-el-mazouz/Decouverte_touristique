import {Client} from "../client/client";
import {Admin} from "../admin/admin";

export class Contact {
  id?: number;
  firstName !: string;
  fullName !: string;
  email !: string;
  message !: string;

  client !: Client;
  admin !: Admin ;




}
