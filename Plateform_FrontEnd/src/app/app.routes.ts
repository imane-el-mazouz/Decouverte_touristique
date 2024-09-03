import { Routes } from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {HomePageComponent} from "./components/home-page/home-page.component";
import {AdminComponent} from "./components/admin-dash/admin.component";
import {ClientDashComponent} from "./components/client-dash/client-dash.component";

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  // { path : 'signup' , component: SignupComponent},
  { path: 'home', component: HomePageComponent },
  { path: 'client', component: ClientDashComponent },
  { path: 'dashboard', component: AdminComponent },
  { path: '', redirectTo: '/home', pathMatch: 'full' }



];
