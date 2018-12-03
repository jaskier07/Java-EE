package pl.gda.pg.eti.kask.javaee.jsf.api.controllers;


import pl.gda.pg.eti.kask.javaee.jsf.api.Pagination;
import pl.gda.pg.eti.kask.javaee.jsf.api.filters.interfaces.AccessControl;
import pl.gda.pg.eti.kask.javaee.jsf.api.filters.interfaces.IBeerFilter;
import pl.gda.pg.eti.kask.javaee.jsf.api.interceptors.interfaces.UserAllowed;
import pl.gda.pg.eti.kask.javaee.jsf.business.model.entities.Beer;
import pl.gda.pg.eti.kask.javaee.jsf.business.services.BreweryService;
import pl.gda.pg.eti.kask.javaee.jsf.business.security.SecurityService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;

import static javax.ws.rs.core.Response.Status;
import static javax.ws.rs.core.Response.created;
import static javax.ws.rs.core.Response.noContent;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.status;
import static pl.gda.pg.eti.kask.javaee.jsf.utils.UriUtils.uri;

@IBeerFilter
@Path("/beer")
@ApplicationScoped
@AccessControl
public class BeerController {
    public static final String METHOD_GET_BEER = "getBeer";
    private static final String PATH_PARAM_BEER = "beer";

    @Inject
    BreweryService breweryService;

    @Inject
    SecurityService securityService;

    @RequestScoped
    @Inject
    private HttpServletRequest request;

    @GET
    @UserAllowed
    public Collection<Beer> getAllBeers(HttpServletRequest httpRequest) {
        if (securityService.checkPrivilege(httpRequest, "USER")) {
            return breweryService.findAllBeers();
        }
        throw new NullPointerException();
    }

    @GET
    @Path("/all")
    @UserAllowed
    public Collection<Beer> getBeersUsingPagination(
            @QueryParam("from") int from,
            @QueryParam("to") int to,
            @QueryParam("diff") int diff) {
        if (securityService.checkPrivilege(request, "USER")) {
            Pagination pagination = new Pagination(from, to);
            pagination.normalizeWithSize(breweryService.findAllBeers().size());
            return new ArrayList<>(breweryService.findAllBeers()).subList(pagination.getFrom(), pagination.getTo());
        }
        throw new NullPointerException();
    }

    @POST
    @UserAllowed
    public Response saveBeer(Beer beer) {
        if (securityService.checkPrivilege(request, "ADMIN")) {
            Long beerId = breweryService.saveBeer(beer, request);
            return created(uri(BeerController.class, METHOD_GET_BEER, beerId)).build();
        }
        throw new NullPointerException();

    }

    @GET
    @Path("/{beer}")
    @UserAllowed
    public Beer getBeer(@PathParam(PATH_PARAM_BEER) Beer beer) {
        if (securityService.checkPrivilege(request, "USER")) {
            return beer;
        }
        throw new NullPointerException();

    }
;
    @DELETE
    @Path("/{beer}")
    @UserAllowed
    public Response deleteBeer(@PathParam(PATH_PARAM_BEER) Beer beer) {
        if (securityService.checkPrivilege(request, "ADMIN")) {
            breweryService.removeBeer(beer);
            return noContent().build();
        }
        throw new NullPointerException();
    }

    @PUT
    @Path("/{beer}")
    @UserAllowed
    public Response updateBeer(@PathParam(PATH_PARAM_BEER) Beer originalBeer, Beer updatedBeer) {
        if (securityService.checkPrivilege(request, "ADMIN")) {
            if (!originalBeer.getId().equals(updatedBeer.getId())) {
                return status(Status.BAD_REQUEST).build();
            }
            breweryService.saveBeer(updatedBeer, request);
            return ok(updatedBeer).build();
        }
        throw new NullPointerException();
    }
}

