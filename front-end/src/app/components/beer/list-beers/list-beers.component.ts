import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {Beer} from '../../../model/beer';
import {BeerService} from '../beer-service';
import {SharedService} from '../../shared-service';

@Component({
  selector: 'app-list-beers',
  templateUrl: './list-beers.component.html',
  styleUrls: ['./list-beers.component.css']
})
export class ListBeersComponent implements OnInit {

  beers: Observable<Beer[]>;

  constructor(private beerService: BeerService,
              private sharedService: SharedService) {
  }

  ngOnInit() {
    this.beers = this.sharedService.findAllBeers();
  }

  remove(beer: Beer) {
    this.beerService.removeBeer(beer)
      .subscribe(() => this.ngOnInit());
  }
}
