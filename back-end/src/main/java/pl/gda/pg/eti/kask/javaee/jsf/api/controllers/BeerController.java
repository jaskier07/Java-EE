package pl.gda.pg.eti.kask.javaee.jsf.api.controllers;


import pl.gda.pg.eti.kask.javaee.jsf.api.filters.IBeerFilter;
import pl.gda.pg.eti.kask.javaee.jsf.business.entities.Beer;
import pl.gda.pg.eti.kask.javaee.jsf.business.services.BreweryService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Collection;

import static javax.ws.rs.core.Response.*;
import static pl.gda.pg.eti.kask.javaee.jsf.utils.UriUtils.uri;

@IBeerFilter
@Path("/beer")
public class BeerController {
    public static final String METHOD_GET_BEER = "getBeer";
    private static final String PATH_PARAM_BEER = "beer";

    @Inject
    BreweryService breweryService;

    @GET
    public Collection<Beer> getAllBeers() {
        return breweryService.findAllBeers();
    }

    @POST
    public Response saveBeer(Beer beer) {
        Long beerId = breweryService.saveBeer(beer);
        return created(uri(BeerController.class, METHOD_GET_BEER, beerId)).build();
    }

    @GET
    @Path("/{beer}")
    public Beer getBeer(@PathParam(PATH_PARAM_BEER) Beer beer) {
        return beer;
    }

    @DELETE
    @Path("/{beer}")
    public Response deleteBeer(@PathParam(PATH_PARAM_BEER) Beer beer) {
        breweryService.removeBeer(beer);
        return noContent().build();
    }

    @PUT
    @Path("/{beer}")
    public Response updateBeer(@PathParam(PATH_PARAM_BEER) Beer originalBeer, Beer updatedBeer) {
        if (!originalBeer.getId().equals(updatedBeer.getId())) {
            return status(Status.BAD_REQUEST).build();
        }
        breweryService.saveBeer(updatedBeer);
        return ok(updatedBeer).build();
    }
}

