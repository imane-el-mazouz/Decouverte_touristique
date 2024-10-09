import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import { ExcursionService } from "../../../service/excursion-service/excursion-service.service";
import { DtoExcursion } from "../../../dto/excursionDTO/dto-excursion";
import {DatePipe, NgForOf, NgIf} from "@angular/common";
import {AddExcursionComponentComponent} from "../add-excursion-component/add-excursion-component.component";
import {Excursion} from "../../../model/excursion/excursion";
import {TableModule} from "primeng/table";
import {eachDayOfInterval, endOfMonth, endOfWeek, isSameMonth, startOfMonth, startOfWeek} from "date-fns";
import {Button} from "primeng/button";
import {DialogModule} from "primeng/dialog";
import {PaginatorModule} from "primeng/paginator";

@Component({
  selector: 'app-list-excursions-component',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgForOf,
    DatePipe,
    AddExcursionComponentComponent,
    NgIf,
    TableModule,
    Button,
    DialogModule,
    PaginatorModule
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
  currentDate: Date = new Date();
  weeks: Date[][] = [];
  highlightedDays: Date[] = [];
  visible: boolean = false;
  paginatedExcursions = [];
  rowsPerPage = 5;
  first = 0;



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
    this.generateCalendar();
    this.highlightToday();
    this.loadData();

    // @ts-ignore
    this.excursions = this.loadExcursions();
    this.paginate({ first: this.first, rows: this.rowsPerPage });
  }

  // loadExcursions(): void {
  //   this.excursionService.getAllExcursions().subscribe(
  //     data => {
  //       console.log('Excursion data:', data);
  //       this.excursions = data;
  //     },
  //     error => console.error('Error loading excursions', error)
  //   );
  // }
  loadExcursions(): void {
    this.excursionService.getAllExcursions().subscribe(
      data => {
        console.log('Excursion data:', data);
        this.excursions = data;
        this.paginate({ first: this.first, rows: this.rowsPerPage });
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

  generateCalendar(): void {
    const startMonth = startOfMonth(this.currentDate);
    const endMonth = endOfMonth(this.currentDate);
    const startDate = startOfWeek(startMonth, { weekStartsOn: 0 });
    const endDate = endOfWeek(endMonth, { weekStartsOn: 0 });

    const days = eachDayOfInterval({ start: startDate, end: endDate });
    const weeks: Date[][] = [];

    while (days.length) {
      weeks.push(days.splice(0, 7));
    }

    this.weeks = weeks;
  }

  protected highlightToday() {

  }

  isHighlighted(day: Date) {

  }

  protected readonly isSameMonth = isSameMonth;

  nextMonth() {

  }

  showDialog() {
    this.visible = true;
  }



  loadData() {
    this.excursions = [
    ];
  }

  paginate(event: any) {
    this.first = event.first;
    this.rowsPerPage = event.rows;
    // @ts-ignore
    this.paginatedExcursions = this.excursions.slice(this.first, this.first + this.rowsPerPage);
  }
}
