import { TestBed } from '@angular/core/testing';

import { ExcursionServiceService } from './excursion-service.service';

describe('ExcursionServiceService', () => {
  let service: ExcursionServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ExcursionServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
