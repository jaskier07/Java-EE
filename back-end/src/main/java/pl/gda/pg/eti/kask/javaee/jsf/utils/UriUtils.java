package pl.gda.pg.eti.kask.javaee.jsf.utils;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class UriUtils {
    public static URI uri(Class<?> clazz, String method, Object... vals) {
        return UriBuilder.fromResource(clazz)
                .path(clazz, method)
                .build(vals);
    }

    public static URI uri(Class<?> clazz) {
        return UriBuilder.fromResource(clazz)
                .path(clazz)
                .build();
    }
}
