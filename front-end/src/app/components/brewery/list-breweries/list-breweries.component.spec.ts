import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListBreweriesComponent } from './list-breweries.component';

describe('ListBreweriesComponent', () => {
  let component: ListBreweriesComponent;
  let fixture: ComponentFixture<ListBreweriesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListBreweriesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListBreweriesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
