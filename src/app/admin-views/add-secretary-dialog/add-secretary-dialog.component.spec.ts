import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddSecretaryDialogComponent } from './add-secretary-dialog.component';

describe('AddSecretaryDialogComponent', () => {
  let component: AddSecretaryDialogComponent;
  let fixture: ComponentFixture<AddSecretaryDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddSecretaryDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddSecretaryDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
