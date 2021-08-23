import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadOwnDocumentDialogComponent } from './upload-own-document-dialog.component';

describe('UploadOwnDocumentDialogComponent', () => {
  let component: UploadOwnDocumentDialogComponent;
  let fixture: ComponentFixture<UploadOwnDocumentDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UploadOwnDocumentDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UploadOwnDocumentDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
