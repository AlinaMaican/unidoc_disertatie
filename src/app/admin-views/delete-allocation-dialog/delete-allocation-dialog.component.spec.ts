import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteAllocationDialogComponent } from './delete-allocation-dialog.component';

describe('DeleteAllocationDialogComponent', () => {
  let component: DeleteAllocationDialogComponent;
  let fixture: ComponentFixture<DeleteAllocationDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DeleteAllocationDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DeleteAllocationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
