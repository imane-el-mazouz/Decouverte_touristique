import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContactAddComponentComponent } from './contact-add-component.component';

describe('ContactAddComponentComponent', () => {
  let component: ContactAddComponentComponent;
  let fixture: ComponentFixture<ContactAddComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ContactAddComponentComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ContactAddComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
