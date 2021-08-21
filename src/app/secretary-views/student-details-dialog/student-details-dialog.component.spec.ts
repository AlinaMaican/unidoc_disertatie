import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentDetailsDialogComponent } from './student-details-dialog.component';

describe('StudentDetailsDialogComponent', () => {
  let component: StudentDetailsDialogComponent;
  let fixture: ComponentFixture<StudentDetailsDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StudentDetailsDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StudentDetailsDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
