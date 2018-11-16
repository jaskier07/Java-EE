import {Component, OnInit} from '@angular/core';
import {Brewery} from '../../../model/brewery';
import {BreweryService} from '../brewery-service';
import {ActivatedRoute} from '@angular/router';
import {AngularUtils} from '../../../utils/angular-utils';
import {HateoasUtils} from '../../../utils/hateoas-utils';

@Component({
  selector: 'app-view-brewery',
  templateUrl: './view-brewery.component.html',
  styleUrls: ['./view-brewery.component.css']
})
export class ViewBreweryComponent implements OnInit {

  brewery: Brewery;
  private utils = new AngularUtils();
  private hateoas = new HateoasUtils();

  constructor(private breweryService: BreweryService,
              private route: ActivatedRoute) { }

  ngOnInit() {
    this.breweryService.findBrewery(Number(this.utils.getIdFromRouter(this.route)))
      .subscribe(response => {
        this.brewery = response.body;
        this.hateoas.printLinks(response);
      });
  }
}
