package pl.gda.pg.eti.kask.javaee.jsf.api.controllers;

import pl.gda.pg.eti.kask.javaee.jsf.api.filters.interfaces.AccessControl;
import pl.gda.pg.eti.kask.javaee.jsf.api.filters.interfaces.IBrewerFilter;
import pl.gda.pg.eti.kask.javaee.jsf.api.interceptors.interfaces.UserAllowed;
import pl.gda.pg.eti.kask.javaee.jsf.business.model.entities.Brewer;
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
@AccessControl
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
    @UserAllowed
    public Collection<Brewer> getAllBrewers() {
        if (securityService.checkPrivilege(request, "USER")) {
            return breweryService.findAllBrewers();
        }
        throw new NullPointerException();
    }


    @GET
    @Path("/filterByAge")
    @UserAllowed
    public Collection<Brewer> getBrewersByAge(@QueryParam("from") String from,
                                              @QueryParam("to") String to) {
        if (securityService.checkPrivilege(request, "USER")) {
            return breweryService.findBrewersByAge(Integer.parseInt(from), Integer.parseInt(to));
        }
        throw new NullPointerException();
    }

    @POST
    @UserAllowed
    public Response saveBrewer(Brewer brewer) {
        if (securityService.checkPrivilege(request, "ADMIN")) {
            Long brewerId = breweryService.saveBrewer(brewer, request);
            return created(uri(BrewerController.class, METHOD_GET_BREWER, brewerId)).build();
        }
        throw new NullPointerException();
    }

    @GET
    @Path("/{brewer}")
    @UserAllowed
    public Brewer getBrewer(@PathParam(PATH_PARAM_BREWER) Brewer brewer) {
        if (securityService.checkPrivilege(request, "USER")) {
            return brewer;
        }
        throw new NullPointerException();
    }

    @DELETE
    @Path("/{brewer}")
    @UserAllowed
    public Response deleteBrewer(@PathParam(PATH_PARAM_BREWER) Brewer brewer) {
        if (securityService.checkPrivilege(request, "ADMIN")) {
            breweryService.removeBrewer(brewer);
            return noContent().build();
        }
        throw new NullPointerException();
    }

    @PUT
    @Path("/{brewer}")
    @UserAllowed
    public Response updateBrewer(@PathParam(PATH_PARAM_BREWER) Brewer originalBrewer, Brewer updatedBrewer) {
        if (securityService.checkPrivilege(request, "ADMIN")) {
            if (!originalBrewer.getId().equals(updatedBrewer.getId())) {
                return status(Status.BAD_REQUEST).build();
            }
            breweryService.saveBrewer(updatedBrewer, request);
            return ok().build();
        }
        throw new NullPointerException();
    }

}
