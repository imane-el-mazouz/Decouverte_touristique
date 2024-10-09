import {Component, ElementRef, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import { EventService } from '../../../service/event-service/event-service.service';
import { DtoEvent } from '../../../dto/eventDTO/dto-event';
import { CategoryEvent } from '../../../enums/category-event';
import { NgForOf } from "@angular/common";
import {Button} from "primeng/button";
import {DialogModule} from "primeng/dialog";

@Component({
  selector: 'app-event-form-component',
  templateUrl: './event-form-component.component.html',
  standalone: true,
  imports: [
    NgForOf,
    ReactiveFormsModule,
    FormsModule,
    Button,
    DialogModule
  ],
  styleUrls: ['./event-form-component.component.scss']
})
export class EventFormComponentComponent implements OnInit {
  @Output() eventAdded = new EventEmitter<DtoEvent>();
  event: DtoEvent = {
    idEvent: 0,
    name: '',
    description: '',
    imgPath: '',
    date: new Date(),
    location: '',
    capacity: 0,
    price: 0,
    rating: 0,
    distance: 0,
    category: CategoryEvent.Festival,
  };

  categories = Object.values(CategoryEvent);
  visible: boolean = false;

  constructor(private eventService: EventService) {}

  onSubmit() {
    this.eventService.saveEvent(this.event).subscribe(
      response => {
        console.log('Event saved successfully!', response);
        this.eventAdded.emit(response);
        this.resetForm();
      },
      error => {
        console.error('Error saving event:', error);
      }
    );
  }

  ngOnInit(): void {
  }

  resetForm() {
    this.event = {
      idEvent: 0,
      name: '',
      description: '',
      imgPath: '',
      date: new Date(),
      location: '',
      capacity: 0,
      price: 0,
      rating: 0,
      distance: 0,
      category: CategoryEvent.Sports
    };
  }

  showDialog() {
    this.visible = true;
  }
}
