import {HttpHeaders, HttpParams} from '@angular/common/http';

export class HeaderUtils {

  saveSecretToSession(response) {

    console.log(response);
    console.log(response.headers);
    console.log(response.headers.get('secret'));
    if (response.headers.get('secret')) {
      console.log('zapisuje sekret: ' + response.headers.get('secret'));
      sessionStorage.setItem('secret', response.headers.get('secret'));
    } else {
      console.log('nie dostalem sekretu');
    }
    if (response.headers.get('login')) {
      console.log('zapisuje login: ' + response.headers.get('login'));
      sessionStorage.setItem('login', response.headers.get('login'));
    }
    if (response.headers.get('forbidden')) {
      alert('BRAK UPRAWNIEN!');
    }
  }

  handleError(response, text: String) {
    console.log(response);
    let errorFound = false;

    if (response.headers.get('forbidden')) {
      alert(text + ' BRAK UPRAWNIEŃ!');
      sessionStorage.removeItem('forbidden');
      errorFound = true;
    }

    if (response.headers.get('notUniqueLogin')) {
      alert(text + ' Ten login jest już zajęty. Prosimy o większą oryginalność.');
      errorFound = true;
    }
    if (!errorFound) {
      alert(text);
    }
  }

  handleErrorNoText(response) {
    console.log(response);
    this.handleError(response, 'Wystąpił błąd.');
  }

  setSecretAsHeaders() {
    if (sessionStorage.getItem('login') && sessionStorage.getItem('secret')) {
      return new HttpHeaders()
        .set('login', sessionStorage.getItem('login'))
        .set('secret', sessionStorage.getItem('secret'));
    }
    return new HttpHeaders();
  }

  getSecretFromSession() {
    return (sessionStorage.getItem('secret'), sessionStorage.getItem('login'));
  }
}
