import {Component, OnInit, ViewChild} from '@angular/core';
import {Brewery} from '../../../model/brewery';
import {Beer} from '../../../model/beer';
import {BreweryService} from '../brewery-service';
import {ActivatedRoute, Router} from '@angular/router';
import {BeerService} from '../../beer/beer-service';
import {EditEntity} from '../../edit-entity';
import {SharedService} from '../../shared-service';
import {HateoasUtils} from '../../../utils/hateoas-utils';

@Component({
  selector: 'app-edit-brewery',
  templateUrl: './edit-brewery.component.html',
  styleUrls: ['./edit-brewery.component.css']
})
export class EditBreweryComponent extends EditEntity implements OnInit {
  private DATE_TIME = 'T05:00:00';
  private DATE_PATTERN = '[0-9]{4}-[0-9]{2}-[0-9]{2}';
  private DATE_SEPARATOR = 'T';

  brewery: Brewery;
  availableBeers: Beer[];
  // @ViewChild('infoLabel') infoLabel;
  @ViewChild('dateInput') dateInput;
  @ViewChild('errorName') errorName: any;
  @ViewChild('errorEmployees') errorEmployees: any;
  @ViewChild('errorDate') errorDate: any;
  @ViewChild('errorBeers') errorBeers: any;

  private hateoas = new HateoasUtils();

  constructor(private breweryService: BreweryService,
              private sharedService: SharedService,
              private beerService: BeerService,
              route: ActivatedRoute,
              private router: Router) {
    super(route);
  }

  ngOnInit() {
    this.initBrewery();
    this.initBeers();
    this.setInfoLabel(this.DATA_OK);
  }

  handleError(error: any) {
    this.errorName.nativeElement.textContent = '';
    this.errorEmployees.nativeElement.textContent = '';
    this.errorDate.nativeElement.textContent = '';
    this.errorBeers.nativeElement.textContent = '';
    if (error.error['name']) {
      this.errorName.nativeElement.textContent = error.error['name'];
    }
    if (error.error['employees']) {
      this.errorEmployees.nativeElement.textContent = error.error['employees'];
    }
    if (error.error['dateEstablished']) {
      this.errorDate.nativeElement.textContent = error.error['dateEstablished'];
    }
    if (error.error['beers']) {
      this.errorBeers.nativeElement.textContent = error.error['beers'];
    }
  }

  initBrewery() {
    const breweryId = this.utils.getIdFromRouter(this.route);
    if (!breweryId) {
      this.brewery = new Brewery();
    } else {
      this.breweryService.findBrewery(Number(breweryId))
        .subscribe(response => {
          this.brewery = response.body;
          this.hateoas.printLinks(response);
        });
    }
  }

  initBeers() {
    this.sharedService.findAllBeers()
      .subscribe(beers => this.availableBeers = beers);
  }

  save() {
    if (this.validateIntegerInput(this.brewery.employees)) {
      const dateWithoutTime = this.dateInput.nativeElement.value;
      this.brewery.dateEstablished = this.getDateFromInput();
      this.breweryService.saveBrewery(this.brewery)
        .subscribe(response => {
            this.setInfoLabel(this.DATA_OK);
            this.hateoas.printLinks(response);
            this.router.navigateByUrl('');
          }, error => {
            this.setInfoLabel(this.DATA_ERROR);
            this.handleError(error);
            this.brewery.dateEstablished = dateWithoutTime;
          }
        );
    } else {
      this.errorEmployees.nativeElement.textContent = 'Podaj liczbę naturalną.';
    }
  }

  getDateFromInput() {
    const inputValue = this.dateInput.nativeElement.value;
    // return inputValue;
    if (this.validateDateFormat(inputValue)) {
      const date = new Date(inputValue + this.DATE_TIME);
      return (isNaN(date.getTime())) ? null : date;
    } else {
      return null;
    }
  }

  validateDateFormat(date) {
    const validation = date.match(this.DATE_PATTERN);
    return validation;
  }

  getDateEstablished() {
    if (this.utils.isNull(this.brewery.dateEstablished)) {
      return '';
    }
    let date = this.brewery.dateEstablished.toString();
    date = date.split(this.DATE_SEPARATOR, 1)[0];
    return date;
  }

  compareBreweries(brewery1: Brewery, brewery2: Brewery): boolean {
    return brewery1 && brewery2 ? brewery1.id === brewery2.id : brewery1 === brewery2;
  }
}
