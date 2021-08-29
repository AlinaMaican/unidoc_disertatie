import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadResponseSecretaryDocumentComponentDialog } from './upload-response-secretary-document.component-dialog';

describe('UploadResponseSecretaryDocumentComponent', () => {
  let component: UploadResponseSecretaryDocumentComponentDialog;
  let fixture: ComponentFixture<UploadResponseSecretaryDocumentComponentDialog>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UploadResponseSecretaryDocumentComponentDialog ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UploadResponseSecretaryDocumentComponentDialog);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
