package pl.gda.pg.eti.kask.javaee.jsf.business.services;

import pl.gda.pg.eti.kask.javaee.jsf.business.model.entities.Beer;
import pl.gda.pg.eti.kask.javaee.jsf.business.model.entities.Brewer;
import pl.gda.pg.eti.kask.javaee.jsf.business.model.entities.Brewery;
import pl.gda.pg.eti.kask.javaee.jsf.business.model.queries.BeerQueries;
import pl.gda.pg.eti.kask.javaee.jsf.business.model.queries.BrewerQueries;
import pl.gda.pg.eti.kask.javaee.jsf.business.model.queries.BreweryQueries;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Collection;

@ApplicationScoped
public class BreweryService {
    private static final int MATURE_AGE = 18;
    private static final int MAX_AGE = 100;

    @PersistenceContext
    EntityManager em;

    @Inject
    private UserService userService;

    /* brewers */
    public Brewer findBrewer(Long id) {
        return em.createQuery(BrewerQueries.FIND_ONE, Brewer.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public Collection<Brewer> findAllBrewers() {
        TypedQuery<Brewer> query = em.createNamedQuery(BrewerQueries.FIND_ALL, Brewer.class);
        return query.getResultList();
    }

    @Transactional
    public Long saveBrewer(Brewer brewer, HttpServletRequest request) {
        if (brewer.getId() == null) {
            brewer.setOwner(userService.findUser(userService.getLoginFromRequest(request)));
            em.persist(brewer);
        } else {
            brewer = em.merge(brewer);
        }
        em.flush();
        return brewer.getId();
    }

    @Transactional
    public void removeBrewer(Brewer brewer) {
        brewer = em.merge(brewer);
        em.remove(brewer);
    }

    /* breweries */
    public Brewery findBrewery(Long id) {
        return em.createQuery(BreweryQueries.FIND_ONE, Brewery.class)
                .setParameter("id", id)
                .getSingleResult();
    }


    public Collection<Brewery> findAllBreweries() {
        TypedQuery<Brewery> query = em.createNamedQuery(BreweryQueries.FIND_ALL, Brewery.class);
        return query.getResultList();
    }

    @Transactional
    public Long saveBrewery(Brewery brewery, HttpServletRequest request) {
        if (brewery.getId() == null) {
            brewery.setOwner(userService.findUser(userService.getLoginFromRequest(request)));
            em.persist(brewery);
        } else {
            brewery = em.merge(brewery);
        }
        em.flush();
        return brewery.getId();
    }

    @Transactional
    public void removeBrewery(Brewery brewery) {
        brewery = em.merge(brewery);
        em.remove(brewery);
    }

    /* beers */
    public Beer findBeer(Long id) {
        return em.createQuery(BeerQueries.FIND_ONE, Beer.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public Collection<Beer> findAllBeers() {
        return em.createNamedQuery(BeerQueries.FIND_ALL, Beer.class)
                .getResultList();
    }

    @Transactional
    public Long saveBeer(Beer beer, HttpServletRequest request) {
        if (beer.getId() == null) {
            beer.setOwner(userService.findUser(userService.getLoginFromRequest(request)));
            em.persist(beer);
        } else {
            beer = em.merge(beer);
        }
        em.flush();
        return beer.getId();
    }


    @Transactional
    public void removeBeer(Beer beer) {
        beer = em.merge(beer);
        em.remove(beer);
    }

    public Collection<Brewer> findBrewersByAge(int from, int to) {
        return em.createQuery(BrewerQueries.FIND_BY_AGE, Brewer.class)
                .setParameter("from", normalizeValue(from))
                .setParameter("to", normalizeValue(to))
                .getResultList();
    }

    private int normalizeValue(int value) {
        if (value < MATURE_AGE) {
            return MATURE_AGE;
        } else if (value > MAX_AGE) {
            return MAX_AGE;
        } else {
            return value;
        }
    }
}
