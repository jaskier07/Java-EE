import {Injectable} from '@angular/core';
import {HttpClient, HttpParams, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {API_URI} from '../shared-service';
import {Brewer} from '../../model/brewer';
import {HeaderUtils} from '../../utils/header-utils';
import {Beer} from '../../model/beer';

@Injectable()
export class BrewerService {
  private headerUtils = new HeaderUtils();

  constructor(private http: HttpClient) {
  }

  findBrewer(id: number): Observable<HttpResponse<Brewer>> {

    return this.http.get<Brewer>(API_URI + `brewer/${id}`, {
      observe: 'response',
      headers: this.headerUtils.setSecretAsHeaders()
    });
  }

  findBrewersByNewest():  Observable<Brewer[]> {
    return this.http.get<Brewer[]>(API_URI + 'brewer/getByNewest', {
      headers: this.headerUtils.setSecretAsHeaders()
    });
  }

  findBrewersByAge(from: number, to: number): Observable<Brewer[]> {
    const params = new HttpParams().set('from', from.toString()).set('to', to.toString());
    console.log(params);
    return this.http.get<Brewer[]>(API_URI + 'brewer/filterByAge', {
      params: params,
      headers: this.headerUtils.setSecretAsHeaders()
    });
  }

  removeBrewer(brewer: Brewer): Observable<any> {
    console.log('jestem');
    return this.http.delete(API_URI + `brewer/${brewer.id}`, {
      observe: 'response',
      headers: this.headerUtils.setSecretAsHeaders()
    });
  }

  saveBrewer(brewer: Brewer): Observable<any> {
    if (brewer.id) {
      return this.http.put(API_URI + `brewer/${brewer.id}`, brewer, {
        observe: 'response',
        headers: this.headerUtils.setSecretAsHeaders()
      });
    } else {
      return this.http.post(API_URI + 'brewer/', brewer, {
        observe: 'response',
        headers: this.headerUtils.setSecretAsHeaders()
      });
    }
  }
}
