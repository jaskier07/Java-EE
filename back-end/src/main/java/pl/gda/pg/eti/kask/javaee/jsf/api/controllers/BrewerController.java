package pl.gda.pg.eti.kask.javaee.jsf.api.controllers;

import pl.gda.pg.eti.kask.javaee.jsf.api.filters.interfaces.AccessControl;
import pl.gda.pg.eti.kask.javaee.jsf.api.filters.interfaces.IBrewerFilter;
import pl.gda.pg.eti.kask.javaee.jsf.business.model.entities.Brewer;
import pl.gda.pg.eti.kask.javaee.jsf.business.security.SecurityService;
import pl.gda.pg.eti.kask.javaee.jsf.business.services.BreweryService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.stream.Collectors;

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

    @GET
    public Collection<Brewer> getAllBrewers() {
        if (securityService.verifyUser()) {
            return breweryService.findAllBrewers();
        }
        throw new NullPointerException();
    }

    @GET
    @Path("/filterByAge")
    public Collection<Brewer> getBrewersByAge(@QueryParam("from") String from,
                                              @QueryParam("to") String to) {
        if (securityService.verifyUser()) {
            return breweryService.findBrewersByAge(Integer.parseInt(from), Integer.parseInt(to));
        }
        throw new NullPointerException();
    }

    @GET
    @Path("/getByNewest")
    public Collection<Brewer> getBrewersByNewest() {
        if (securityService.verifyUser()) {
            return breweryService.findAllBrewers().stream().sorted((o1, o2) ->  {
                if (o1.getLastUpdateDate().equals(o2.getLastUpdateDate())) {
                    return 0;
                }
                return (o1.getLastUpdateDate().before(o2.getLastUpdateDate()) ? 1 : -1);
            }).collect(Collectors.toList());
        }
        throw new NullPointerException();
    }

    @POST
    public Response saveBrewer(Brewer brewer) {
        if (securityService.verifyUser()) {
            Long brewerId = breweryService.saveBrewer(brewer);
            return created(uri(BrewerController.class, METHOD_GET_BREWER, brewerId)).build();
        }
        throw new NullPointerException();
    }

    @GET
    @Path("/{brewer}")
    public Brewer getBrewer(@PathParam(PATH_PARAM_BREWER) Brewer brewer) {
        if (securityService.verifyUser()) {
            return brewer;
        }
        throw new NullPointerException();
    }

    @DELETE
    @Path("/{brewer}")
    public Response deleteBrewer(@PathParam(PATH_PARAM_BREWER) Brewer brewer) {
        if (securityService.verifyUser()) {
            breweryService.removeBrewer(brewer);
            return noContent().build();
        }
        throw new NullPointerException();
    }

    @PUT
    @Path("/{brewer}")
    public Response updateBrewer(@PathParam(PATH_PARAM_BREWER) Brewer originalBrewer, Brewer updatedBrewer) {
        if (securityService.verifyUser()) {
            if (!originalBrewer.getId().equals(updatedBrewer.getId())) {
                return status(Status.BAD_REQUEST).build();
            }
            breweryService.updateBrewer(updatedBrewer);
            return ok().build();
        }
        throw new NullPointerException();
    }
}
