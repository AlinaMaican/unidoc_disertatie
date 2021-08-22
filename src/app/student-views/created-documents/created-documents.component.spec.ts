import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreatedDocumentsComponent } from './created-documents.component';

describe('CreatedDocumentsComponent', () => {
  let component: CreatedDocumentsComponent;
  let fixture: ComponentFixture<CreatedDocumentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreatedDocumentsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreatedDocumentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
