import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateExcursionComponent } from './update-excursion.component';

describe('UpdateExcursionComponent', () => {
  let component: UpdateExcursionComponent;
  let fixture: ComponentFixture<UpdateExcursionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UpdateExcursionComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(UpdateExcursionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
