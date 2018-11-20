package pl.gda.pg.eti.kask.javaee.jsf.api;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

@Provider
public class EJBValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    private static final int UNPROCESSABLE_ENTITY = 412;

    @Override
    public Response toResponse(ConstraintViolationException e) {
        Map<String, String> errors = new HashMap<>();

        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            errors.put(violation.getPropertyPath().toString(), violation.getMessageTemplate());
        }

        return Response.status(UNPROCESSABLE_ENTITY).type("text/plain")
                .entity(errors).build();
    }
}
