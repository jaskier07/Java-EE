export class HateoasUtils {
  private AVAILABLE_ENTITIES = 'all';
  private SAVE_ENTITY = 'save';
  private REMOVE_ENTITY = 'delete';
  private UPDATE_ENTITY = 'put';
  private SELF = 'self';
  private BEER_0 = 'beer_0';

  printEntityHeaders(response) {
    console.log('reading hateoas...');
    if (response.headers.get(this.AVAILABLE_ENTITIES)) {
      console.log(response.headers.get(this.AVAILABLE_ENTITIES));
    }
    if (response.headers.get(this.SAVE_ENTITY)) {
      console.log(response.headers.get(this.SAVE_ENTITY));
    }
    if (response.headers.get(this.REMOVE_ENTITY)) {
      console.log(response.headers.get(this.REMOVE_ENTITY));
    }
    if (response.headers.get(this.UPDATE_ENTITY)) {
      console.log(response.headers.get(this.UPDATE_ENTITY));
    }
    if (response.headers.get(this.SELF)) {
      console.log(response.headers.get(this.SELF));
    }
    if (response.headers.get(this.BEER_0)) {
      let index = 0;
      while (response.headers.get('beer_' + index)) {
        console.log(response.headers.get('beer_' + index));
        index++;
      }
    }
    console.log('end of reading');
  }
}
