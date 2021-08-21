import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditSecretaryDocumentDialogComponent } from './edit-secretary-document-dialog.component';

describe('EditSecretaryDocumentDialogComponent', () => {
  let component: EditSecretaryDocumentDialogComponent;
  let fixture: ComponentFixture<EditSecretaryDocumentDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditSecretaryDocumentDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditSecretaryDocumentDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
