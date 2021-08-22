import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewStudentDocumentDialogComponent } from './view-student-document-dialog.component';

describe('ViewStudentDocumentDialogComponent', () => {
  let component: ViewStudentDocumentDialogComponent;
  let fixture: ComponentFixture<ViewStudentDocumentDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewStudentDocumentDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewStudentDocumentDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
