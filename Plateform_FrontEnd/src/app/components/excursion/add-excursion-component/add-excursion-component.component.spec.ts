import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddExcursionComponentComponent } from './add-excursion-component.component';

describe('AddExcursionComponentComponent', () => {
  let component: AddExcursionComponentComponent;
  let fixture: ComponentFixture<AddExcursionComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddExcursionComponentComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddExcursionComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
