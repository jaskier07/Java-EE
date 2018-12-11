import {Component, OnInit, ViewChild} from '@angular/core';
import {Beer} from '../../../model/beer';
import {BeerService} from '../beer-service';
import {HateoasUtils} from '../../../utils/hateoas-utils';
import {Resource} from '../../../utils/resource';
import {HeaderUtils} from '../../../utils/header-utils';

@Component({
  selector: 'app-list-beers',
  templateUrl: './list-beers.component.html',
  styleUrls: ['./list-beers.component.css']
})
export class ListBeersComponent implements OnInit {
  @ViewChild('idInput') idInput: any;
  @ViewChild('nameInput') nameInput: any;
  @ViewChild('voltageInput') voltageInput: any;
  @ViewChild('ibuInput') ibuInput: any;

  private headerUtils = new HeaderUtils();
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

  filter() {
    this.beerService.filterBeers(this.idInput.nativeElement.value,
      this.nameInput.nativeElement.value,
      this.voltageInput.nativeElement.value,
      this.ibuInput.nativeElement.value)
      .subscribe(response => {
        this.beers = response.body;
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
      .subscribe(() => this.ngOnInit(), error => {
        this.headerUtils.handleErrorNoText(error);
      });
  }

  nextElements() {
    this.beerService.findBeersUsingPaginationUri(this.nextResource.uri, this.DIFF.toString())
      .subscribe(response => {
        this.handleResponse(response);
      }, error => {
        this.headerUtils.handleErrorNoText(error);
      });
  }

  previousElements() {
    this.beerService.findBeersUsingPaginationUri(this.previousResource.uri, this.DIFF.toString())
      .subscribe(response => {
        this.handleResponse(response);
      }, error => {
        this.headerUtils.handleErrorNoText(error);
      });
  }

  clearFilters() {
    console.log(this.idInput);
    this.idInput.nativeElement.value = '';
    this.nameInput.nativeElement.value = '';
    this.voltageInput.nativeElement.value = '';
    this.ibuInput.nativeElement.value = '';
    this.ngOnInit();
  }
}
