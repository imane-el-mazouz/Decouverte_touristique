import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import {Observable} from "rxjs";
import {HttpHeaders} from "@angular/common/http";
import {Role} from "../../enums/role";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private tokenKey = 'token';
  private userIdKey = 'userId';
  private userRoleKey = 'userRole';

  constructor(@Inject(PLATFORM_ID) private platformId: Object) {}

  setToken(token: string): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.setItem(this.tokenKey, token);
    }
  }

  getToken(): string | null {
    if (isPlatformBrowser(this.platformId)) {
      return localStorage.getItem(this.tokenKey);
    }
    return null;
  }

  setUserRole(role : Role): void{
    if(isPlatformBrowser(this.platformId)){
      localStorage.setItem(this.userRoleKey , role)
    }
  }

  // getUserRole() : Role | null {
  //   if (isPlatformBrowser((this.platformId))){
  //     return localStorage.getItem(this.userRoleKey) as Role
  //   }
  //   return null;
  //
  // }

  getUserRole(): Role | null {
    const token = this.getToken();
    if (token) {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.role as Role;
    }
    return null;
  }



  clearToken(): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem(this.tokenKey);
      localStorage.removeItem(this.userIdKey);
    }
  }

}
