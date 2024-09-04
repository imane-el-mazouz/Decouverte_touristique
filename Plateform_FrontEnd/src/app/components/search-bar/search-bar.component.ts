import { Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {SearchResults} from "../../model/search/search-results";
import {SearchService} from "../../service/search/search-service.service";

@Component({
  selector: 'app-search-bar',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgForOf,
    NgIf
  ],
  templateUrl: './search-bar.component.html',
  styleUrl: './search-bar.component.css'
})
export class SearchBarComponent {
  searchForm: FormGroup;
  searchResults: SearchResults | null = null;

  constructor(private fb: FormBuilder, private searchService: SearchService) {
    this.searchForm = this.fb.group({
      location: [''],
      date: [''],
      category: ['']
    });
  }

  onSubmit(): void {
    const { location, date, category } = this.searchForm.value;

    this.searchService.search(location, date, category).subscribe(
      (results) => {
        this.searchResults = results;
      },
      (error) => {
        console.error('Error fetching search results:', error);
      }
    );
  }

}
