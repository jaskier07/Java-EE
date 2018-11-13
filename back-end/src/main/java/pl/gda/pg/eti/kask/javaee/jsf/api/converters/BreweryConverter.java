package pl.gda.pg.eti.kask.javaee.jsf.api.converters;

import pl.gda.pg.eti.kask.javaee.jsf.business.entities.Brewery;
import pl.gda.pg.eti.kask.javaee.jsf.business.services.BreweryService;

import javax.ws.rs.ext.Provider;

@Provider
public class BreweryConverter extends AbstractEntityConverter<Brewery> {

    public BreweryConverter() {
        super(Brewery.class, Brewery::getId, BreweryService::findBrewery);
    }
}
