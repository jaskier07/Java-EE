import {Component, OnInit, ViewChild} from '@angular/core';
import {Beer} from '../../../model/beer';
import {ActivatedRoute, Router} from '@angular/router';
import {BeerService} from '../beer-service';
import {EditEntity} from '../../edit-entity';
import {HateoasUtils} from '../../../utils/hateoas-utils';

@Component({
  selector: 'app-edit-beer',
  templateUrl: './edit-beer.component.html',
  styleUrls: ['./edit-beer.component.css']
})
export class EditBeerComponent extends EditEntity implements OnInit {
  beer: Beer;
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
          this.hateoas.printEntityHeaders(response);
        });
    }
  }

  save() {
    if (this.checkIfAllDataProvided()) {
      this.beerService.saveBeer(this.beer)
        .subscribe(response => {
          this.setInfoLabel(this.DATA_OK);
          this.hateoas.printEntityHeaders(response);
          this.router.navigateByUrl('beers');
        }, error => {
          this.setInfoLabel(this.DATA_ERROR);
        });
    } else {
      this.setInfoLabel(this.DATA_ERROR);
    }
  }

  checkIfAllDataProvided() {
    return this.beer.voltage !== null
      && this.beer.IBU !== null
      && this.utils.notEmpty(this.beer.name);
  }
}
