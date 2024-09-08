import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BlogTraditionComponentComponent } from './blog-tradition-component.component';

describe('BlogTraditionComponentComponent', () => {
  let component: BlogTraditionComponentComponent;
  let fixture: ComponentFixture<BlogTraditionComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BlogTraditionComponentComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BlogTraditionComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
