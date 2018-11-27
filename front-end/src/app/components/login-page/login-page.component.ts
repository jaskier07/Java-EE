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

  constructor(private sharedService: SharedService) { }

  ngOnInit() {
  }

  handleLoginInput() {

     this.sharedService.login(this.loginInput.nativeElement.value, this.passwordInput.nativeElement.value)
       .subscribe(response => {
         console.log(response);
       }, error => {
         console.log(error);
       });
    }
}
