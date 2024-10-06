import {Component, EventEmitter, Output} from '@angular/core';

import { CategoryEvent } from '../../../enums/category-event';
import {FormBuilder, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {EventService} from "../../../service/event-service/event-service.service";
import {DtoEvent} from "../../../dto/eventDTO/dto-event";
import {DatePipe, NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-event-search-filter',
  templateUrl: './event-search-filter.component.ts.component.html',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgForOf,
    NgIf,
    DatePipe
  ],
  styleUrls: ['./event-search-filter.component.ts.component.css']
})
export class EventSearchFilterComponent {
  @Output() searchResults = new EventEmitter<DtoEvent[]>();
  events: DtoEvent[] = [];
  categories = Object.values(CategoryEvent);
  searchFilterForm: FormGroup;
  searchForm: FormGroup ;

  constructor(private eventService: EventService, private fb: FormBuilder) {
    this.searchFilterForm = this.fb.group({
      category: [''],
      location: [''],
      date: ['']
    });
    this.searchForm = this.fb.group({
      name: [''],
      category: [''],
      distance: ['']
    });

  }

  // searchAndFilter(): void {
  //   const { category, location, date } = this.searchFilterForm.value;
  //
  //   if (category || location || date) {
  //     const filterData = { category, location, date };
  //     this.eventService.filterEvents(filterData).subscribe((filteredEvents) => {
  //       this.events = filteredEvents;
  //     });
  //   } else {
  //     this.eventService.searchEvents(category, location, date).subscribe((searchedEvents) => {
  //       this.events = searchedEvents;
  //     });
  //   }
  // }

  searchEvents(): void {
    const name = this.searchForm!.get('name')?.value;
    const category = this.searchForm!.get('category')?.value;
    const distance = this.searchForm!.get('distance')?.value;

    this.eventService.searchEvents(name, category, distance).subscribe({
      next: (events) => {
        this.searchResults.emit(events);
        this.events = events;
        console.log('Filtered events:', this.events);
      },
      error: (err) => {
        console.error('Error searching events', err);
      }
    });
  }
}
