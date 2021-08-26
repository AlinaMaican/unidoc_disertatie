import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteSecretaryDialogComponent } from './delete-secretary-dialog.component';

describe('DeleteSecretaryDialogComponent', () => {
  let component: DeleteSecretaryDialogComponent;
  let fixture: ComponentFixture<DeleteSecretaryDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DeleteSecretaryDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DeleteSecretaryDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
