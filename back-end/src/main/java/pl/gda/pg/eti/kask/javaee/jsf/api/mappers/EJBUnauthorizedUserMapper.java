package pl.gda.pg.eti.kask.javaee.jsf.api.mappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.FORBIDDEN;

@Provider
public class EJBUnauthorizedUserMapper implements ExceptionMapper<NullPointerException> {

    @Override
    public Response toResponse(NullPointerException exception) {
        return Response.status(FORBIDDEN).header("forbidden", true).build();
    }
}
