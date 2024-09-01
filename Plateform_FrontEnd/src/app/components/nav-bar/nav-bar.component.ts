import {Component, OnInit} from '@angular/core';
import {RouterLink, RouterLinkActive} from "@angular/router";
import {NgIf} from "@angular/common";
import {AuthService} from "../../service/auth_service/auth-service.service";

@Component({
  selector: 'app-nav-bar',
  standalone: true,
  imports: [
    RouterLink,
    RouterLinkActive,
    NgIf
  ],
  templateUrl: './nav-bar.component.html',
  styleUrl: './nav-bar.component.scss'
})
export class NavBarComponent implements OnInit{

  constructor(
    private plateformId : AuthService ,
  ) {
  }

  ngOnInit(): void {
    this.isLogin()
  }

  isLogin():boolean{
    if (this.plateformId.getToken()==null){
      return true
    }
    return false
  }

}
