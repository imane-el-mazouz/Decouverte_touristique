
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { EventService } from '../../../service/event-service/event-service.service';
import { DtoEvent } from '../../../dto/eventDTO/dto-event';
import { CategoryEvent } from '../../../enums/category-event';
import { CurrencyPipe, DatePipe, NgForOf, NgIf } from '@angular/common';
import { Router } from '@angular/router';
import {EventFormComponentComponent} from "../event-form-component/event-form-component.component";
import {

  EventSearchFilterComponent
} from "../event-search-filter.component.ts/event-search-filter.component.ts.component";
import {EventFilterDTO} from "../../../dto/event-filter.dto";
import {PaginatorModule} from "primeng/paginator";
import {Button} from "primeng/button";
import {DialogModule} from "primeng/dialog";
import {EventChartComponent} from "../event-chart/event-chart.component";

@Component({
  selector: 'app-event-list-component',
  templateUrl: './event-list-component.component.html',
  standalone: true,
  imports: [
    DatePipe,
    CurrencyPipe,
    ReactiveFormsModule,
    NgIf,
    NgForOf,
    EventFormComponentComponent,
    EventSearchFilterComponent,
    PaginatorModule,
    Button,
    DialogModule,
    EventChartComponent
  ],
  styleUrls: ['./event-list-component.component.scss']
})
export class EventListComponentComponent implements OnInit {
  events: DtoEvent[] = [];
  editMode = false;
  selectedEvent?: DtoEvent;
  eventForm: FormGroup;
  bookingForm: FormGroup;
  bookingFormVisible = false;
  bookingSuccess = false;
  bookingError = false;
  fileToUpload: File | null = null;
  categories = Object.values(CategoryEvent);
  filterForm!: FormGroup;
  paginatedEvents: DtoEvent[] = [];
  rowsPerPage: number = 5;
  totalRecords: number = 0;
  currentPage: number = 0;
  visible: boolean = false;


  constructor(
    private eventService: EventService,
    private fb: FormBuilder,
    private router: Router
  ) {
    this.eventForm = this.fb.group({
      name: ['', Validators.required],
      description: [''],
      date: ['', Validators.required],
      imgPath: ['', Validators.required],
      location: [''],
      capacity: [0, Validators.required],
      price: [0, Validators.required],
      rating: [0],
      distance: [0],
      category: ['', Validators.required]
    });

    this.bookingForm = this.fb.group({
      numberOfPerson: [1, Validators.required],
      dateTime: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadEvents();
    this.filterForm = this.fb.group({
        minPrice: [null],
        maxPrice: [null],
        minRating: [null],
        maxRating: [null],
        maxDistance: [null]
      }
    )
  }

  // loadEvents(): void {
  //   this.eventService.getAllEvents().subscribe({
  //     next: (events) => {
  //       this.events = events;
  //       this.totalRecords = events.length;
  //       this.paginate({first: 0, rows: this.rowsPerPage});
  //       console.log('Loaded events:', this.events);
  //     },
  //     error: (err) => {
  //       console.error('Error loading events', err);
  //     }
  //   });
  // }
  loadEvents(): void {
    this.eventService.getAllEvents().subscribe({
      next: (events) => {
        this.events = events;
        this.totalRecords = events.length;
        this.paginate({first: 0, rows: this.rowsPerPage});
        console.log('Loaded events:', this.events);
      },
      error: (err) => {
        console.error('Error loading events', err);
      }
    });
  }


  onFileChange(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.fileToUpload = file;
    }
  }

  showBookingForm(event: DtoEvent): void {
    this.selectedEvent = event;
    this.bookingFormVisible = true;
    this.bookingSuccess = false;
    this.bookingError = false;
  }

  bookEvent(): void {
    if (this.bookingForm.valid && this.selectedEvent) {
      const bookingData = {
        eventId: this.selectedEvent.idEvent,
        numberOfPerson: this.bookingForm.get('numberOfPerson')?.value,
        dateTime: this.bookingForm.get('dateTime')?.value
      };

      this.eventService.bookEvent(bookingData).subscribe({
        next: () => {
          this.bookingSuccess = true;
          this.bookingFormVisible = false;
        },
        error: () => {
          this.bookingError = true;
        }
      });
    }
  }

  onEventAdded(newEvent: DtoEvent) {
    this.events.push(newEvent);
  }

  deleteEvent(eventId: number | undefined | null): void {
    if (!eventId) {
      console.error("Cannot delete event with undefined or null ID");
      return;
    }

    this.eventService.deleteEvent(eventId).subscribe(
      () => {
        console.log(`Event with ID ${eventId} deleted successfully.`);
        this.loadEvents();
      },
      (error) => {
        console.error("Error deleting event:", error);
      }
    );
  }

  updateEvent(): void {
    if (this.eventForm.valid && this.selectedEvent) {
      const updatedEvent: DtoEvent = {
        ...this.selectedEvent,
        ...this.eventForm.value
      };

      this.eventService.updateEvent(updatedEvent.idEvent, updatedEvent).subscribe({
        next: () => {
          console.log('Event updated successfully');
          this.loadEvents();
          this.cancelEdit();
        },
        error: (err) => {
          console.error('Error updating event', err);
        }
      });
    }
  }

  editEvent(event: DtoEvent): void {
    this.selectedEvent = event;
    this.editMode = true;
    this.eventForm.patchValue(event);
  }

  cancelEdit(): void {
    this.selectedEvent = undefined;
    this.editMode = false;
    this.eventForm.reset();
  }


  onSearchResults(results: DtoEvent[]): void {
    this.events = results;
  }

  paginate(event: any): void {
    const start = event.first;
    const end = event.first + event.rows;
    this.paginatedEvents = this.events.slice(start, end);
  }

  showDialog() {
    this.visible = true;
  }
}
