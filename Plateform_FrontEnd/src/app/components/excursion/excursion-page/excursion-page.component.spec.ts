import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExcursionPageComponent } from './excursion-page.component';

describe('ExcursionPageComponent', () => {
  let component: ExcursionPageComponent;
  let fixture: ComponentFixture<ExcursionPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ExcursionPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ExcursionPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
