// import { Component } from '@angular/core';
//
// @Component({
//   selector: 'app-blog-tradition-component',
//   standalone: true,
//   imports: [],
//   templateUrl: './blog-tradition-component.component.html',
//   styleUrl: './blog-tradition-component.component.css'
// })
// export class BlogTraditionComponentComponent {
//
//   traditions: Tradition[] = [];
//   selectedTraditionId: number | null = null;
//   newBlog: Blog = new Blog();
//   newTradition: Tradition = new Tradition();
//
//   constructor(private blogService: BlogService) { }
//
//   ngOnInit(): void {
//     this.loadTraditions();
//   }
//
//   loadTraditions(): void {
//     this.blogService.getAllTraditions().subscribe(traditions => {
//       this.traditions = traditions;
//     });
//   }
//
//   loadBlogs(traditionId: number): void {
//     this.blogService.getBlogsByTraditionId(traditionId).subscribe(blogs => {
//       this.newBlog = { ...this.newBlog, tradition: this.traditions.find(t => t.id === traditionId)! };
//     });
//   }
//
//   addBlog(): void {
//     if (this.selectedTraditionId !== null) {
//       this.blogService.addBlogToTradition(this.selectedTraditionId, this.newBlog).subscribe(() => {
//         this.loadBlogs(this.selectedTraditionId);
//         this.newBlog = new Blog();  // Clear form
//       });
//     }
//   }
//
//   createTradition(): void {
//     this.blogService.createTraditionWithBlogs(this.newTradition).subscribe(tradition => {
//       this.loadTraditions();
//       this.newTradition = new Tradition();  // Clear form
//     });
//   }
// }
import { Component, OnInit } from '@angular/core';
import {Tradition} from "../../model/tradition/tradition";
import {Blog} from "../../model/blog/blog";
import {TraditionBlogService} from "../../service/blog-service/blog-service.service";
import {FormsModule} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";


@Component({
  selector: 'app-tradition-blog-management',
  templateUrl: './blog-tradition-component.component.html',
  standalone: true,
  imports: [
    FormsModule,
    NgIf,
    NgForOf
  ],
  styleUrls: ['./blog-tradition-component.component.css']
})
export class TraditionBlogManagementComponent implements OnInit {
  traditions: Tradition[] = [];
  selectedTraditionId: number | null = null;
  newTradition: Tradition = new Tradition();
  newBlog: Blog = new Blog();
  blogs: Blog[] = [];

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
    this.service.getBlogsByTraditionId(traditionId).subscribe(
      (data) => {
        this.blogs = data;
      },
      (error) => {
        console.error('Error loading blogs', error);
      }
    );
  }

  createTradition(): void {
    this.service.createTraditionWithBlogs(this.newTradition).subscribe(
      (data) => {
        this.loadTraditions();
        this.newTradition = new Tradition();
      },
      (error) => {
        console.error('Error creating tradition', error);
      }
    );
  }

  addBlogToTradition(): void {
    if (this.selectedTraditionId !== null) {
      this.service.addBlogToTradition(this.selectedTraditionId, this.newBlog).subscribe(
        (data) => {
          this.loadBlogsByTraditionId(this.selectedTraditionId); // Reload blogs after adding a new one
          this.newBlog = new Blog(); // Reset form
        },
        (error) => {
          console.error('Error adding blog', error);
        }
      );
    }
  }
}
