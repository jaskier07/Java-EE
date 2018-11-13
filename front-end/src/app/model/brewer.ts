import {Beer} from './beer';

export class Brewer {
  id: number;
  name: string;
  age: number;
  beers: Beer[];

  constructor() {
    this.name = '';
    this.age = null;
    this.beers = [];
  }
}
