import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppComponent} from './components/app/app.component';
import {AppRoutingModule} from './app-routing.module';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {ListBreweriesComponent} from './components/brewery/list-breweries/list-breweries.component';
import {ViewBreweryComponent} from './components/brewery/view-brewery/view-brewery.component';
import {EditBreweryComponent} from './components/brewery/edit-brewery/edit-brewery.component';
import {ListBeersComponent} from './components/beer/list-beers/list-beers.component';
import {ViewBeerComponent} from './components/beer/view-beer/view-beer.component';
import {EditBeerComponent} from './components/beer/edit-beer/edit-beer.component';
import {BeerService} from './components/beer/beer-service';
import {BreweryService} from './components/brewery/brewery-service';
import {SharedService} from './components/shared-service';
import {ListBrewersComponent} from './components/brewer/list-brewers/list-brewers.component';
import {BrewerService} from './components/brewer/brewer-service';
import {ViewBrewerComponent} from './components/brewer/view-brewer/view-brewer.component';
import {EditBrewerComponent} from './components/brewer/edit-brewer/edit-brewer.component';
import {LoginPageComponent} from './components/login-page/login-page.component';
import {AccountService} from './components/login-page/account-service';
import {ListBrewersNewestComponent} from './components/brewer/list-brewers-newest/list-brewers-newest.component';


@NgModule({
  declarations: [
    AppComponent,
    ListBreweriesComponent,
    ViewBreweryComponent,
    EditBreweryComponent,
    ListBeersComponent,
    ViewBeerComponent,
    EditBeerComponent,
    ListBrewersComponent,
    ViewBrewerComponent,
    EditBrewerComponent,
    LoginPageComponent,
    ListBrewersNewestComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    AppRoutingModule
  ],
  providers: [
    BreweryService,
    BeerService,
    SharedService,
    BrewerService,
    AccountService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
