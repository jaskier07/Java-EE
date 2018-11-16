import {Component, OnInit} from '@angular/core';
import {Beer} from '../../../model/beer';
import {BeerService} from '../beer-service';
import {ActivatedRoute} from '@angular/router';
import {AngularUtils} from '../../../utils/angular-utils';
import {HateoasUtils} from '../../../utils/hateoas-utils';

@Component({
  selector: 'app-view-beer',
  templateUrl: './view-beer.component.html',
  styleUrls: ['./view-beer.component.css']
})
export class ViewBeerComponent implements OnInit {

  beer: Beer;
  private utils = new AngularUtils();
  private hateoas = new HateoasUtils();

  constructor(private beerService: BeerService,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.beerService.findBeer(Number(this.utils.getIdFromRouter(this.route)))
      .subscribe(response => {
        this.beer = response.body;
        this.hateoas.printLinks(response);
      });
  }


}
