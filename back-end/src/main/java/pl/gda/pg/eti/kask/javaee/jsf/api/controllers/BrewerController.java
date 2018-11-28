package pl.gda.pg.eti.kask.javaee.jsf.api.controllers;

import pl.gda.pg.eti.kask.javaee.jsf.api.filters.Authorize;
import pl.gda.pg.eti.kask.javaee.jsf.api.filters.IBrewerFilter;
import pl.gda.pg.eti.kask.javaee.jsf.business.entities.Brewer;
import pl.gda.pg.eti.kask.javaee.jsf.business.services.BreweryService;
import pl.gda.pg.eti.kask.javaee.jsf.business.services.SecurityService;

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
import java.util.Collection;

import static javax.ws.rs.core.Response.Status;
import static javax.ws.rs.core.Response.created;
import static javax.ws.rs.core.Response.noContent;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.status;
import static pl.gda.pg.eti.kask.javaee.jsf.utils.UriUtils.uri;

@IBrewerFilter
@Path("/brewer")
@ApplicationScoped
@Authorize
public class BrewerController {
    private static final String METHOD_GET_BREWER = "getBrewer";
    private static final String PATH_PARAM_BREWER = "brewer";

    @Inject
    BreweryService breweryService;


    @Inject
    SecurityService securityService;

    @RequestScoped
    @Inject
    private HttpServletRequest request;

    @GET
    public Collection<Brewer> getAllBrewers() {
        if (securityService.checkPriviledge(request, "USER")) {
            return breweryService.findAllBrewers();
        }
        throw new NullPointerException();
    }


    @GET
    @Path("/filterByAge")
    public Collection<Brewer> getBrewersByAge(@QueryParam("from") String from,
                                              @QueryParam("to") String to) {
        if (securityService.checkPriviledge(request, "USER")) {
            return breweryService.findBrewersByAge(Integer.parseInt(from), Integer.parseInt(to));
        }
        throw new NullPointerException();
    }

    @POST
    public Response saveBrewer(Brewer brewer) {
        if (securityService.checkPriviledge(request, "ADMIN")) {
            Long brewerId = breweryService.saveBrewer(brewer);
            return created(uri(BrewerController.class, METHOD_GET_BREWER, brewerId)).build();
        }
        throw new NullPointerException();
    }

    @GET
    @Path("/{brewer}")
    public Brewer getBrewer(@PathParam(PATH_PARAM_BREWER) Brewer brewer) {
        if (securityService.checkPriviledge(request, "USER")) {
            return brewer;
        }
        throw new NullPointerException();
    }

    @DELETE
    @Path("/{brewer}")
    public Response deleteBrewer(@PathParam(PATH_PARAM_BREWER) Brewer brewer) {
        if (securityService.checkPriviledge(request, "ADMIN")) {
            breweryService.removeBrewer(brewer);
            return noContent().build();
        }
        throw new NullPointerException();
    }

    @PUT
    @Path("/{brewer}")
    public Response updateBrewer(@PathParam(PATH_PARAM_BREWER) Brewer originalBrewer, Brewer updatedBrewer) {
        if (securityService.checkPriviledge(request, "ADMIN")) {
            if (!originalBrewer.getId().equals(updatedBrewer.getId())) {
                return status(Status.BAD_REQUEST).build();
            }
            breweryService.saveBrewer(updatedBrewer);
            return ok().build();
        }
        throw new NullPointerException();
    }

}
