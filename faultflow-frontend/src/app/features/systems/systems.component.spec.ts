import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SystemsComponentComponent } from './systems.component';

describe('SystemsComponentComponent', () => {
  let component: SystemsComponentComponent;
  let fixture: ComponentFixture<SystemsComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SystemsComponentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SystemsComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
