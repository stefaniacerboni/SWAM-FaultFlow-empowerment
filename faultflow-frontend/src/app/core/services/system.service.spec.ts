import { TestBed } from '@angular/core/testing';

import { SystemsService } from './systems.service';

describe('SystemService', () => {
  let service: SystemsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SystemsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
