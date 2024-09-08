import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListExcursionsComponentComponent } from './list-excursions-component.component';

describe('ListExcursionsComponentComponent', () => {
  let component: ListExcursionsComponentComponent;
  let fixture: ComponentFixture<ListExcursionsComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListExcursionsComponentComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ListExcursionsComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
