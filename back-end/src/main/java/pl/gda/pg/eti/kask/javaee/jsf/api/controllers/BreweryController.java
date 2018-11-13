package pl.gda.pg.eti.kask.javaee.jsf.api.controllers;


import pl.gda.pg.eti.kask.javaee.jsf.api.filters.IBreweryFilter;
import pl.gda.pg.eti.kask.javaee.jsf.business.entities.Brewery;
import pl.gda.pg.eti.kask.javaee.jsf.business.services.BreweryService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static javax.ws.rs.core.Response.*;
import static pl.gda.pg.eti.kask.javaee.jsf.utils.UriUtils.uri;

@IBreweryFilter
@Path("/brewery")
public class BreweryController {
    private static final String METHOD_GET_BREWERY = "getBrewery";
    private static final String PATH_PARAM_BREWERY = "brewery";

    @Inject
    BreweryService breweryService;

    @GET
    public Collection<Brewery> getAllBreweries() {
        return breweryService.findAllBreweries();
    }


    @POST
    public Response saveBrewery(Brewery brewery) {
        Long breweryId = breweryService.saveBrewery(brewery);
        return created(uri(BreweryController.class, METHOD_GET_BREWERY, breweryId)).build();
    }

    @GET
    @Path("/{brewery}")
    public Brewery getBrewery(@PathParam(PATH_PARAM_BREWERY) Brewery brewery) {
        return brewery;
    }

    @DELETE
    @Path("/{brewery}")
    public Response deleteBrewery(@PathParam(PATH_PARAM_BREWERY) Brewery brewery) {
        breweryService.removeBrewery(brewery);
        return noContent().build();
    }

    @PUT
    @Path("/{brewery}")
    public Response updateBrewery(@PathParam(PATH_PARAM_BREWERY) Brewery originalBrewery, Brewery updatedBrewery) {
        if (!originalBrewery.getId().equals(updatedBrewery.getId())) {
            return status(Status.BAD_REQUEST).build();
        }
        breweryService.saveBrewery(updatedBrewery);
        return ok().build();
    }
}

