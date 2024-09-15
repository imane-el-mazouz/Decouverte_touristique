import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { EventService } from '../../../service/event-service/event-service.service';
import { DtoEvent } from '../../../dto/eventDTO/dto-event';
import { CategoryEvent } from '../../../enums/category-event';
import { NgForOf } from "@angular/common";

@Component({
  selector: 'app-event-form-component',
  templateUrl: './event-form-component.component.html',
  standalone: true,
  imports: [
    NgForOf,
    ReactiveFormsModule
  ],
  styleUrls: ['./event-form-component.component.css']
})
export class EventFormComponentComponent implements OnInit {
  eventForm: FormGroup;
  categories = Object.values(CategoryEvent);
  fileToUpload: File | null = null;
  @ViewChild('fileToUpload') fileInput!: ElementRef<HTMLInputElement>; 

  constructor(
    private fb: FormBuilder,
    private eventService: EventService
  ) {
    this.eventForm = this.fb.group({
      name: ['', Validators.required],
      description: [''],
      imgPath: [''],
      date: ['', Validators.required],
      location: [''],
      capacity: [0, Validators.required],
      price: [0, Validators.required],
      rating: [0],
      distance: [0],
      category: ['Sports']
    });
  }

  ngOnInit(): void {}

  onFileChange(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.fileToUpload = file;
    }
  }

  submitForm(): void {
    if (this.eventForm.valid) {
      const inputFileElement = this.fileInput.nativeElement.files;
      if (inputFileElement && inputFileElement.length > 0) {
        this.eventService.uploadImage(inputFileElement[0]).subscribe({
          next: (imageUrl: string) => {
            const eventDTO: DtoEvent = { ...this.eventForm.value, imgPath: imageUrl };
            this.createEvent(eventDTO);
          },
          error: (err) => {
            console.error('Error uploading image', err);
          }
        });
      } else {
        const eventDTO: DtoEvent = this.eventForm.value;
        this.createEvent(eventDTO);
      }
    } else {
      console.error('Form is invalid');
    }
  }

  private createEvent(eventDTO: DtoEvent) {
    const inputFileElement = this.fileInput.nativeElement.files;
    this.eventService.createEvent(eventDTO, inputFileElement ? inputFileElement[0] : null).subscribe({
      next: () => {
        console.log('Event created successfully');
        this.eventForm.reset();
        this.fileInput.nativeElement.value = '';  // Reset file input
      },
      error: (err) => {
        console.error('Error creating event', err);
      }
    });
  }
}
