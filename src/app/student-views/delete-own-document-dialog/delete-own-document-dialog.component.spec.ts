import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteOwnDocumentDialogComponent } from './delete-own-document-dialog.component';

describe('DeleteOwnDocumentDialogComponent', () => {
  let component: DeleteOwnDocumentDialogComponent;
  let fixture: ComponentFixture<DeleteOwnDocumentDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DeleteOwnDocumentDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DeleteOwnDocumentDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
