export class Resource {
  uri: string;
  method: string;
  rel: string;


  constructor(uri: string, rel: string, method?: string) {
    this.uri = uri;
    this.rel = rel;
    this.method = method;
  }

}
