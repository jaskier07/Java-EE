package pl.gda.pg.eti.kask.javaee.jsf.api.filters;

import lombok.extern.java.Log;
import pl.gda.pg.eti.kask.javaee.jsf.api.Pagination;
import pl.gda.pg.eti.kask.javaee.jsf.api.controllers.BeerController;
import pl.gda.pg.eti.kask.javaee.jsf.business.entities.Beer;
import pl.gda.pg.eti.kask.javaee.jsf.business.services.BreweryService;
import pl.gda.pg.eti.kask.javaee.jsf.utils.FilterUtils;
import pl.gda.pg.eti.kask.javaee.jsf.utils.UriUtils;

import javax.annotation.Priority;
import javax.inject.Inject;
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

    @Inject
    private BreweryService breweryService;
    private FilterUtils filterUtils = new FilterUtils();
    private int beersSize;

    @Override
    public void filter(ContainerRequestContext request, ContainerResponseContext response) throws IOException {
        filterUtils.addAccessControl(response);
        beersSize = breweryService.findAllBeers().size();

        if (isPaginationRequest(request)) {
            addPaginationHeaders(request, response);
        } else {
            response.getHeaders().add("all", createHeaderValue("all available beers", "GET"));
            if (response.getEntity() instanceof Beer) {
                Long id = ((Beer) response.getEntity()).getId();
                response.getHeaders().add("save", createHeaderValue("save new beer", "POST"));
                response.getHeaders().add("put", createHeaderValue("update beer", "updateBeer", id, "PUT"));
                response.getHeaders().add("self", createHeaderValue("self", "getBeer", id, "GET"));
                response.getHeaders().add("delete", createHeaderValue("remove beer", "deleteBeer", id, "DELETE"));
            }
        }
    }

    private void addPaginationHeaders(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        Pagination request = new Pagination();
        request.setFrom(Integer.valueOf(requestContext.getUriInfo().getQueryParameters().getFirst("from")));
        request.setTo(Integer.valueOf(requestContext.getUriInfo().getQueryParameters().getFirst("to")));
        int diff = (Integer.valueOf(requestContext.getUriInfo().getQueryParameters().getFirst("diff")));
        request.normalizeWithSize(beersSize);

        if (request.getFrom() > 0) {
           setPaginationPreviousElements(request, responseContext, diff);
        }
        if (request.getTo() + diff < beersSize) {
            setPaginationNextElements(request, responseContext, diff);
        } else if (request.getTo() < beersSize - 1) {
            setPaginationNextRemainedElements(request, responseContext, diff);
        }
    }

    private void setPaginationPreviousElements(Pagination request, ContainerResponseContext responseContext, int diff) {
        Pagination previous = new Pagination();
        previous.setFrom(findPreviousFrom(request, diff));
        previous.setTo(request.getFrom());
        responseContext.getHeaders().add("previous", createHeaderValue("previous elements", "getBeersUsingPagination", previous));
    }

    private void setPaginationNextElements(Pagination request, ContainerResponseContext responseContext, int diff) {
        Pagination next = new Pagination();
        next.setFrom(request.getTo());
        next.setTo(request.getTo() + diff);
        responseContext.getHeaders().add("next", createHeaderValue("next elements", "getBeersUsingPagination", next));
    }

    private void setPaginationNextRemainedElements(Pagination request, ContainerResponseContext responseContext, int diff) {
        Pagination next = new Pagination();
        next.setFrom(request.getTo());
        next.setTo(beersSize);
        responseContext.getHeaders().add("next", createHeaderValue("next elements", "getBeersUsingPagination", next));
    }

    private boolean isPaginationRequest(ContainerRequestContext request) {
        return request.getUriInfo().getPath().equals(UriUtils.uri(BeerController.class, "getBeersUsingPagination").toString());
    }

    private int findPreviousFrom(Pagination request, int diff) {
        int i = (request.getFrom() - diff);
        if (i <= 0) {
            return 0;
        } else {
            return i;
        }
    }

    private Link createHeaderValue(String rel, String methodName, Pagination pagination) {
        return Link.fromUri(UriUtils.paginationUri(BeerController.class, methodName, pagination.getFrom(), pagination.getTo())).rel(rel).title("GET").build();
    }

    private Link createHeaderValue(String rel, String methodName, Long id, String method) {
        return Link.fromUri(UriUtils.uri(BeerController.class, methodName, id)).rel(rel).title(method).build();
    }

    private Link createHeaderValue(String rel, String method) {
        return Link.fromUri(UriUtils.uri(BeerController.class)).rel(rel).title(method).build();
    }
}
