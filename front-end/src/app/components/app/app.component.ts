import {Component} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  loggedUser: string;
  title = 'app';

  constructor() {
    console.log('app');
    if (sessionStorage.getItem('login')) {
      this.loggedUser = sessionStorage.getItem('login');
    } else {
      console.log(sessionStorage.getItem('login'));
      this.loggedUser = undefined;
    }
  }
}
