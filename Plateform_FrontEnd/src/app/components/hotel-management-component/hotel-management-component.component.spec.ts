import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HotelManagementComponentComponent } from './hotel-management-component.component';

describe('HotelManagementComponentComponent', () => {
  let component: HotelManagementComponentComponent;
  let fixture: ComponentFixture<HotelManagementComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HotelManagementComponentComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(HotelManagementComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
