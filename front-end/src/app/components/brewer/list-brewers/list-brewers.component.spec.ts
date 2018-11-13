import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListBrewersComponent } from './list-brewers.component';

describe('ListBrewersComponent', () => {
  let component: ListBrewersComponent;
  let fixture: ComponentFixture<ListBrewersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListBrewersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListBrewersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
