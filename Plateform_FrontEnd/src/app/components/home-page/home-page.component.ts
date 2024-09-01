import { Component } from '@angular/core';

import {RouterLink, RouterLinkActive} from "@angular/router";
import {FooterComponent} from "../footer/footer.component";
import {NavBarComponent} from "../nav-bar/nav-bar.component";

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [
    FooterComponent,
    NavBarComponent,
    RouterLink,
    RouterLinkActive,
  ],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.scss'
})
export class HomePageComponent {

}
