
import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { ExcursionService } from '../../../service/excursion-service/excursion-service.service';
import {Router} from "@angular/router";
import {NgIf} from "@angular/common";
import {DtoExcursion} from "../../../dto/excursionDTO/dto-excursion";
import {DialogModule} from "primeng/dialog";
import {Button} from "primeng/button";

@Component({
  selector: 'app-add-excursion-component',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgIf,
    DialogModule,
    Button
  ],
  templateUrl: './add-excursion-component.component.html',
  styleUrl: './add-excursion-component.component.css'
})
export class AddExcursionComponentComponent implements OnInit {
  excursionForm: FormGroup;
  selectedFile: File | null = null;
  @Output() excursionAdded = new EventEmitter<DtoExcursion>();
  visible: boolean = false;

   constructor(
    private fb: FormBuilder,
    private excursionService: ExcursionService,
    private router: Router

  ) {
    this.excursionForm = this.fb.group({
      name: ['', Validators.required],
      description: [''],
      imgPath: ['', Validators.required],
      dateTime: ['', Validators.required],
      location: ['', Validators.required],
      capacity: ['', [Validators.required, Validators.min(1)]],
      rating: ['', [Validators.required, Validators.min(0), Validators.max(5)]]
    });
  }


  addExcursion() {
    if (this.excursionForm.valid) {
      this.excursionService.addExcursion(this.excursionForm.value).subscribe({
        next: (response) => {
          this.excursionAdded.emit(response);
          alert('Excursion added successfully!');
        },
        error: (err) => {
          console.error('Error adding excursion', err);
          alert('There was an error adding the excursion. Please try again.');
        }
      });
    }
  }


  ngOnInit(): void {
  }

  showDialog() {
    this.visible = true;
  }
}
