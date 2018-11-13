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
        Beer piwo1 = new Beer(1L, "Książęce Czerwony Lager", 1.7, 11);
        Beer piwo2 = new Beer(2L, "Książęce Złote", 2.7, 21);
        Beer piwo3 = new Beer(3L, "Książęce Niebieskie", 3.7, 3);
        Beer piwo4 = new Beer(4L, "Książęce Seledynowe", 4.7, 41);
        Beer piwo5 = new Beer(5L, "Książęce Oberżyna", 5.1, 51);
        Beer piwo6 = new Beer(6L, "Harnaś", 5.7, 81);
        Beer piwo7 = new Beer(7L, "Specjal", 6.5, 23);
        Beer piwo8 = new Beer(8L, "Tyskie Jasne Pełne", 4.9, 12);
        Beer piwo9 = new Beer(9L, "Książęce Złote Pszeniczne", 5.2, 14);
        Beer piwo10 = new Beer(10L, "Braniewo Chmielowe", 5.0, 21);
        Beer piwo11 = new Beer(11L, "Specjal X", 8.0, 32);
        Beer piwo12 = new Beer(12L, "Książęce IPA", 4.0, 1);
        Beer piwo13 = new Beer(13L, "Specjal bursztynowy", 4.5, 223);
        Beer piwo14 = new Beer(14L, "Specjal specjalny", 3.5, 123);
        Beer piwo15 = new Beer(15L, "Specjal Mocne", 2.5, 53);
        Beer piwo16 = new Beer(16L, "Specjal Cienias", 1.5, 13);
        Beer piwo17 = new Beer(17L, "Specjal Cienias", 1.5, 13);

        Brewery specialBrewery = new Brewery(1L, "Browar Specjal", 23, DateUtils.parseDate("2015-01-01"), new HashSet<>(Arrays.asList(piwo7, piwo13)));
        Brewery tyskieBrewery = new Brewery(2L, "Browar w Tychach", 432, DateUtils.parseDate("1410-07-15"), new HashSet<>(Arrays.asList(piwo1, piwo2)));

        Brewer brewerJan = new Brewer(1L, "Jan", 24, new HashSet<>(Arrays.asList(piwo7, piwo1, piwo2, piwo13)));
        Brewer brewerMarcin = new Brewer(2L, "Marcin", 56, new HashSet<>(Arrays.asList(piwo16, piwo15, piwo14)));

        beers.put(piwo1.getId(), piwo1);
        beers.put(piwo2.getId(), piwo2);
        beers.put(piwo3.getId(), piwo3);
        beers.put(piwo4.getId(), piwo4);
        beers.put(piwo5.getId(), piwo5);
        beers.put(piwo6.getId(), piwo6);
        beers.put(piwo7.getId(), piwo7);
        beers.put(piwo8.getId(), piwo8);
        beers.put(piwo9.getId(), piwo9);
        beers.put(piwo10.getId(), piwo10);
        beers.put(piwo11.getId(), piwo11);
        beers.put(piwo12.getId(), piwo12);
        beers.put(piwo13.getId(), piwo13);
        beers.put(piwo14.getId(), piwo14);
        beers.put(piwo15.getId(), piwo15);
        beers.put(piwo16.getId(), piwo16);
        beers.put(piwo17.getId(), piwo17);

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
