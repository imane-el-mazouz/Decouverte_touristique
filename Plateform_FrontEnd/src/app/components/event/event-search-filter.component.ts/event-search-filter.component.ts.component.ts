import { Component } from '@angular/core';

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
  events: DtoEvent[] = [];
  categories = Object.values(CategoryEvent);
  searchFilterForm: FormGroup;

  constructor(private eventService: EventService, private fb: FormBuilder) {
    this.searchFilterForm = this.fb.group({
      category: [''],
      location: [''],
      date: ['']
    });
  }

  searchAndFilter(): void {
    const { category, location, date } = this.searchFilterForm.value;

    if (category || location || date) {
      const filterData = { category, location, date };
      this.eventService.filterEvents(filterData).subscribe((filteredEvents) => {
        this.events = filteredEvents;
      });
    } else {
      this.eventService.searchEvents(category, location, date).subscribe((searchedEvents) => {
        this.events = searchedEvents;
      });
    }
  }
}
