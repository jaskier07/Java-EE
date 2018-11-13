import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {API_URI} from '../shared-service';
import {Brewer} from '../../model/brewer';

@Injectable()
export class BrewerService {
  constructor(private http: HttpClient) {
  }

  findBrewer(id: number): Observable<HttpResponse<Brewer>> {
    return this.http.get<Brewer>(API_URI + `brewer/${id}`, {observe: 'response'});
  }

  removeBrewer(brewer: Brewer): Observable<any> {
    return this.http.delete(API_URI + `brewer/${brewer.id}`, {observe: 'response'});
  }

  saveBrewer(brewer: Brewer): Observable<any> {
    if (brewer.id) {
      return this.http.put(API_URI + `brewer/${brewer.id}`, brewer, {observe: 'response'});
    } else {
      return this.http.post(API_URI + 'brewer/', brewer, {observe: 'response'});
    }
  }
}
