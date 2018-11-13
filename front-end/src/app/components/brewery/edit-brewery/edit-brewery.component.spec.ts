import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditBreweryComponent } from './edit-brewery.component';

describe('EditBreweryComponent', () => {
  let component: EditBreweryComponent;
  let fixture: ComponentFixture<EditBreweryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditBreweryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditBreweryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
