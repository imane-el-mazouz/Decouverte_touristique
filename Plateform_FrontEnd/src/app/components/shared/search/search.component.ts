import { Component, OnInit } from '@angular/core';
import { SearchResults } from "../../../dto/search-results.dto";
import { FormBuilder, FormsModule } from "@angular/forms";
import { SearchService } from "../../../service/search/search-service.service";
import {NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-search',
  standalone: true,
  imports: [
    FormsModule,
    NgIf,
    NgForOf
  ],
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss'] // Correction ici, c'était styleUrl
})
export class SearchComponent implements OnInit {
  location: string = '';
  date: string = '';
  category: string = '';
  searchResults: SearchResults | null = null;

  constructor(private fb: FormBuilder, private searchService: SearchService) {}

  onSearch() {
    this.searchService.search(this.location, this.date, this.category).subscribe(
      results => {
        this.searchResults = results;
      },
      error => {
        console.error('Erreur de recherche:', error);
      }
    );
  }

  ngOnInit(): void {
  }
}
