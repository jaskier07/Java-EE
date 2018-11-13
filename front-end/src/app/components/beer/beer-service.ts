import {HttpClient, HttpResponse} from '@angular/common/http';
import {Beer} from '../../model/beer';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {API_URI} from '../shared-service';


@Injectable()
export class BeerService {
  constructor(private http: HttpClient) {
  }

  findBeer(id: number): Observable<HttpResponse<Beer>> {
    return this.http.get<Beer>(API_URI + `beer/${id}`, {observe: 'response'});
  }

  removeBeer(beer: Beer): Observable<any> {
    return this.http.delete(API_URI + `beer/${beer.id}`, {observe: 'response'});
  }

  saveBeer(beer: Beer): Observable<any> {
    if (beer.id) {
      return this.http.put(API_URI + `beer/${beer.id}`, beer, {observe: 'response'});
    } else {
      return this.http.post(API_URI + 'beer/', beer, {observe: 'response'});
    }
  }
}
