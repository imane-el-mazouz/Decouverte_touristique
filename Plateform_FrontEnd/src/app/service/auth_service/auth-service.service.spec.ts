import { TestBed } from '@angular/core/testing';
import {AuthService} from "./auth-service.service";

describe('AuthService', () => {
  let service: AuthService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AuthService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should set and get the token', () => {
    const token = 'test-token';
    service.setToken(token);
    expect(service.getToken()).toBe(token);
  });

  it('should clear the token', () => {
    service.setToken('test-token');
    service.clearToken();
    expect(service.getToken()).toBeNull();
  });
});
