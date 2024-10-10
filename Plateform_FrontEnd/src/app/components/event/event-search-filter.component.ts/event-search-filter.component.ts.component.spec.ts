import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EventSearchFilterComponent } from './event-search-filter.component.ts.component';

describe('EventSearchFilterComponentTsComponent', () => {
  let component: EventSearchFilterComponent;
  let fixture: ComponentFixture<EventSearchFilterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EventSearchFilterComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EventSearchFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
