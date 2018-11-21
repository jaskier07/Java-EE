import {ActivatedRoute} from '@angular/router';
import {ViewChild} from '@angular/core';
import {AngularUtils} from '../utils/angular-utils';

export abstract class EditEntity {
  protected DATA_OK = 'Brak problemów. Ale tylko kliknij zapisz, to zobaczymy...';
  protected DATA_ERROR = 'Błędne dane w formularzu lub nie podano wszystkich wymaganych danych.';
  protected utils = new AngularUtils();
  @ViewChild('infoLabel') infoLabel;

  protected constructor(protected route: ActivatedRoute) {
  }

  protected setInfoLabel(text: string) {
    if (this.infoLabel != null && this.infoLabel.nativeElement !== null) {
      this.infoLabel.nativeElement.textContent = text;
    }
  }


  protected validateIntegerInput(value: number) {
    return value.toString().match('^\\d+$');
  }

}
