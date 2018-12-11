import {HttpClient, HttpParams} from '@angular/common/http';
import {API_URI} from '../shared-service';
import {HeaderUtils} from '../../utils/header-utils';
import {Injectable} from '@angular/core';

@Injectable()
export class AccountService {
  private CREATED = 201;
  private headerUtils = new HeaderUtils();

  constructor(private http: HttpClient) {
  }

  login(login: string, password: string) {
    const params = new HttpParams().set('login', login).set('password', password);
    return this.http.post(API_URI + 'login/login', '', {params: params, observe: 'response'})
      .subscribe(response => {
        console.log(response);
        this.headerUtils.saveSecretToSession(response);
        location.reload(true);
        alert('Zalogowano!');
      }, error => {
        this.headerUtils.handleError(error, 'Błąd zalogowania!');
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
        this.headerUtils.handleError(error, 'błąd wylogowania!');
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
        this.headerUtils.handleError(error, 'błąd zmiany hasła! podaj poprawne!');
      });
  }

  register(login: any, password: any) {
    const params = new HttpParams().set('login', login).set('password', password);
    return this.http.post(API_URI + 'login/register', '', {params: params, observe: 'response'})
      .subscribe(response => {
        if (response.status === this.CREATED) {
          alert('Utworzono konto.');
          location.reload(true);
        } else {
          alert('Błąd rejestracji!');
        }
      }, error => {
        this.headerUtils.handleError(error, 'Błąd rejestracji!');
      });
  }
}
