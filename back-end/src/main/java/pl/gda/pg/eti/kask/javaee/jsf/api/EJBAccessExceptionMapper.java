package pl.gda.pg.eti.kask.javaee.jsf.api;

import javax.ejb.EJBAccessException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.FORBIDDEN;

@Provider
public class EJBAccessExceptionMapper implements ExceptionMapper<NullPointerException> {

    @Override
    public Response toResponse(NullPointerException exception) {
        return Response.status(FORBIDDEN).header("forbidden2", true).build();
    }
}
