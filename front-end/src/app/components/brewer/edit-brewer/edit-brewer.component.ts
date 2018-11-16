import {Component, OnInit} from '@angular/core';
import {Brewer} from '../../../model/brewer';
import {EditEntity} from '../../edit-entity';
import {Beer} from '../../../model/beer';
import {ActivatedRoute, Router} from '@angular/router';
import {BrewerService} from '../brewer-service';
import {SharedService} from '../../shared-service';
import {HateoasUtils} from '../../../utils/hateoas-utils';

@Component({
  selector: 'app-edit-brewer',
  templateUrl: './edit-brewer.component.html',
  styleUrls: ['./edit-brewer.component.css']
})
export class EditBrewerComponent extends EditEntity implements OnInit {

  brewer: Brewer;
  availableBeers: Beer[];
  private hateoas = new HateoasUtils();

  constructor(private brewerService: BrewerService,
              private sharedService: SharedService,
              route: ActivatedRoute,
              private router: Router) {
    super(route);
  }

  ngOnInit(  ) {
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
        });
    }
  }

  initBeers() {
    this.sharedService.findAllBeers()
      .subscribe(beers => {
        this.availableBeers = beers;
        console.log(beers);
      });
  }

  compareBrewers(brewer1: Brewer, brewer2: Brewer): boolean {
    return brewer1 && brewer2 ? brewer1.id === brewer2.id : brewer1 === brewer2;
  }

  checkIfAllDataProvided() {
    return this.brewer.age !== null
    && this.utils.notEmpty(this.brewer.name);
  }

  save() {
    if (this.checkIfAllDataProvided()) {
      this.brewerService.saveBrewer(this.brewer)
        .subscribe(response => {
          this.setInfoLabel(this.DATA_OK);
          this.hateoas.printLinks(response);
          this.router.navigateByUrl('brewers');
        }, error => {
          this.setInfoLabel(this.DATA_ERROR);
        });
    } else {
      this.setInfoLabel(this.DATA_ERROR);
    }
  }
}
