/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { CategoryServiceService } from './category.service';

describe('Service: CategoryService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CategoryServiceService]
    });
  });

  it('should ...', inject([CategoryServiceService], (service: CategoryServiceService) => {
    expect(service).toBeTruthy();
  }));
});
