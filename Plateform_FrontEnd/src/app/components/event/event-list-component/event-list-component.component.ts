import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { EventService } from '../../../service/event-service/event-service.service';
import { DtoEvent } from '../../../dto/eventDTO/dto-event';
import { CategoryEvent } from '../../../enums/category-event';
import { CurrencyPipe, DatePipe, NgForOf, NgIf } from "@angular/common";

@Component({
  selector: 'app-event-list-component',
  templateUrl: './event-list-component.component.html',
  standalone: true,
  imports: [
    NgForOf,
    CurrencyPipe,
    DatePipe,
    ReactiveFormsModule,
    NgIf
  ],
  styleUrls: ['./event-list-component.component.css']
})
export class EventListComponentComponent implements OnInit {
  events: DtoEvent[] = [];
  editMode = false;
  selectedEvent?: DtoEvent;
  eventForm: FormGroup;
  fileToUpload: File | null = null;
  categories = Object.values(CategoryEvent);

  constructor(private eventService: EventService, private fb: FormBuilder) {
    this.eventForm = this.fb.group({
      name: ['', Validators.required],
      description: [''],
      date: ['', Validators.required],
      location: [''],
      capacity: [0, Validators.required],
      price: [0, Validators.required],
      rating: [0],
      distance: [0],
      category: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadEvents();
  }

  loadEvents(): void {
    this.eventService.getAllEvents().subscribe({
      next: (events) => {
        this.events = events;
      },
      error: (err) => {
        console.error('Error loading events', err);
      }
    });
  }

  editEvent(event: DtoEvent): void {
    this.selectedEvent = event;
    this.editMode = true;
    this.eventForm.patchValue(event);
  }

  updateEvent(): void {
    if (this.selectedEvent && this.eventForm.valid) {
      const updatedEvent: DtoEvent = { ...this.selectedEvent, ...this.eventForm.value };

      const formData = new FormData();
      formData.append('name', updatedEvent.name);
      formData.append('description', updatedEvent.description);
      formData.append('date', updatedEvent.date.toISOString());
      formData.append('location', updatedEvent.location);
      formData.append('capacity', updatedEvent.capacity.toString());
      formData.append('price', updatedEvent.price.toString());
      formData.append('rating', updatedEvent.rating.toString());
      formData.append('distance', updatedEvent.distance.toString());
      formData.append('category', updatedEvent.category.toString());
      if (this.fileToUpload) {
        formData.append('img', this.fileToUpload);
      }

      this.eventService.updateEvent(this.selectedEvent.id, formData).subscribe({
        next: () => {
          this.loadEvents();
          this.cancelEdit();
        },
        error: (err) => {
          console.error('Error updating event', err);
        }
      });
    }
  }

  deleteEvent(eventId: number): void {
    this.eventService.deleteEvent(eventId).subscribe({
      next: () => {
        this.loadEvents();
      },
      error: (err) => {
        console.error('Error deleting event', err);
      }
    });
  }

  cancelEdit(): void {
    this.selectedEvent = undefined;
    this.editMode = false;
    this.eventForm.reset();
    this.fileToUpload = null;
  }

  onFileChange(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.fileToUpload = file;
    }
  }
}
