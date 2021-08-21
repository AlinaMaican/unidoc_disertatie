import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangeStatusStudentDocumentDialogComponent } from './change-status-student-document-dialog.component';

describe('ChangeStatusStudentDocumentDialogComponent', () => {
  let component: ChangeStatusStudentDocumentDialogComponent;
  let fixture: ComponentFixture<ChangeStatusStudentDocumentDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChangeStatusStudentDocumentDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChangeStatusStudentDocumentDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
