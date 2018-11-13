import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewBrewerComponent } from './view-brewer.component';

describe('ViewBrewerComponent', () => {
  let component: ViewBrewerComponent;
  let fixture: ComponentFixture<ViewBrewerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewBrewerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewBrewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
