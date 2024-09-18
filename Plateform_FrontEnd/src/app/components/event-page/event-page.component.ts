import { Component } from '@angular/core';
import {FooterComponent} from "../shared/footer/footer.component";
import {NavBarComponent} from "../shared/nav-bar/nav-bar.component";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-event-page',
  standalone: true,
  templateUrl: './event-page.component.html',
  imports: [
    FooterComponent,
    NavBarComponent,
    NgForOf
  ],
  styleUrls: ['./event-page.component.scss']
})
export class EventPageComponent {
  currentPage = 1;
  iconsPerPage = 2;
  icons = [
    'https://cdn.builder.io/api/v1/image/assets/TEMP/0be4f5ae517fb455396a4b0d7af4bc7bf793de43906db4c47433085ebb2d9941?placeholderIfAbsent=true&apiKey=af169940446d48718e0b8026890b04d0',
    'https://cdn.builder.io/api/v1/image/assets/TEMP/363ec222dcaa41986982fe7d4fe42c6754d8b686330570a3039ec56d3f6faf61?placeholderIfAbsent=true&apiKey=af169940446d48718e0b8026890b04d0',
    'https://cdn.builder.io/api/v1/image/assets/TEMP/b52887050f70a579d63d67c8a54c32bfc221f1888c33da90bb32617a157b0c51?placeholderIfAbsent=true&apiKey=af169940446d48718e0b8026890b04d0',
    'https://cdn.builder.io/api/v1/image/assets/TEMP/1aa94dd5dbd1c62c4dcbe90535e72c08d66364c14f02b9335869d28aabcc38cb?placeholderIfAbsent=true&apiKey=af169940446d48718e0b8026890b04d0'
  ];

  get totalPages(): number {
    return Math.ceil(this.icons.length / this.iconsPerPage);
  }

  getVisibleIcons(): string[] {
    const startIndex = (this.currentPage - 1) * this.iconsPerPage;
    return this.icons.slice(startIndex, startIndex + this.iconsPerPage);
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
    }
  }

  prevPage(): void {
    if (this.currentPage > 1) {
      this.currentPage--;
    }
  }

  isNextDisabled(): boolean {
    return this.currentPage >= this.totalPages;
  }

  isPrevDisabled(): boolean {
    return this.currentPage <= 1;
  }
}
