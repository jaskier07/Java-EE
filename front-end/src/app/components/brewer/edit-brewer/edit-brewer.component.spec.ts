import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditBrewerComponent } from './edit-brewer.component';

describe('EditBrewerComponent', () => {
  let component: EditBrewerComponent;
  let fixture: ComponentFixture<EditBrewerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditBrewerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditBrewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
