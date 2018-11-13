import {ActivatedRoute} from '@angular/router';

export class AngularUtils {
  isEmpty(string: string) {
    return string && string !== '';
  }

  notEmpty(string: string) {
    return string && string !== '';
  }

  isNull(value) {
    return value === undefined || value === null;
  }

  notNull(value) {
    return value !== undefined && value !== null;
  }

  getIdFromRouter(route: ActivatedRoute): number {
    const id = route.snapshot.paramMap.get('id');
    return this.isValidId(id) ? Number(id) : null;
  }

  isValidId(id) {
    return id || isNaN(Number(id)) || Number(id) === 0;
  }
}
