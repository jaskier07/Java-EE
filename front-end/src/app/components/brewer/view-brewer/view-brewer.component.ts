import { Component, OnInit } from '@angular/core';
import {Brewer} from '../../../model/brewer';
import {BrewerService} from '../brewer-service';
import {ActivatedRoute} from '@angular/router';
import {AngularUtils} from '../../../utils/angular-utils';
import {HateoasUtils} from '../../../utils/hateoas-utils';

@Component({
  selector: 'app-view-brewer',
  templateUrl: './view-brewer.component.html',
  styleUrls: ['./view-brewer.component.css']
})
export class ViewBrewerComponent implements OnInit {

  brewer: Brewer;
  private utils = new AngularUtils();
  private hateoas = new HateoasUtils();

  constructor(private brewerService: BrewerService,
              private route: ActivatedRoute) { }

  ngOnInit() {
    this.brewerService.findBrewer(this.utils.getIdFromRouter(this.route))
    .subscribe(response => {
      this.brewer = response.body;
      this.hateoas.printEntityHeaders(response);
    });
  }

}
