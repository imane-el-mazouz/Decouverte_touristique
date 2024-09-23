//
// import { Component, OnInit } from '@angular/core';
// import { Tradition } from "../../model/tradition/tradition";
// import { Blog } from "../../model/blog/blog";
// import { TraditionBlogService } from "../../service/blog-service/blog-service.service";
// import { FormsModule } from "@angular/forms";
// import { NgForOf, NgIf } from "@angular/common";
//
// @Component({
//   selector: 'app-tradition-blog-management',
//   templateUrl: './blog-tradition-component.component.html',
//   standalone: true,
//   imports: [
//     FormsModule,
//     NgIf,
//     NgForOf
//   ],
//   styleUrls: ['./blog-tradition-component.component.scss']
// })
// export class TraditionBlogManagementComponent implements OnInit {
//   traditions: Tradition[] = [];
//   searchResults: Tradition[] = []; // Property to hold search results
//   selectedTraditionId: number | null = null;
//   newTradition: Tradition = new Tradition();
//   newBlog: Blog = new Blog();
//   blogs: Blog[] = [];
//   city: string = '';
//
//   constructor(private service: TraditionBlogService) {}
//
//   ngOnInit(): void {
//     this.loadTraditions();
//   }
//
//   loadTraditions(): void {
//     this.service.getAllTraditions().subscribe(
//       (data) => {
//         this.traditions = data;
//       },
//       (error) => {
//         console.error('Error loading traditions', error);
//       }
//     );
//   }
//
//   onSelectTradition(traditionId: number): void {
//     this.selectedTraditionId = traditionId;
//     this.loadBlogsByTraditionId(traditionId);
//   }
//
//   loadBlogsByTraditionId(traditionId: number | null): void {
//     if (traditionId !== null) {
//       this.service.getBlogsByTraditionId(traditionId).subscribe(
//         (data) => {
//           this.blogs = data;
//         },
//         (error) => {
//           console.error('Error loading blogs', error);
//         }
//       );
//     }
//   }
//
//   createTradition(): void {
//     console.log('Creating tradition with data:', this.newTradition);
//
//     this.service.createTraditionWithBlogs(this.newTradition).subscribe(
//       (data: any) => {
//         console.log('Tradition created successfully:', data);
//         this.loadTraditions();
//         this.newTradition = new Tradition(); // Clear form
//       },
//       (error: any) => {
//         console.error('Error creating tradition:', error);
//       }
//     );
//   }
//
//
//   addBlogToTradition(): void {
//     if (this.selectedTraditionId !== null) {
//       console.log('Adding blog to tradition:', this.newBlog, 'for tradition ID:', this.selectedTraditionId);
//
//       this.service.addBlogToTradition(this.selectedTraditionId, this.newBlog).subscribe(
//         (data) => {
//           console.log('Blog added successfully:', data);
//           this.loadBlogsByTraditionId(this.selectedTraditionId); // Reload blogs after adding a new one
//           this.newBlog = new Blog(); // Reset form
//         },
//         (error) => {
//           console.error('Error adding blog:', error);
//         }
//       );
//     }
//   }
//
//   searchTraditions(): void {
//     if (this.city) {
//       this.service.searchTraditionsByCity(this.city)
//         .subscribe((data: Tradition[]) => {
//             this.searchResults = data; // Update searchResults instead of traditions
//           },
//           (error) => {
//             console.error('Error searching traditions', error);
//           });
//     }
//   }
// }
import { Component, OnInit } from '@angular/core';
import { Tradition } from "../../model/tradition/tradition";
import { Blog } from "../../model/blog/blog";
import { TraditionBlogService } from "../../service/blog-service/blog-service.service";

import {NgForOf, NgIf} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatCardModule} from "@angular/material/card";
import {MatListModule} from "@angular/material/list";
import {MatDialogModule} from "@angular/material/dialog";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {MatIcon, MatIconModule} from "@angular/material/icon";

@Component({
  selector: 'app-tradition-blog-management',
  templateUrl: './blog-tradition-component.component.html',
  styleUrls: ['./blog-tradition-component.component.scss'],
  standalone: true,
  imports: [

    NgIf,
    NgForOf,

    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatCardModule,
    MatListModule,
    MatDialogModule,
    MatSnackBarModule,
    MatIconModule,
    FormsModule
  ],
})
export class TraditionBlogManagementComponent implements OnInit {
  traditions: Tradition[] = [];
  searchResults: Tradition[] = [];
  selectedTraditionId: number | null = null;
  newTradition: Tradition = new Tradition();
  newBlog: Blog = new Blog();
  blogs: Blog[] = [];
  city: string = '';
  displayAddBlogDialog: boolean = false;

  constructor(private service: TraditionBlogService) {}

  ngOnInit(): void {
    this.loadTraditions();
  }

  loadTraditions(): void {
    this.service.getAllTraditions().subscribe(
      (data) => {
        this.traditions = data;
      },
      (error) => {
        console.error('Error loading traditions', error);
      }
    );
  }

  onSelectTradition(traditionId: number): void {
    this.selectedTraditionId = traditionId;
    this.loadBlogsByTraditionId(traditionId);
  }

  loadBlogsByTraditionId(traditionId: number | null): void {
    if (traditionId !== null) {
      this.service.getBlogsByTraditionId(traditionId).subscribe(
        (data) => {
          this.blogs = data;
        },
        (error) => {
          console.error('Error loading blogs', error);
        }
      );
    }
  }

  createTradition(): void {
    this.service.createTraditionWithBlogs(this.newTradition).subscribe(
      (data: any) => {

        this.loadTraditions();
        this.newTradition = new Tradition();
      },
      (error: any) => {
        console.error('Error creating tradition:', error);
      }
    );
  }

  addBlogToTradition(): void {
    if (this.selectedTraditionId !== null) {
      this.service.addBlogToTradition(this.selectedTraditionId, this.newBlog).subscribe(
        (data) => {
          this.loadBlogsByTraditionId(this.selectedTraditionId);
          this.newBlog = new Blog();
          this.displayAddBlogDialog = false;
        },
        (error) => {
          console.error('Error adding blog:', error);
        }
      );
    }
  }

  searchTraditions(): void {
    if (this.city) {
      this.service.searchTraditionsByCity(this.city)
        .subscribe((data: Tradition[]) => {
            this.searchResults = data;
          },
          (error) => {
            console.error('Error searching traditions', error);
          });
    }
  }

  editTradition(id: number) {

  }

  deleteTradition(id: number) {

  }
}
