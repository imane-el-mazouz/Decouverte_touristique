// src/app/models/review.model.ts
export interface Review {
  id?: number;
  rating: number;
  comment: string;
  date?: Date;
  client?: { id?: number; email?: string };
  reservation?: { id?: number };
}
