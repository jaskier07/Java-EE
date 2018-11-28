import {HttpHeaders, HttpParams} from '@angular/common/http';

export class HeaderUtils {

  saveSecretToSession(response) {

    console.log(response);
    console.log(response.headers);
    console.log(response.headers.get('secret'));
    if (response.headers.get('secret')) {
      sessionStorage.setItem('secret', response.headers.get('secret'));
    }
    if (response.headers.get('login')) {
      sessionStorage.setItem('login', response.headers.get('login'));
    }
    if (response.headers.get('forbidden2')) {
      alert('BRAK UPRAWNIEN!');
    }
  }

  handleError(response) {
    console.log(response);
    if (response.headers.get('forbidden2')) {
      alert('BRAK UPRAWNIEN!');
      sessionStorage.removeItem('forbidden2');
    }
  }

  setSecretAsHeaders() {
    if (sessionStorage.getItem('login') && sessionStorage.getItem('secret')) {
      return new HttpHeaders()
        .set('login', sessionStorage.getItem('login'))
        .set('secret', sessionStorage.getItem('secret'))
        ;
    }
    return new HttpHeaders();
  }

  getSecretFromSession() {
    return (sessionStorage.getItem('secret'), sessionStorage.getItem('login'));
  }
}
