import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListBrewersNewestComponent } from './list-brewers-newest.component';

describe('ListBrewersNewestComponent', () => {
  let component: ListBrewersNewestComponent;
  let fixture: ComponentFixture<ListBrewersNewestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListBrewersNewestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListBrewersNewestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
