import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {Beer} from '../../../model/beer';
import {BeerService} from '../beer-service';
import {HateoasUtils} from '../../../utils/hateoas-utils';
import {Resource} from '../../../utils/resource';

@Component({
  selector: 'app-list-beers',
  templateUrl: './list-beers.component.html',
  styleUrls: ['./list-beers.component.css']
})
export class ListBeersComponent implements OnInit {

  private DIFF = 5;
  private hateoas = new HateoasUtils();
  from = 0;
  to = this.from + this.DIFF;
  beers: Beer[];
  previousResource: Resource;
  nextResource: Resource;

  constructor(private beerService: BeerService) {
  }

  ngOnInit() {
    this.beerService.findBeersUsingPagination(this.from.toString(), this.to.toString(), this.DIFF.toString())
      .subscribe(response => {
        this.handleResponse(response);
      });
  }

  handleResponse(response) {
    this.hateoas.printLinks(response);
    this.beers = response.body;
    this.previousResource = this.hateoas.createReourceWithHeader(response, 'next');
    this.nextResource = this.hateoas.createReourceWithHeader(response, 'previous');
  }

  remove(beer: Beer) {
    this.beerService.removeBeer(beer)
      .subscribe(() => this.ngOnInit());
  }

  nextElements() {
    this.beerService.findBeersUsingPaginationUri(this.nextResource.uri, this.DIFF.toString())
      .subscribe(response => {
        this.handleResponse(response);
      });
  }

  previousElements() {
    this.beerService.findBeersUsingPaginationUri(this.previousResource.uri, this.DIFF.toString())
      .subscribe(response => {
        this.handleResponse(response);
      });
  }

}
