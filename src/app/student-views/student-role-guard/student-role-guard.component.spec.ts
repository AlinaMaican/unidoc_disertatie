import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentRoleGuardComponent } from './student-role-guard.component';

describe('StudentRoleGuardComponent', () => {
  let component: StudentRoleGuardComponent;
  let fixture: ComponentFixture<StudentRoleGuardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StudentRoleGuardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StudentRoleGuardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
