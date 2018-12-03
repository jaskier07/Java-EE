package pl.gda.pg.eti.kask.javaee.jsf.api.converters;

import pl.gda.pg.eti.kask.javaee.jsf.business.model.entities.Beer;
import pl.gda.pg.eti.kask.javaee.jsf.business.services.BreweryService;

import javax.ws.rs.ext.Provider;


@Provider
public class BeerConverter extends AbstractEntityConverter<Beer> {
    public BeerConverter() {
        super(Beer.class, Beer::getId, BreweryService::findBeer);
    }
}
