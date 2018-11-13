package pl.gda.pg.eti.kask.javaee.jsf.api;

import lombok.AllArgsConstructor;

public class Resource {
    private String uri;
    private String method;
    private String rel;

    public Resource(String uri, String method, String rel) {
        this.uri = uri;
        this.method = method;
        this.rel = rel;
    }

    public Resource(String uri, String rel) {
        this.uri = uri;
        this.rel = rel;
    }
}
