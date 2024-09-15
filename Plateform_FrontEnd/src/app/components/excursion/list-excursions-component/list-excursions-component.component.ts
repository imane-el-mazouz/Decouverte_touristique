import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {Excursion} from "../../../model/excursion/excursion";
import {ExcursionService} from "../../../service/excursion-service/excursion-service.service";
import {DtoExcursion} from "../../../dto/excursionDTO/dto-excursion";
import {DatePipe, NgForOf} from "@angular/common";
import {AddExcursionComponentComponent} from "../add-excursion-component/add-excursion-component.component";

@Component({
  selector: 'app-list-excursions-component',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgForOf,
    DatePipe,
    AddExcursionComponentComponent
  ],
  templateUrl: './list-excursions-component.component.html',
  styleUrl: './list-excursions-component.component.css'
})
export class ListExcursionsComponentComponent implements OnInit{
  excursions: DtoExcursion[] = [];
  searchForm: FormGroup;
  filterForm: FormGroup;

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
  }

  ngOnInit(): void {
    this.loadExcursions();
  }

  loadExcursions(): void {
    this.excursionService.getAllExcursions().subscribe(
      data => {
        console.log('Excursion data:', data);
        this.excursions = data;
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

  onUpdate(id: number, updatedExcursion: DtoExcursion): void {
    this.excursionService.updateExcursion(id, updatedExcursion).subscribe(
      response => {
        console.log('Excursion updated!', response);
        this.loadExcursions();
      },
      error => console.error('Error updating excursion', error)
    );
  }
  onDelete(id: number): void {
    this.excursionService.deleteExcursion(id).subscribe(
      () => {
        console.log('Excursion deleted!');
        this.loadExcursions();
      },
      error => console.error('Error deleting excursion', error)
    );
  }
}
