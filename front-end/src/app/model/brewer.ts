import {Beer} from './beer';

export class Brewer {
  id: number;
  name: string;
  age: number;
  beers: Beer[];
  lastUpdateDate: Date;

  constructor() {
    this.name = '';
    this.age = null;
    this.beers = [];
  }
}
