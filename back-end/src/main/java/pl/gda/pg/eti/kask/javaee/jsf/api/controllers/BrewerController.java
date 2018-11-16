package pl.gda.pg.eti.kask.javaee.jsf.api.controllers;

import pl.gda.pg.eti.kask.javaee.jsf.api.filters.IBrewerFilter;
import pl.gda.pg.eti.kask.javaee.jsf.business.entities.Brewer;
import pl.gda.pg.eti.kask.javaee.jsf.business.services.BreweryService;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
public class BrewerController {
    private static final String METHOD_GET_BREWER = "getBrewer";
    private static final String PATH_PARAM_BREWER = "brewer";

    @Inject
    BreweryService breweryService;

    @GET
    public Collection<Brewer> getAllBrewers() {
        return breweryService.findAllBrewers();
    }

    @POST
    public Response saveBrewer(Brewer brewer) {
        Long brewerId = breweryService.saveBrewer(brewer);
        return created(uri(BrewerController.class, METHOD_GET_BREWER, brewerId)).build();
    }

    @GET
    @Path("/{brewer}")
    public Brewer getBrewer(@PathParam(PATH_PARAM_BREWER) Brewer brewer) {
        return brewer;
    }

    @DELETE
    @Path("/{brewer}")
    public Response deleteBrewer(@PathParam(PATH_PARAM_BREWER) Brewer brewer) {
        breweryService.removeBrewer(brewer);
        return noContent().build();
    }

    @PUT
    @Path("/{brewer}")
    public Response updateBrewer(@PathParam(PATH_PARAM_BREWER) Brewer originalBrewer, Brewer updatedBrewer) {
        if (!originalBrewer.getId().equals(updatedBrewer.getId())) {
            return status(Status.BAD_REQUEST).build();
        }
        breweryService.saveBrewer(updatedBrewer);
        return ok().build();
    }
}
