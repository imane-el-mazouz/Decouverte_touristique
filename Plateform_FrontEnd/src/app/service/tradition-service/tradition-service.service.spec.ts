import { TestBed } from '@angular/core/testing';

import { TraditionServiceService } from './tradition-service.service';

describe('TraditionServiceService', () => {
  let service: TraditionServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TraditionServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
