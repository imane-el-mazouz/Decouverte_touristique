import {Component, OnInit} from '@angular/core';

import {ActivatedRoute, Router, RouterLink, RouterLinkActive} from "@angular/router";
import {FooterComponent} from "../footer/footer.component";
import {NavBarComponent} from "../nav-bar/nav-bar.component";
import {SearchComponent} from "../search/search.component";

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [
    FooterComponent,
    NavBarComponent,
    RouterLink,
    RouterLinkActive,
    SearchComponent,
  ],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.scss'
})
export class HomePageComponent implements OnInit {
  constructor(private route: ActivatedRoute, private router:Router) {
  }

  ngOnInit(): void {
  }
  navigateToAbout(): void {
    this.router.navigate(['/about']);
  }

}
