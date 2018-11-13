import {Resource} from './resource';

export class HateoasUtils {
  private AVAILABLE_ENTITIES = 'all';
  private SAVE_ENTITY = 'save';
  private REMOVE_ENTITY = 'delete';
  private UPDATE_ENTITY = 'put';
  private SELF = 'self';
  private BEER_0 = 'beer_0';
  private LINK_SEPARATOR = ';';
  private PREVIOUS_BEER = 'previous';
  private NEXT_BEER = 'next';

  printLinks(response) {
    console.log(this.createResources(response));
  }

  createResources(response) {
    const links: Resource[] = [];
    if (response.headers.get(this.AVAILABLE_ENTITIES)) {
      links.push(this.createResource(response.headers.get(this.AVAILABLE_ENTITIES)));
    }
    if (response.headers.get(this.SAVE_ENTITY)) {
      links.push(this.createResource(response.headers.get(this.SAVE_ENTITY)));
    }
    if (response.headers.get(this.REMOVE_ENTITY)) {
      links.push(this.createResource(response.headers.get(this.REMOVE_ENTITY)));
    }
    if (response.headers.get(this.UPDATE_ENTITY)) {
      links.push(this.createResource(response.headers.get(this.UPDATE_ENTITY)));
    }
    if (response.headers.get(this.SELF)) {
      links.push(this.createResource(response.headers.get(this.SELF)));
    }
    if (response.headers.get(this.BEER_0)) {
      let index = 0;
      while (response.headers.get('beer_' + index)) {
        links.push(this.createResource(response.headers.get('beer_' + index)));
        index++;
      }
    }
    if (response.headers.get(this.PREVIOUS_BEER)) {
      links.push(this.createResource(response.headers.get(this.PREVIOUS_BEER)));
    }
    if (response.headers.get(this.NEXT_BEER)) {
      links.push(this.createResource(response.headers.get(this.NEXT_BEER)));
    }
    return links;
  }

  createResource(header: string) {
    const parts = header.split(this.LINK_SEPARATOR);
    if (parts.length > 1) {
      const uri = parts[0].replace('<', '').replace('>', '');
      const rel = parts[1].replace(' rel=', '').replace('\"', '');
      if (parts.length === 3) {
        const method = parts[2].replace(' title=\"', '').replace('\"', '');
        return new Resource(uri, rel, method);
      }
      return new Resource(uri, rel);
    }
  }

  createReourceWithHeader(response, value: string) {
    if (response.headers.get(value)) {
      return this.createResource(response.headers.get(value));
    }
  }
}
