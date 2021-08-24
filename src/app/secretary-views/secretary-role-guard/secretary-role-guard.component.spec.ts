import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SecretaryRoleGuardComponent } from './secretary-role-guard.component';

describe('SecretaryRoleGuardComponent', () => {
  let component: SecretaryRoleGuardComponent;
  let fixture: ComponentFixture<SecretaryRoleGuardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SecretaryRoleGuardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SecretaryRoleGuardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
