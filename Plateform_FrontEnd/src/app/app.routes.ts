import { Routes } from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {HomePageComponent} from "./components/shared/home-page/home-page.component";
import {AdminComponent} from "./components/admin-dash/admin.component";
import {ClientDashComponent} from "./components/client-dash/client-dash.component";
import {SearchBarComponent} from "./components/search-bar/search-bar.component";
import {SignupComponent} from "./components/singup/singup.component";
import {DashComponent} from "./components/dash/dash.component";
import {ContactPageComponent} from "./components/contact-page/contact-page.component";
import {ContactAddComponent} from "./components/contact-add-component/contact-add-component.component";
import {EventFormComponentComponent} from "./components/event/event-form-component/event-form-component.component";
import {EventListComponentComponent} from "./components/event/event-list-component/event-list-component.component";
import {
  ListExcursionsComponentComponent
} from "./components/excursion/list-excursions-component/list-excursions-component.component";
import {
  AddExcursionComponentComponent
} from "./components/excursion/add-excursion-component/add-excursion-component.component";
import {HotelManagementComponent} from "./components/hotel-management-component/hotel-management-component.component";
import {
  TraditionBlogManagementComponent
} from "./components/blog-tradition-component/blog-tradition-component.component";
import {GuardService} from "./service/auth_guard/guard.service";
import {Role} from "./enums/role";
import {ReviewManagementComponent} from "./components/review-management/review-management.component";
import {AboutPageComponent} from "./components/about-page/about-page.component";
import {EventPageComponent} from "./components/event-page/event-page.component";
import {ExcursionPageComponent} from "./components/excursion/excursion-page/excursion-page.component";

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path : 'signup' , component: SignupComponent},
  { path: 'home', component: HomePageComponent },
  { path: 'client', component: ClientDashComponent },
  { path: 'dashboard', component: AdminComponent },
  { path: 'search', component: SearchBarComponent },
  { path: 'info', component: DashComponent },
  { path: 'contacts', component: ContactPageComponent },
  { path: 'addContact', component: ContactAddComponent },
  { path: 'addEvent', component: EventFormComponentComponent },
  { path: 'events', component: EventListComponentComponent },
  { path: 'excursions', component: ListExcursionsComponentComponent },
  { path: 'addExcursion', component: AddExcursionComponentComponent },
  { path: 'hotels', component: HotelManagementComponent },
  { path: 'blogs', component: TraditionBlogManagementComponent },
  { path: 'reviews', component: ReviewManagementComponent },
  { path: 'about', component: AboutPageComponent },
  { path: 'events-page', component: EventPageComponent },
  { path: 'excursions-page', component: ExcursionPageComponent },

  {
    path: 'dashboard',
    component: AdminComponent,
    canActivate: [GuardService],
    data: {expectedRole: Role.Admin},
    children: [
      { path: 'addEvent', component: EventFormComponentComponent },
      { path: 'events', component: EventListComponentComponent },
      { path: 'excursions', component: ListExcursionsComponentComponent },
      { path: 'addExcursion', component: AddExcursionComponentComponent },
      { path: 'hotels', component: HotelManagementComponent },
      { path: 'blogs', component: TraditionBlogManagementComponent },
      { path: 'client', component: ClientDashComponent },
      { path: 'dashboard', component: AdminComponent },
      { path: 'search', component: SearchBarComponent },
      { path: 'info', component: DashComponent },
      { path: 'contacts', component: ContactPageComponent },
      { path: 'reviews', component: ReviewManagementComponent },

    ]
  },
  { path: '', redirectTo: '/home', pathMatch: 'full' }

];
