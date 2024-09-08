import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {ExcursionService} from "../../../service/excursion-service/excursion-service.service";

@Component({
  selector: 'app-add-excursion-component',
  standalone: true,
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './add-excursion-component.component.html',
  styleUrl: './add-excursion-component.component.css'
})
export class AddExcursionComponentComponent {
  excursionForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private excursionService: ExcursionService
  ) {
    this.excursionForm = this.fb.group({
      capacity: ['', Validators.required],
      name: ['', Validators.required],
      description: ['', Validators.required],
      dateTime: ['', Validators.required],
      location: ['', Validators.required],
      rating: [''],
    });

  }

  ngOnInit(): void {
        throw new Error('Method not implemented.');
    }

  onFileChange(event: any) {
    if (event.target.files && event.target.files[0]) {
      const file = event.target.files[0];
      this.excursionForm.patchValue({
        imgPath: file
      });
    }
  }


  onSubmit() {
    if (this.excursionForm.valid) {
      const formData = new FormData();
      for (const key of Object.keys(this.excursionForm.value)) {
        formData.append(key, this.excursionForm.value[key]);
      }

      if (this.excursionForm.get('imgPath')?.value) {
        formData.append('imgPath', this.excursionForm.get('imgPath')?.value);
      }

      this.excursionService.addExcursion(formData).subscribe(
        (response: any) => console.log('Excursion added!', response),
        (error: any) => console.error('Error adding excursion', error)
      );
    }
  }

}
