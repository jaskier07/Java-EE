import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListBreweriesComponent} from './components/brewery/list-breweries/list-breweries.component';
import {ViewBreweryComponent} from './components/brewery/view-brewery/view-brewery.component';
import {EditBreweryComponent} from './components/brewery/edit-brewery/edit-brewery.component';
import {ListBeersComponent} from './components/beer/list-beers/list-beers.component';
import {ViewBeerComponent} from './components/beer/view-beer/view-beer.component';
import {EditBeerComponent} from './components/beer/edit-beer/edit-beer.component';
import {ListBrewersComponent} from './components/brewer/list-brewers/list-brewers.component';
import {ViewBrewerComponent} from './components/brewer/view-brewer/view-brewer.component';
import {EditBrewerComponent} from './components/brewer/edit-brewer/edit-brewer.component';
import {ManageAccountComponent} from './components/manage-account/manage-account.component';
import {LoginPageComponent} from './components/login-page/login-page.component';

const routes: Routes = [
  {path: '', component: ListBreweriesComponent},
  {path: 'breweries/new', component: EditBreweryComponent},
  {path: 'breweries/:id', component: ViewBreweryComponent},
  {path: 'breweries/:id/edit', component: EditBreweryComponent},

  {path: 'beers', component: ListBeersComponent},
  {path: 'beers/new', component: EditBeerComponent},
  {path: 'beers/:id', component: ViewBeerComponent},
  {path: 'beers/:id/edit', component: EditBeerComponent},

  {path: 'brewers', component: ListBrewersComponent},
  {path: 'brewers/new', component: EditBrewerComponent},
  {path: 'brewers/:id', component: ViewBrewerComponent},
  {path: 'brewers/:id/edit', component: EditBrewerComponent},

  {path: 'manage', component: ManageAccountComponent},
  {path: 'login', component: LoginPageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
