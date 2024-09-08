import { Component } from '@angular/core';

@Component({
  selector: 'app-blog-tradition-component',
  standalone: true,
  imports: [],
  templateUrl: './blog-tradition-component.component.html',
  styleUrl: './blog-tradition-component.component.css'
})
export class BlogTraditionComponentComponent {

  traditions: Tradition[] = [];
  selectedTraditionId: number | null = null;
  newBlog: Blog = new Blog();
  newTradition: Tradition = new Tradition();

  constructor(private blogService: BlogService) { }

  ngOnInit(): void {
    this.loadTraditions();
  }

  loadTraditions(): void {
    this.blogService.getAllTraditions().subscribe(traditions => {
      this.traditions = traditions;
    });
  }

  loadBlogs(traditionId: number): void {
    this.blogService.getBlogsByTraditionId(traditionId).subscribe(blogs => {
      this.newBlog = { ...this.newBlog, tradition: this.traditions.find(t => t.id === traditionId)! };
    });
  }

  addBlog(): void {
    if (this.selectedTraditionId !== null) {
      this.blogService.addBlogToTradition(this.selectedTraditionId, this.newBlog).subscribe(() => {
        this.loadBlogs(this.selectedTraditionId);
        this.newBlog = new Blog();  // Clear form
      });
    }
  }

  createTradition(): void {
    this.blogService.createTraditionWithBlogs(this.newTradition).subscribe(tradition => {
      this.loadTraditions();
      this.newTradition = new Tradition();  // Clear form
    });
  }
}
