import {Component, OnInit, ViewChild} from '@angular/core';
import {BeerService} from '../beer/beer-service';
import {SharedService} from '../shared-service';
import {AccountService} from './account-service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {
  @ViewChild('login') loginInput;
  @ViewChild('password') passwordInput;
  @ViewChild('loginRegister') loginRegisterInput;
  @ViewChild('passwordRegister') passwordRegisterInput;
  @ViewChild('psswdOld') psswdOld;
  @ViewChild('psswdNew') psswdNew;
  loggedUser: string;

  constructor(private accountService: AccountService) {
    if (sessionStorage.getItem('login')) {
      this.loggedUser = sessionStorage.getItem('login');
    } else {
      this.loggedUser = undefined;
    }
  }

  ngOnInit() {
  }

  logout() {
    this.accountService.logout();
  }

  handleLoginInput() {
    this.accountService.login(this.loginInput.nativeElement.value, this.passwordInput.nativeElement.value);
  }

  handleChangePassword() {
    this.accountService.changePassword(this.loggedUser, this.psswdOld.nativeElement.value, this.psswdNew.nativeElement.value);
  }

  handleRegisterInput() {
    this.accountService.register(this.loginRegisterInput.nativeElement.value, this.passwordRegisterInput.nativeElement.value);
  }
}
