import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {NavBarComponent} from "../shared/nav-bar/nav-bar.component";
import {CurrencyPipe, NgForOf, NgIf} from "@angular/common";
import {FooterComponent} from "../shared/footer/footer.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TraditionBlogService} from "../../service/blog-service/blog-service.service";

@Component({
  selector: 'app-tradition-page',
  standalone: true,
  imports: [
    NavBarComponent,
    NgForOf,
    FooterComponent,
    FormsModule,
    NgIf,
    ReactiveFormsModule,
    CurrencyPipe
  ],
  templateUrl: './tradition-page.component.html',
  styleUrl: './tradition-page.component.scss'
})
export class TraditionPageComponent implements OnInit {
  @Input() events: any[] = [];
  filteredEvents: any[] = [];
  city: string = '';

  @Output() searchEvent = new EventEmitter<string>();

  constructor(private traditionService: TraditionBlogService) {
  }

  ngOnInit(): void {
    this.filteredEvents = this.events;
  }

  filterEvents(searchTerm: string): void {
    this.filteredEvents = this.events.filter(event =>
      event.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
      event.location.toLowerCase().includes(searchTerm.toLowerCase())
    );
  }

  filterByCity(city: string): void {
    this.filteredEvents = this.events.filter(event =>
      event.location.toLowerCase().includes(city.toLowerCase())
    );
  }

  onSearch(event: any): void {
    const searchTerm = event.target.value;
    this.searchEvent.emit(searchTerm);
    this.filterEvents(searchTerm);
  }

  onCityChange(event: any): void {
    const city = event.target.value;
    this.city = city;
    this.filterByCity(city);
  }

}
