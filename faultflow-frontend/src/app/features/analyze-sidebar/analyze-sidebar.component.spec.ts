import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnalyzeSidebarComponent } from './analyze-sidebar.component';

describe('AnalyzeSidebarComponent', () => {
  let component: AnalyzeSidebarComponent;
  let fixture: ComponentFixture<AnalyzeSidebarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AnalyzeSidebarComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AnalyzeSidebarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
