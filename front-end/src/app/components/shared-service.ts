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

}
