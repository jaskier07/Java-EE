package pl.gda.pg.eti.kask.javaee.jsf.business.services;

import pl.gda.pg.eti.kask.javaee.jsf.business.entities.Beer;
import pl.gda.pg.eti.kask.javaee.jsf.business.entities.Brewer;
import pl.gda.pg.eti.kask.javaee.jsf.business.entities.Brewery;
import pl.gda.pg.eti.kask.javaee.jsf.utils.DateUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.*;

@ApplicationScoped
public class BreweryService {
    protected final SortedMap<Long, Beer> beers = new TreeMap<>();
    protected final SortedMap<Long, Brewery> breweries = new TreeMap<>();
    protected final SortedMap<Long, Brewer> brewers = new TreeMap<>();
    protected static final Long ID_FIRST_ELEMENT = 1L;
    protected static final int NEXT_ELEMENT = 1;

    @PostConstruct
    public void init() {
        Beer special = new Beer(1L, "Specjal", 6.5, 23);
        Beer tyskie = new Beer(2L, "Tyskie Jasne Pełne", 4.9, 12);
        Beer ksiazece = new Beer(3L, "Książęce Złote Pszeniczne", 5.2, 14);
        Beer braniewo = new Beer(4L, "Braniewo Chmielowe", 5.0, 21);
        Beer specjalMocny = new Beer(5L, "Specjal Mocny", 8.0, 32);
        Beer ksiazeceIpa = new Beer(5L, "Książęce IPA", 4.0, 1);
        Beer ksiazeceCzerwone = new Beer(5L, "Książęce Czerwony Lager", 5.7, 51);

        Brewery specialBrewery = new Brewery(1L, "Browar Specjal", 23, DateUtils.parseDate("2015-01-01"), new HashSet<>(Arrays.asList(special, braniewo)));
        Brewery tyskieBrewery = new Brewery(2L, "Browar w Tychach", 432, DateUtils.parseDate("1410-07-15"), new HashSet<>(Arrays.asList(tyskie, ksiazece)));

        Brewer brewerJan = new Brewer(1L, "Jan", 24, new HashSet<>(Arrays.asList(special, tyskie, ksiazece, braniewo)));
        Brewer brewerMarcin = new Brewer(2L, "Marcin", 56, new HashSet<>(Arrays.asList(specjalMocny, ksiazeceIpa, ksiazeceCzerwone)));

        beers.put(special.getId(), special);
        beers.put(tyskie.getId(), tyskie);
        beers.put(ksiazece.getId(), ksiazece);
        beers.put(braniewo.getId(), braniewo);
        beers.put(specjalMocny.getId(), specjalMocny);
        beers.put(ksiazeceIpa.getId(), ksiazeceIpa);
        beers.put(ksiazeceCzerwone.getId(), ksiazeceCzerwone);

        breweries.put(specialBrewery.getId(), specialBrewery);
        breweries.put(tyskieBrewery.getId(), tyskieBrewery);

        brewers.put(brewerJan.getId(), brewerJan);
        brewers.put(brewerMarcin.getId(), brewerMarcin);
    }

    public Brewery findBrewery(Long id) {
        return breweries.get(id);
    }

    public Beer findBeer(Long id) {
        return beers.get(id);
    }

    public Brewer findBrewer(Long id) {
        return brewers.get(id);
    }

    public Collection<Brewer> findAllBrewers() {
        return brewers.values();
    }

    public Long saveBrewer(Brewer brewer) {
        setBrewerIdIfNotPresent(brewer);
        brewers.put(brewer.getId(), brewer);
        return brewer.getId();
    }

    public void removeBrewer(Brewer brewer) {
        brewers.remove(brewer.getId());
    }

    private void setBrewerIdIfNotPresent(Brewer brewer) {
        if (brewer.getId() == null) {
            if (brewers.isEmpty()) {
                brewer.setId(ID_FIRST_ELEMENT);
            } else {
                brewer.setId(brewers.lastKey() + NEXT_ELEMENT);
            }
        }
    }

    public Collection<Brewery> findAllBreweries() {
        return breweries.values();
    }


    public Long saveBrewery(Brewery brewery) {
        setBreweryIdIfNotePresent(brewery);
        breweries.put(brewery.getId(), brewery);
        return brewery.getId();
    }

    public void removeBrewery(Brewery brewery) {
        breweries.remove(brewery.getId());
    }

    private void setBreweryIdIfNotePresent(Brewery brewery) {
        if (brewery.getId() == null) {
            if (breweries.isEmpty()) {
                brewery.setId(ID_FIRST_ELEMENT);
            } else {
                brewery.setId(breweries.lastKey() + NEXT_ELEMENT);
            }
        }
    }

    public Collection<Beer> findAllBeers() {
        return beers.values();
    }

    public Long saveBeer(Beer beer) {
        if (beer.getId() == null) {
            setBeerId(beer);
        } else {
            updateBeerInCollections(beer);
        }
        beers.put(beer.getId(), beer);
        return beer.getId();
    }

    private void updateBeerInCollections(Beer beer) {
        breweries.values().stream()
                .filter(brewery -> brewery.getBeers().contains(beer)).forEach(
                        brewery -> {
                            brewery.getBeers().remove(beer);
                            brewery.getBeers().add(beer);
                        }
        );
        brewers.values().stream()
                .filter(brewer -> brewer.getBeers().contains(beer)).forEach(
                        brewer -> {
                            brewer.getBeers().remove(beer);
                            brewer.getBeers().add(beer);
                        }
        );
    }

    public void removeBeer(Beer beer) {
        breweries.values().stream()
                .filter(brewery -> brewery.getBeers().contains(beer)).forEach(
                brewery -> brewery.getBeers().remove(beer)
        );
        brewers.values().stream()
                .filter(brewer -> brewer.getBeers().contains(beer)).forEach(
                brewer -> brewer.getBeers().remove(beer)
        );
        beers.remove(beer.getId());
    }

    private void setBeerId(Beer beer) {
        if (beers.isEmpty()) {
            beer.setId(ID_FIRST_ELEMENT);
        } else {
            beer.setId(beers.lastKey() + NEXT_ELEMENT);
        }
    }
}
