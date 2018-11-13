import {Observable} from 'rxjs';
import {Beer} from '../model/beer';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Brewery} from '../model/brewery';
import {Brewer} from '../model/brewer';

export const API_URI = 'http://localhost:8080/JAX-RX-1.0-SNAPSHOT/api/';

@Injectable()
export class SharedService {
  constructor(private http: HttpClient) {
  }

  findAllBeers(): Observable<Beer[]> {
    return this.http.get<Beer[]>(API_URI + 'beer');
  }


  findAllBreweries(): Observable<Brewery[]> {
    return this.http.get<Brewery[]>(API_URI + 'brewery');
  }

  findAllBrewers(): Observable<Brewer[]> {
    return this.http.get<Brewer[]>(API_URI + 'brewer');
  }
}
