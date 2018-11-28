import {Observable} from 'rxjs';
import {Beer} from '../model/beer';
import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Brewery} from '../model/brewery';
import {Brewer} from '../model/brewer';
import {HeaderUtils} from '../utils/header-utils';

export const API_URI = 'http://localhost:8080/JAX-RX-1.0-SNAPSHOT/api/';

@Injectable()
export class SharedService {
  private headerUtils = new HeaderUtils();

  constructor(private http: HttpClient) {
  }

  findAllBeers(): Observable<Beer[]> {
    return this.http.get<Beer[]>(API_URI + 'beer', {headers: this.headerUtils.setSecretAsHeaders()});
  }

  findAllBreweries(): Observable<Brewery[]> {
    return this.http.get<Brewery[]>(API_URI + 'brewery', {headers: this.headerUtils.setSecretAsHeaders()});
  }

  findAllBrewers(): Observable<Brewer[]> {
    return this.http.get<Brewer[]>(API_URI + 'brewer', {headers: this.headerUtils.setSecretAsHeaders()});
  }

  login(login: string, password: string) {
    const params = new HttpParams().set('login', login).set('password', password);
    return this.http.post(API_URI + 'login/login', '', {params: params, observe: 'response'})
      .subscribe(response => {
        this.headerUtils.saveSecretToSession(response);
        location.reload(true);
        alert('Zalogowano!');
      }, error => {
        alert('błąd zalogowania!');
        this.headerUtils.handleError(error);
      });
  }

  logout() {

    const params = new HttpParams().set('login', sessionStorage.getItem('login')).set('secret', sessionStorage.getItem('secret'));
    return this.http.post(API_URI + 'login/logout', '', {params: params, observe: 'response'})
      .subscribe(response => {
        sessionStorage.clear();
        location.reload(true);
        alert('Wylogowano!');
      }, error => {
        alert('błąd wylogowania!');
        this.headerUtils.handleError(error);
      });
  }

  changePassword(login: string, oldPsswd: string, newPsswd: string) {
    const params = new HttpParams().set('login', login).set('oldPsswd', oldPsswd).set('newPsswd', newPsswd);
    return this.http.post(API_URI + 'login/password', '', {params: params, observe: 'response'})
      .subscribe(response => {
        this.headerUtils.saveSecretToSession(response);
        alert('Zmieniono!');
        location.reload(true);
      }, error => {
        alert('błąd zmiany hasła! podaj poprawne!');
        this.headerUtils.handleError(error);
      });
  }
}
