import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewBeerComponent } from './view-beer.component';

describe('ViewBeerComponent', () => {
  let component: ViewBeerComponent;
  let fixture: ComponentFixture<ViewBeerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewBeerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewBeerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
