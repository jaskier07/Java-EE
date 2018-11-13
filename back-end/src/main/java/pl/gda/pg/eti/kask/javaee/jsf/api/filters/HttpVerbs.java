package pl.gda.pg.eti.kask.javaee.jsf.api.filters;

import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;

public enum HttpVerbs {
    POST("POST"),
    GET("GET"),
    PUT("PUT"),
    DELETE("DELETE");

    private String text;

    private HttpVerbs(String text) {
        this.text = text;
    }
}
