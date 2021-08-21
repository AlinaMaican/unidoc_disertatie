import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadSecretaryDocumentDialogComponent } from './upload-secretary-document-dialog.component';

describe('UploadSecretaryDocumentDialogComponent', () => {
  let component: UploadSecretaryDocumentDialogComponent;
  let fixture: ComponentFixture<UploadSecretaryDocumentDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UploadSecretaryDocumentDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UploadSecretaryDocumentDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
