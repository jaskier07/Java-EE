package pl.gda.pg.eti.kask.javaee.jsf.utils;

import pl.gda.pg.eti.kask.javaee.jsf.api.controllers.BeerController;
import pl.gda.pg.eti.kask.javaee.jsf.api.filters.enums.HttpVerbs;
import pl.gda.pg.eti.kask.javaee.jsf.business.model.entities.Beer;

import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.Link;
import java.util.Set;

public class FilterUtils {
    public void addAccessControl(ContainerResponseContext response) {
        response.getHeaders().add("Access-Control-Expose-Headers", "*");
        response.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        response.getHeaders().add("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, X-Codingpedia");
    }

    public void addBeerHeaders(ContainerResponseContext response, Set<Beer>beers) {
        int index = 0;
        for (Beer beer : beers) {
            response.getHeaders().add("beer_" + index, createBeerHeaderValue("get beer nr " + index, "getBeer", beer.getId(), HttpVerbs.GET.name()));
            ++index;
        }
    }

    private Link createBeerHeaderValue(String rel, String methodName, Long id, String method) {
        return Link.fromUri(UriUtils.uri(BeerController.class, methodName, id)).rel(rel).title(method).build();
    }
}
