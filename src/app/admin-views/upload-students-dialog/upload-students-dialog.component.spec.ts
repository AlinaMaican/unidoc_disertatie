import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadStudentsDialogComponent } from './upload-students-dialog.component';

describe('UploadStudentsDialogComponent', () => {
  let component: UploadStudentsDialogComponent;
  let fixture: ComponentFixture<UploadStudentsDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UploadStudentsDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UploadStudentsDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
