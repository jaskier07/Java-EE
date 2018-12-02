import {Component, OnInit, ViewChild} from '@angular/core';
import {Beer} from '../../../model/beer';
import {ActivatedRoute, Router} from '@angular/router';
import {BeerService} from '../beer-service';
import {EditEntity} from '../../edit-entity';
import {HateoasUtils} from '../../../utils/hateoas-utils';
import 'rxjs/add/operator/catch';
import {HeaderUtils} from '../../../utils/header-utils';

@Component({
  selector: 'app-edit-beer',
  templateUrl: './edit-beer.component.html',
  styleUrls: ['./edit-beer.component.css']
})
export class EditBeerComponent extends EditEntity implements OnInit {
  private headerUtils = new HeaderUtils();

  beer: Beer;
  @ViewChild('errorName') errorName: any;
  @ViewChild('errorIbu') errorIbu: any;
  @ViewChild('errorVoltage') errorVoltage: any;

  private hateoas = new HateoasUtils();

  constructor(private beerService: BeerService,
              route: ActivatedRoute,
              private router: Router) {
    super(route);
  }

  ngOnInit() {
    const beerId = this.utils.getIdFromRouter(this.route);
    if (!beerId) {
      this.beer = new Beer();
    } else {
      this.beerService.findBeer(Number(beerId))
        .subscribe(response => {
          this.beer = response.body;
          this.hateoas.printLinks(response);
        }, error => {
          this.headerUtils.handleErrorNoText(error);
        });
    }
  }

  save() {
    if (this.validateIntegerInput(this.beer.IBU)) {
      this.beerService.saveBeer(this.beer)
        .subscribe((response: Response) => {
          this.setInfoLabel(this.DATA_OK);
          this.hateoas.printLinks(response);
          this.router.navigateByUrl('beers');
        }, error => {
          this.handleError(error);
          this.setInfoLabel(this.DATA_ERROR);
          this.headerUtils.handleErrorNoText(error);
        });
    } else {
      this.errorIbu.nativeElement.textContent = 'Podaj liczbę naturalną.';
    }
  }


  handleError(error: any) {
    this.clearValidationInfos();

    if (error.error['name']) {
      this.errorName.nativeElement.textContent = error.error['name'];
    }
    if (error.error['voltage']) {
      this.errorVoltage.nativeElement.textContent = error.error['voltage'];
    }
    if (error.error['IBU']) {
      this.errorIbu.nativeElement.textContent = error.error['IBU'];
    }
  }

  clearValidationInfos() {
    this.errorName.nativeElement.textContent = '';
    this.errorVoltage.nativeElement.textContent = '';
    this.errorIbu.nativeElement.textContent = '';
  }
}
