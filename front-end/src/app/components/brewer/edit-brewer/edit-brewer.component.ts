import {Component, OnInit, ViewChild} from '@angular/core';
import {Brewer} from '../../../model/brewer';
import {EditEntity} from '../../edit-entity';
import {Beer} from '../../../model/beer';
import {ActivatedRoute, Router} from '@angular/router';
import {BrewerService} from '../brewer-service';
import {SharedService} from '../../shared-service';
import {HateoasUtils} from '../../../utils/hateoas-utils';
import {HeaderUtils} from '../../../utils/header-utils';

@Component({
  selector: 'app-edit-brewer',
  templateUrl: './edit-brewer.component.html',
  styleUrls: ['./edit-brewer.component.css']
})
export class EditBrewerComponent extends EditEntity implements OnInit {

  brewer: Brewer;
  private headerUtils = new HeaderUtils();
  availableBeers: Beer[];
  @ViewChild('errorName') errorName: any;
  @ViewChild('errorAge') errorAge: any;
  @ViewChild('errorBeers') errorBeers: any;

  private hateoas = new HateoasUtils();

  constructor(private brewerService: BrewerService,
              private sharedService: SharedService,
              route: ActivatedRoute,
              private router: Router) {
    super(route);
  }

  ngOnInit() {
    this.initBrewer();
    this.initBeers();
    this.setInfoLabel(this.DATA_OK);
  }

  initBrewer() {
    const brewerId = this.utils.getIdFromRouter(this.route);
    if (!brewerId) {
      this.brewer = new Brewer();
    } else {
      this.brewerService.findBrewer(Number(brewerId))
        .subscribe(response => {
          this.brewer = response.body;
          this.hateoas.printLinks(response);
        }, error => {
          this.headerUtils.handleError(error);
        });
    }
  }

  initBeers() {
    this.sharedService.findAllBeers()
      .subscribe(beers => {
        this.availableBeers = beers;
      }, error => {
        this.headerUtils.handleError(error);
      });
  }

  compareBrewers(brewer1: Brewer, brewer2: Brewer): boolean {
    return brewer1 && brewer2 ? brewer1.id === brewer2.id : brewer1 === brewer2;
  }


  handleError(error: any) {
    this.clearValidationInfos();
    if (error.error['name']) {
      this.errorName.nativeElement.textContent = error.error['name'];
    }
    if (error.error['age']) {
      this.errorAge.nativeElement.textContent = error.error['age'];
    }
    if (error.error['beers']) {
      this.errorBeers.nativeElement.textContent = error.error['beers'];
    }
  }

  clearValidationInfos() {
    this.errorName.nativeElement.textContent = '';
    this.errorAge.nativeElement.textContent = '';
    this.errorBeers.nativeElement.textContent = '';
  }

  save() {
    if (this.validateIntegerInput(this.brewer.age)) {
      this.brewerService.saveBrewer(this.brewer)
        .subscribe(response => {
          this.setInfoLabel(this.DATA_OK);
          this.hateoas.printLinks(response);
          this.router.navigateByUrl('brewers');
        }, error => {
          this.handleError(error);
          this.setInfoLabel(this.DATA_ERROR);
          this.headerUtils.handleError(error);

        });
    } else {
      this.errorAge.nativeElement.textContent = 'Podaj liczbę naturalną.';
    }
  }
}
