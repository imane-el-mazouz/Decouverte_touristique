import { Component } from '@angular/core';
import {RouterLink} from "@angular/router";
import {NgIf} from "@angular/common";
import {PersonServiceService} from "../../service/person-service/person-service.service";


@Component({
  selector: 'app-aside',
  standalone: true,
  imports: [
    RouterLink,
    NgIf,

  ],
  templateUrl: './aside.component.html',
  styleUrl: './aside.component.scss'
})
export class AsideComponent {
  showSaveUserForm = false;
  showTechForm = false;
  constructor(private personService: PersonServiceService) { }
  //
  // toggleSaveUserDisplay() {
  //   this.personService.toggleSaveUserDisplay(true);
  // }
  // toggleSaveTechDisplay() {
  //   this.personService.toggleSaveTechDisplay(true);
  // }
  //
  // hideSaveUser() {
  //   this.personService.toggleSaveUserDisplay(false);
  // }
}
