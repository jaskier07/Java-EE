package pl.gda.pg.eti.kask.javaee.jsf.api.filters;

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
import java.util.Set;

@IBreweryFilter
@Provider
@Priority(Priorities.USER)
public class BreweryFilter implements ContainerResponseFilter {
    private FilterUtils filterUtils = new FilterUtils();

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext response) throws IOException {
        filterUtils.addAccessControl(response);

        response.getHeaders().add(Headers.ALL_ENTITIES.getValue(), createHeaderValue("all available breweries", HttpVerbs.GET));
        if (response.getEntity() instanceof Brewery) {
            Long id = ((Brewery) response.getEntity()).getId();
            response.getHeaders().add(Headers.SAVE_ENTITY.getValue(), createHeaderValue("save new brewery", HttpVerbs.POST));
            response.getHeaders().add(Headers.UPDATE_ENTITY.getValue(), createHeaderValue("update brewery", "updateBrewery", id, HttpVerbs.PUT));
            response.getHeaders().add(Headers.SELF_ENTITY.getValue(), createHeaderValue("self", "getBrewery", id, HttpVerbs.GET));
            response.getHeaders().add(Headers.REMOVE_ENTITY.getValue(), createHeaderValue("remove brewery", "deleteBrewery", id, HttpVerbs.DELETE));

            Set<Beer> beers = ((Brewery) response.getEntity()).getBeers();
            filterUtils.addBeerHeaders(response, beers);
        }
    }

    private Link createHeaderValue(String rel, String methodName, Long id, HttpVerbs verbs) {
        return Link.fromUri(UriUtils.uri(BreweryController.class, methodName, id)).rel(rel).title(verbs.name()).build();
    }

    private Link createHeaderValue(String rel, HttpVerbs verbs) {
        return Link.fromUri(UriUtils.uri(BreweryController.class)).rel(rel).title(verbs.name()).build();
    }
}
