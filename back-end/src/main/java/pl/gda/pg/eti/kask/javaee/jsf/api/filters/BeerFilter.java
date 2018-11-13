package pl.gda.pg.eti.kask.javaee.jsf.api.filters;

import lombok.extern.java.Log;
import pl.gda.pg.eti.kask.javaee.jsf.api.controllers.BeerController;
import pl.gda.pg.eti.kask.javaee.jsf.business.entities.Beer;
import pl.gda.pg.eti.kask.javaee.jsf.utils.FilterUtils;
import pl.gda.pg.eti.kask.javaee.jsf.utils.UriUtils;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Link;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@IBeerFilter
@Provider
@Priority(Priorities.USER)
@Log
public class BeerFilter implements ContainerResponseFilter {
    private FilterUtils filterUtils = new FilterUtils();

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext response) throws IOException {
        filterUtils.addAccessControl(response);

        response.getHeaders().add("all", createHeaderValue("all available beers", "GET"));
        if (response.getEntity() instanceof Beer) {
            Long id = ((Beer) response.getEntity()).getId();
            response.getHeaders().add("save", createHeaderValue("save new beer", "POST"));
            response.getHeaders().add("put", createHeaderValue("update beer", "updateBeer", id, "PUT"));
            response.getHeaders().add("self", createHeaderValue("self", "getBeer", id, "GET"));
            response.getHeaders().add("delete", createHeaderValue("remove beer", "deleteBeer", id, "DELETE"));
        }
    }

    private Link createHeaderValue(String rel, String methodName, Long id, String method) {
        return Link.fromUri(UriUtils.uri(BeerController.class, methodName, id)).rel(rel).title(method).build();
    }

    private Link createHeaderValue(String rel, String method) {
        return Link.fromUri(UriUtils.uri(BeerController.class)).rel(rel).title(method).build();
    }
}
