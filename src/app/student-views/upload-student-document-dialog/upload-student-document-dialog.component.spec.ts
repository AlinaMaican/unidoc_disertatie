import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadStudentDocumentDialogComponent } from './upload-student-document-dialog.component';

describe('UploadStudentDocumentDialogComponent', () => {
  let component: UploadStudentDocumentDialogComponent;
  let fixture: ComponentFixture<UploadStudentDocumentDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UploadStudentDocumentDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UploadStudentDocumentDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
