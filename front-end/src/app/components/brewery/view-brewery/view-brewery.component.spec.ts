import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewBreweryComponent } from './view-brewery.component';

describe('ViewBreweryComponent', () => {
  let component: ViewBreweryComponent;
  let fixture: ComponentFixture<ViewBreweryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewBreweryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewBreweryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
