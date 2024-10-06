// import { Component, OnInit } from '@angular/core';
// import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
// import { EventService } from '../../../service/event-service/event-service.service';
// import { DtoEvent } from '../../../dto/eventDTO/dto-event';
// import { CategoryEvent } from '../../../enums/category-event';
// import { CurrencyPipe, DatePipe, NgForOf, NgIf } from '@angular/common';
//
// @Component({
//   selector: 'app-event-list-component',
//   templateUrl: './event-list-component.component.html',
//   standalone: true,
//   imports: [
//     DatePipe,
//     CurrencyPipe,
//     ReactiveFormsModule,
//     NgIf,
//     NgForOf
//   ],
//   styleUrls: ['./event-list-component.component.css']
// })
// export class EventListComponentComponent implements OnInit {
//   events: DtoEvent[] = [];
//   editMode = false;
//   selectedEvent?: DtoEvent;
//   eventForm: FormGroup;
//   bookingForm: FormGroup;
//   bookingFormVisible = false;
//   bookingSuccess = false;
//   bookingError = false;
//   fileToUpload: File | null = null;
//   categories = Object.values(CategoryEvent);
//
//   constructor(private eventService: EventService, private fb: FormBuilder) {
//     this.eventForm = this.fb.group({
//       name: ['', Validators.required],
//       description: [''],
//       date: ['', Validators.required],
//       location: [''],
//       capacity: [0, Validators.required],
//       price: [0, Validators.required],
//       rating: [0],
//       distance: [0],
//       category: ['', Validators.required]
//     });
//   }
//
//   ngOnInit(): void {
//     this.loadEvents();
//   }
//
//   loadEvents(): void {
//     this.eventService.getAllEvents().subscribe({
//       next: (events) => {
//         this.events = events;
//         console.log('Loaded events:', this.events);
//       },
//       error: (err) => {
//         console.error('Error loading events', err);
//       }
//     });
//   }
//
//   editEvent(event: DtoEvent): void {
//     this.selectedEvent = event;
//     this.editMode = true;
//     this.eventForm.patchValue(event);
//   }
//
//   updateEvent(): void {
//     if (this.selectedEvent && this.eventForm.valid) {
//       const formValues = this.eventForm.value;
//       const updatedEvent: DtoEvent = { ...this.selectedEvent, ...formValues };
//
//       // Ensure date is an instance of Date
//       if (typeof updatedEvent.date === 'string') {
//         updatedEvent.date = new Date(updatedEvent.date);
//       }
//
//       const formData = new FormData();
//       formData.append('name', updatedEvent.name);
//       formData.append('description', updatedEvent.description);
//       formData.append('date', updatedEvent.date.toISOString()); // Ensure date is converted to ISO string
//       formData.append('location', updatedEvent.location);
//       formData.append('capacity', updatedEvent.capacity.toString());
//       formData.append('price', updatedEvent.price.toString());
//       formData.append('rating', updatedEvent.rating.toString());
//       formData.append('distance', updatedEvent.distance.toString());
//       formData.append('category', updatedEvent.category.toString());
//       if (this.fileToUpload) {
//         formData.append('img', this.fileToUpload);
//       }
//
//       this.eventService.updateEvent(this.selectedEvent.id, formData).subscribe({
//         next: () => {
//           this.loadEvents();
//           this.cancelEdit();
//         },
//         error: (err) => {
//           console.error('Error updating event', err);
//         }
//       });
//     }
//   }
//
//
//   //
//   // deleteEvent(eventId: number): void {
//   //   if (!eventId) {
//   //     console.error("Event ID is invalid:", eventId);
//   //     return;
//   //   }
//   //
//   //   this.eventService.deleteEvent(eventId).subscribe({
//   //     next: () => {
//   //       console.log(`Event ${eventId} deleted successfully.`);
//   //       this.loadEvents();
//   //     },
//   //     error: (err) => {
//   //       console.error('Error deleting event', err);
//   //     }
//   //   });
//   // }
//   deleteEvent(eventId: number): void {
//     if (!eventId) {
//       console.error("Event ID is invalid:", eventId);
//       return;
//     }
//
//     this.eventService.deleteEvent(eventId).subscribe({
//       next: () => {
//         console.log(`Event ${eventId} deleted successfully.`);
//         this.loadEvents();
//       },
//       error: (err) => {
//         console.error('Error deleting event', err);
//       }
//     });
//   }
//
//   cancelEdit(): void {
//     this.selectedEvent = undefined;
//     this.editMode = false;
//     this.eventForm.reset();
//     this.fileToUpload = null;
//   }
//
//   onFileChange(event: any): void {
//     const file = event.target.files[0];
//     if (file) {
//       this.fileToUpload = file;
//     }
//   }
//
//   onDeleteEvent(id: number): void {
//     if (id === undefined || id === null) {
//       console.error('Invalid event ID');
//       return;
//     }
//     this.eventService.deleteEvent(id).subscribe(
//       () => {
//         this.loadEvents();
//       },
//       error => {
//         console.error('Error deleting event:', error);
//       }
//     );
//   }
//
//   showBookingForm(event: DtoEvent): void {
//     this.selectedEvent = event;
//     this.bookingFormVisible = true;
//     this.bookingSuccess = false;
//     this.bookingError = false;
//   }
//
//   bookEvent(): void {
//     if (this.bookingForm.valid && this.selectedEvent) {
//       const bookingData = {
//         eventId: this.selectedEvent.id,
//         numberOfPerson: this.bookingForm.get('numberOfPerson')?.value,
//         dateTime: this.bookingForm.get('dateTime')?.value
//       };
//
//       this.eventService.bookEvent(bookingData).subscribe({
//         next: () => {
//           this.bookingSuccess = true;
//           this.bookingFormVisible = false;
//         },
//         error: () => {
//           this.bookingError = true;
//         }
//       });
//     }
//   }
// }
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
    EventSearchFilterComponent
  ],
  styleUrls: ['./event-list-component.component.css']
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
  }

  loadEvents(): void {
    this.eventService.getAllEvents().subscribe({
      next: (events) => {
        this.events = events;
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
        ...this.eventForm.value // récupère les valeurs modifiées du formulaire
      };

      this.eventService.updateEvent(updatedEvent.idEvent, updatedEvent).subscribe({
        next: () => {
          console.log('Event updated successfully');
          this.loadEvents(); // Recharge la liste des événements pour afficher les modifications
          this.cancelEdit(); // Réinitialise le formulaire et sort du mode édition
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

}
