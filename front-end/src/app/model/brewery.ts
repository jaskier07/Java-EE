import {Beer} from './beer';

export class Brewery {

  id: number;
  name: string;
  employees: number;
  dateEstablished: Date;
  beers: Beer[];


  constructor() {
    this.id = null;
    this.name = '';
    this.employees = null;
    this.dateEstablished = null;
    this.beers = [];
  }
}
