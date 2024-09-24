import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TraditionPageComponent } from './tradition-page.component';

describe('TraditionPageComponent', () => {
  let component: TraditionPageComponent;
  let fixture: ComponentFixture<TraditionPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TraditionPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TraditionPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
