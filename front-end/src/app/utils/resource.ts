export class Resource {
  uri: string;
  method: string;
  rel: string;

  constructor(original: Resource) {
    this.uri = original.uri;
    this.method = original.method;
    this.rel = original.rel;
}
}
