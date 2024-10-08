import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import { ExcursionService } from "../../../service/excursion-service/excursion-service.service";
import { DtoExcursion } from "../../../dto/excursionDTO/dto-excursion";
import {DatePipe, NgForOf, NgIf} from "@angular/common";
import {AddExcursionComponentComponent} from "../add-excursion-component/add-excursion-component.component";
import {Excursion} from "../../../model/excursion/excursion";
import {TableModule} from "primeng/table";

@Component({
  selector: 'app-list-excursions-component',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgForOf,
    DatePipe,
    AddExcursionComponentComponent,
    NgIf,
    TableModule
  ],
  templateUrl: './list-excursions-component.component.html',
  styleUrls: ['./list-excursions-component.component.scss']
})
export class ListExcursionsComponentComponent implements OnInit {
  excursions: DtoExcursion[] = [];
  searchForm: FormGroup;
  filterForm: FormGroup;
  bookingForm: FormGroup;
  updateForm: FormGroup | undefined;
  selectedExcursion: DtoExcursion | null = null;
  isUpdating: boolean = false;


  constructor(
    private fb: FormBuilder,
    private excursionService: ExcursionService
  ) {
    this.searchForm = this.fb.group({
      date: [''],
      location: ['']
    });

    this.filterForm = this.fb.group({
      minRating: [0],
      maxRating: [5]
    });

    this.bookingForm = this.fb.group({
      userName: [''],
      userEmail: ['']
    });

    this.updateForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      imgPath: ['', Validators.required],
      dateTime: ['', Validators.required],
      location: ['', Validators.required],
      capacity: ['', [Validators.required, Validators.min(1)]],
      rating: ['', [Validators.required, Validators.min(1), Validators.max(5)]]
    });

  }

  ngOnInit(): void {
    this.loadExcursions();
  }

  loadExcursions(): void {
    this.excursionService.getAllExcursions().subscribe(
      data => {
        console.log('Excursion data:', data);
        this.excursions = data;
      },
      error => console.error('Error loading excursions', error)
    );
  }

  onSearch(): void {
    const { date, location } = this.searchForm.value;
    this.excursionService.searchExcursions(date, location).subscribe(
      data => this.excursions = data,
      error => console.error('Error searching excursions', error)
    );
  }

  onFilter(): void {
    const { minRating, maxRating } = this.filterForm.value;
    this.excursionService.filterExcursions(minRating, maxRating).subscribe(
      data => this.excursions = data,
      error => console.error('Error filtering excursions', error)
    );
  }



  onBook(id: number): void {
    this.selectedExcursion = this.excursions.find(excursion => excursion.idExcursion === id) || null;
  }

  onConfirmBooking(): void {
    if (this.bookingForm.valid && this.selectedExcursion) {
      const bookingDetails = this.bookingForm.value;
      console.log('Booking confirmed for excursion:', this.selectedExcursion);
      this.bookingForm.reset();
      this.selectedExcursion = null;
    }
  }

  deleteExcursion(id: number) {
    if (confirm('Are you sure you want to delete this excursion?')) {
      this.excursionService.deleteExcursion(id).subscribe({
        next: () => {
          this.excursions = this.excursions.filter(excursion => excursion.idExcursion !== id);
        },
        error: (err) => {
          console.error('Error deleting excursion', err);
        }
      });
    }
  }

  onExcursionAdded(excursion: DtoExcursion) {
    this.excursions.push(excursion);
  }
  updateExcursion(excursion: DtoExcursion): void {
    this.isUpdating = true;
    this.selectedExcursion = excursion;
    this.updateForm!.patchValue({
      name: excursion.name,
      description: excursion.description,
      imgPath: excursion.imgPath,
      dateTime: excursion.dateTime,
      location: excursion.location,
      capacity: excursion.capacity,
      rating: excursion.rating
    });


  }
  onConfirmUpdate(): void {
    if (this.selectedExcursion && this.updateForm!.valid) {
      const updatedExcursion = {
        ...this.selectedExcursion,
        ...this.updateForm!.value
      };

      this.excursionService.updateExcursion(this.selectedExcursion.idExcursion, updatedExcursion).subscribe({
        next: (updatedData) => {
          const index = this.excursions.findIndex(e => e.idExcursion === this.selectedExcursion!.idExcursion);
          if (index !== -1) {
            this.excursions[index] = updatedData;
          }
          this.isUpdating = false;
          this.selectedExcursion = null;
          this.updateForm!.reset();
        },
        error: (err) => {
          console.error('Error updating excursion', err);
        }
      });
    }
  }

}
