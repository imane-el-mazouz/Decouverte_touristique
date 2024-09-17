import { Component, Input, OnInit } from '@angular/core';
import { ReviewService } from '../../service/review-service/review-service.service';
import { Review } from '../../model/review/review';
import { DatePipe, NgForOf, NgIf } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-review-management',
  standalone: true,
  imports: [
    DatePipe,
    NgForOf,
    NgIf,
    FormsModule
  ],
  templateUrl: './review-management.component.html',
  styleUrls: ['./review-management.component.css']
})
export class ReviewManagementComponent implements OnInit {
  reviews: Review[] = [];
  @Input() reservationId!: number;
  review: Review = { rating: 1, comment: '' };
  isEditMode: boolean = false;

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
    this.reviewService.getAllReviews().subscribe(
      (reviews) => this.reviews = reviews,
      (error) => console.error('Error loading reviews', error)
    );
  }

  saveReview(): void {
    if (this.isEditMode) {
      // Implement update review functionality if needed
    } else {
      this.reviewService.addReview(this.reservationId, this.review).subscribe(
        () => this.router.navigate(['/reviews']),
        (error) => console.error('Error saving review', error)
      );
    }
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
