package pl.gda.pg.eti.kask.javaee.jsf.api.converters;

import pl.gda.pg.eti.kask.javaee.jsf.business.entities.Brewer;
import pl.gda.pg.eti.kask.javaee.jsf.business.services.BreweryService;

import javax.ws.rs.ext.Provider;

@Provider
public class BrewerConverter extends AbstractEntityConverter<Brewer> {
    public BrewerConverter() {
        super(Brewer.class, Brewer::getId, BreweryService::findBrewer);
    }
}
