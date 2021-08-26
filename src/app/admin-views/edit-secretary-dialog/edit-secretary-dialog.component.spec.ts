import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditSecretaryDialogComponent } from './edit-secretary-dialog.component';

describe('EditSecretaryDialogComponent', () => {
  let component: EditSecretaryDialogComponent;
  let fixture: ComponentFixture<EditSecretaryDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditSecretaryDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditSecretaryDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
