import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Brewery} from '../../model/brewery';
import {API_URI} from '../shared-service';
import {HeaderUtils} from '../../utils/header-utils';

@Injectable()
export class BreweryService {
  private headerUtils = new HeaderUtils();

  constructor(private http: HttpClient) {
  }

  findBrewery(id: number): Observable<HttpResponse<Brewery>> {
    return this.http.get<Brewery>(API_URI + `brewery/${id}`, {
      observe: 'response',
      headers: this.headerUtils.setSecretAsHeaders()
    });
  }

  removeBrewery(brewery: Brewery): Observable<any> {
    return this.http.delete(API_URI + `brewery/${brewery.id}`, {
      observe: 'response',
      headers: this.headerUtils.setSecretAsHeaders()
    });
  }

  saveBrewery(brewery: Brewery): Observable<any> {
    if (brewery.id) {
      return this.http.put(API_URI + `brewery/${brewery.id}`, brewery, {
        observe: 'response',
        headers: this.headerUtils.setSecretAsHeaders()
      });
    } else {
      return this.http.post(API_URI + 'brewery/', brewery, {
        observe: 'response',
        headers: this.headerUtils.setSecretAsHeaders()
      });
    }
  }
}
