import {HttpClient, HttpParams, HttpResponse} from '@angular/common/http';
import {Beer} from '../../model/beer';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {API_URI} from '../shared-service';
import {AngularUtils} from '../../utils/angular-utils';
import {HeaderUtils} from '../../utils/header-utils';


@Injectable()
export class BeerService {
  private headerUtils = new HeaderUtils();

  constructor(private http: HttpClient) {
  }

  findBeersUsingPagination(from: string, to: string, diff: string): Observable<HttpResponse<Beer[]>> {
    const params = new HttpParams().set('from', from).set('to', to).set('diff', diff);
    return this.http.get<Beer[]>(API_URI + 'beer/all', {
      params: params,
      observe: 'response', headers: this.headerUtils.setSecretAsHeaders()
    });
  }

  filterBeers(id: string, name: string, voltage: string, ibu: string): Observable<HttpResponse<Beer[]>> {
    const params = new HttpParams().set('id', id).set('name', name).set('voltage', voltage).set('ibu', ibu);
    return this.http.get<Beer[]>(API_URI + 'beer/filter', {
      params: params,
      observe: 'response', headers: this.headerUtils.setSecretAsHeaders()
    });
  }


  findBeersUsingPaginationUri(uri: string, diff: string): Observable<HttpResponse<Beer[]>> {
    const params = new HttpParams().set('diff', diff);
    const fineUri = uri.slice(1);
    return this.http.get<Beer[]>(API_URI + fineUri, {
      params: params, observe: 'response', headers: this.headerUtils.setSecretAsHeaders()
    })
      ;
  }

  findBeer(id: number): Observable<HttpResponse<Beer>> {
    return this.http.get<Beer>(API_URI + `beer/${id}`, {
      observe: 'response', headers: this.headerUtils.setSecretAsHeaders()
    })
      ;
  }

  removeBeer(beer: Beer): Observable<any> {
    return this.http.delete(API_URI + `beer/${beer.id}`, {
      observe: 'response', headers: this.headerUtils.setSecretAsHeaders()});
  }

  saveBeer(beer: Beer): Observable<any> {
    if (beer.id) {
      return this.http.put(API_URI + `beer/${beer.id}`, beer, {
        observe: 'response', headers: this.headerUtils.setSecretAsHeaders()}
      );
    } else {
      return this.http.post(API_URI + 'beer/', beer, {
        observe: 'response', headers: this.headerUtils.setSecretAsHeaders()}
      );
    }
  }

}
