import {Component, OnInit, ViewChild} from '@angular/core';
import {BeerService} from '../beer/beer-service';
import {SharedService} from '../shared-service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {
  @ViewChild('login') loginInput;
  @ViewChild('password') passwordInput;
  @ViewChild('psswdOld') psswdOld;
  @ViewChild('psswdNew') psswdNew;
  loggedUser: string;

  constructor(private sharedService: SharedService) {
    if (sessionStorage.getItem('login')) {
      this.loggedUser = sessionStorage.getItem('login');
    } else {
      this.loggedUser = undefined;
    }
  }

  ngOnInit() {
  }

  logout() {
    this.sharedService.logout();
  }

  handleLoginInput() {

    this.sharedService.login(this.loginInput.nativeElement.value, this.passwordInput.nativeElement.value);
  }

  handleChangePassword() {
    this.sharedService.changePassword(this.loggedUser, this.psswdOld.nativeElement.value, this.psswdNew.nativeElement.value);
  }
}
