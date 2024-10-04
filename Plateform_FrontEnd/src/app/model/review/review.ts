export interface Review {
  id?: number;
  rating: number;
  comment: string;
  date?: Date;
  client?: { id?: number; email?: string ;name: string;};
  reservation?: { id?: number };
}
