import { Component, OnInit } from '@angular/core';
import {Observable} from 'rxjs';
import {Brewer} from '../../../model/brewer';
import {BrewerService} from '../brewer-service';
import {SharedService} from '../../shared-service';

@Component({
  selector: 'app-list-brewers',
  templateUrl: './list-brewers.component.html',
  styleUrls: ['./list-brewers.component.css']
})
export class ListBrewersComponent implements OnInit {

  brewers: Observable<Brewer[]>;

  constructor(private sharedService: SharedService,
              private brewerService: BrewerService) { }

  ngOnInit() {
    this.brewers = this.sharedService.findAllBrewers();
  }

  remove(brewer: Brewer) {
    this.brewerService.removeBrewer(brewer)
      .subscribe(() => this.ngOnInit());
  }

}
