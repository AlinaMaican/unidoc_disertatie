import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SecretaryAllocationsDialogComponent } from './secretary-allocations-dialog.component';

describe('SecretaryAllocationsDialogComponent', () => {
  let component: SecretaryAllocationsDialogComponent;
  let fixture: ComponentFixture<SecretaryAllocationsDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SecretaryAllocationsDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SecretaryAllocationsDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
