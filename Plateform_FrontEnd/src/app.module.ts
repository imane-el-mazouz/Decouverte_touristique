
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import {AppComponent} from "./app/app.component";
import {routes} from "./app/app.routes";
import {AuthInterceptorService} from "./app/service/auth_interceptor/auth-interceptor-service.service";

let MatLuxonDateModule;

@NgModule({
  declarations: [


  ],
  imports: [
    HttpClientModule,
    RouterModule.forRoot(routes),
    FormsModule,
    BrowserAnimationsModule,

    AppComponent,
    BrowserModule,
    AppComponent


],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptorService,
      multi: true
    }
  ],
  bootstrap: []
})
export class AppModule { }
