import {Component, OnInit} from '@angular/core';
import {Admin} from "../../model/admin/admin";
import {DashAdmin} from "../../model/DashAdmin/dash-admin";
import {AdminService} from "../../service/admin_service/admin-service.service";
import {FormsModule} from "@angular/forms";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-dash',
  standalone: true,
  imports: [
    FormsModule,
    NgIf
  ],
  templateUrl: './dash.component.html',
  styleUrl: './dash.component.css'
})
export class DashComponent implements OnInit{

  admin: Admin = new Admin();
  dashboardData: DashAdmin | undefined;
  errorMessage: string | undefined;

  constructor(private adminService: AdminService) { }

  ngOnInit(): void {
    this.loadDashboard();
  }

  // Load dashboard data
  loadDashboard(): void {
    this.adminService.getDashboard().subscribe(
      (data: DashAdmin) => {
        this.dashboardData = data;
        console.log('Dashboard data loaded successfully:', this.dashboardData);
      },
      (error) => {
        this.errorMessage = 'Error loading dashboard data';
        console.error('Error loading dashboard data', error);
      }
    );
  }

  // Update admin information
  updateAdmin(): void {
    this.adminService.updateAdmin(this.admin.id, this.admin).subscribe(
      (updatedAdmin: Admin) => {
        this.admin = updatedAdmin;
        console.log('Admin updated successfully:', this.admin);
      },
      (error) => {
        this.errorMessage = 'Error updating admin';
        console.error('Error updating admin', error);
      }
    );
  }
}
