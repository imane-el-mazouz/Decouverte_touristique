import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EventSearchFilterComponentTsComponent } from './event-search-filter.component.ts.component';

describe('EventSearchFilterComponentTsComponent', () => {
  let component: EventSearchFilterComponentTsComponent;
  let fixture: ComponentFixture<EventSearchFilterComponentTsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EventSearchFilterComponentTsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EventSearchFilterComponentTsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
