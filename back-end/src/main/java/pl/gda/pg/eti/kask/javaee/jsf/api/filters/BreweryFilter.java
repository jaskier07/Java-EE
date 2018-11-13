package pl.gda.pg.eti.kask.javaee.jsf.api.filters;

import pl.gda.pg.eti.kask.javaee.jsf.api.controllers.BeerController;
import pl.gda.pg.eti.kask.javaee.jsf.api.controllers.BreweryController;
import pl.gda.pg.eti.kask.javaee.jsf.business.entities.Beer;
import pl.gda.pg.eti.kask.javaee.jsf.business.entities.Brewery;
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
import java.util.List;
import java.util.Set;

@IBreweryFilter
@Provider
@Priority(Priorities.USER)
public class BreweryFilter implements ContainerResponseFilter {
    private FilterUtils filterUtils = new FilterUtils();

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext response) throws IOException {
        filterUtils.addAccessControl(response);

        response.getHeaders().add("all", createHeaderValue("all available breweries", "GET"));
        if (response.getEntity() instanceof Brewery) {
            Long id = ((Brewery) response.getEntity()).getId();
            response.getHeaders().add("save", createHeaderValue("save new brewery", "POST"));
            response.getHeaders().add("put", createHeaderValue("update brewery", "updateBrewery", id, "PUT"));
            response.getHeaders().add("self", createHeaderValue("self", "getBrewery", id, "GET"));
            response.getHeaders().add("delete", createHeaderValue("remove brewery", "deleteBrewery", id, "DELETE"));

            Set<Beer> beers = ((Brewery) response.getEntity()).getBeers();
            filterUtils.addBeerHeaders(response, beers);
        }
    }

    private Link createBeerHeaderValue(String rel, String methodName, Long id, String method) {
        return Link.fromUri(UriUtils.uri(BeerController.class, methodName, id)).rel(rel).title(method).build();
    }

    private Link createHeaderValue(String rel, String methodName, Long id, String method) {
        return Link.fromUri(UriUtils.uri(BreweryController.class, methodName, id)).rel(rel).title(method).build();
    }

    private Link createHeaderValue(String rel, String method) {
        return Link.fromUri(UriUtils.uri(BreweryController.class)).rel(rel).title(method).build();
    }
}
