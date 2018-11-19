import {Component, OnInit, ViewChild} from '@angular/core';
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
  private DEFAULT_FROM_VALUE = 0;
  private DEFAULT_TO_VALUE = 100;

  @ViewChild("inputFrom") inputFrom: any;
  @ViewChild("inputTo") inputTo: any;

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


  filterByAge() {
    let from = this.inputFrom.nativeElement.value;
    let to = this.inputTo.nativeElement.value;

    if (isNaN(from)) {
      from = this.DEFAULT_FROM_VALUE;
    }
    if (isNaN(to)) {
      to = this.DEFAULT_TO_VALUE;
    }

    this.brewers = this.brewerService.findBrewersByAge(from, to);
  }
}
