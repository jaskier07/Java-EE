import { Component, OnInit } from '@angular/core';
import {Observable} from 'rxjs';
import {Brewery} from '../../../model/brewery';
import {BreweryService} from '../brewery-service';
import {SharedService} from '../../shared-service';

@Component({
  selector: 'app-list-breweries',
  templateUrl: './list-breweries.component.html',
  styleUrls: ['./list-breweries.component.css']
})
export class ListBreweriesComponent implements OnInit {

  breweries: Observable<Brewery[]>;

  constructor(private breweryService: BreweryService,
              private sharedService: SharedService) { }

  ngOnInit() {
    this.breweries = this.sharedService.findAllBreweries();
  }

  remove(brewery: Brewery) {
    this.breweryService.removeBrewery(brewery)
      .subscribe(() => this.ngOnInit());
  }
}
