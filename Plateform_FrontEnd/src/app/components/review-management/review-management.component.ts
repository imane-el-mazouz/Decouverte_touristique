import { Component, Input, OnInit } from '@angular/core';
import { ReviewService } from '../../service/review-service/review-service.service';
import { Review } from '../../model/review/review';
import { DatePipe, NgForOf, NgIf } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import {TableModule} from "primeng/table";
import {Button, ButtonModule} from "primeng/button";
import {PrimeIcons} from "primeng/api";

@Component({
  selector: 'app-review-management',
  standalone: true,
  imports: [
    DatePipe,
    NgForOf,
    NgIf,
    FormsModule,
    TableModule,
    Button,
    ButtonModule

  ],
  templateUrl: './review-management.component.html',
  styleUrls: ['./review-management.component.css']
})
export class ReviewManagementComponent implements OnInit {
  reviews: Review[] = [];
  actions = [
    {
      label: 'Delete',
      icon: PrimeIcons.TRASH
    }
  ];
  @Input() reservationId!: number;
  review: Review = { rating: 1, comment: '' };
  isEditMode: boolean = false;
  private roomId!: number;

  constructor(
    private reviewService: ReviewService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadReviews();
    this.route.paramMap.subscribe(params => {
      const id = +params.get('id')!;
      if (id) {
        this.isEditMode = true;
        this.reviewService.getReviewById(id).subscribe(
          (review) => this.review = review,
          (error) => console.error('Error loading review', error)
        );
      }
    });
  }

  loadReviews(): void {
    this.reviewService.getAllReviews(this.roomId).subscribe(
      (reviews) => this.reviews = reviews,
      (error) => console.error('Error loading reviews', error)
    );
  }



  deleteReview(id: number | undefined): void {
    if (confirm('Are you sure you want to delete this review?')) {
      this.reviewService.deleteReview(id).subscribe(
        () => {
          this.loadReviews();
          alert('Review deleted successfully.');
        },
        (error) => console.error('Error deleting review', error)
      );
    }
  }
}
