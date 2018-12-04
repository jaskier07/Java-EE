import { Component, OnInit } from '@angular/core';
import {Observable} from 'rxjs';
import {Brewer} from '../../../model/brewer';
import {SharedService} from '../../shared-service';
import {BrewerService} from '../brewer-service';

@Component({
  selector: 'app-list-brewers-newest',
  templateUrl: './list-brewers-newest.component.html',
  styleUrls: ['./list-brewers-newest.component.css']
})
export class ListBrewersNewestComponent implements OnInit {

  brewers: Observable<Brewer[]>;

  constructor(private brewerService: BrewerService) { }

  ngOnInit() {
    this.brewers = this.brewerService.findBrewersByNewest();
  }

}
