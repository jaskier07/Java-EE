package pl.gda.pg.eti.kask.javaee.jsf.utils;

import javax.ejb.EJBAccessException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.FORBIDDEN;

@Provider
public class EJBAccessExceptionMapper implements ExceptionMapper<EJBAccessException> {

    @Override
    public Response toResponse(EJBAccessException exception) {
        return Response.status(FORBIDDEN).build();
    }
}
