import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SecretaryNotificationsComponent } from './secretary-notifications.component';

describe('SecretaryNotificationsComponent', () => {
  let component: SecretaryNotificationsComponent;
  let fixture: ComponentFixture<SecretaryNotificationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SecretaryNotificationsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SecretaryNotificationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
