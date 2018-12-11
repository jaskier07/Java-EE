package pl.gda.pg.eti.kask.javaee.jsf.business.services;

import pl.gda.pg.eti.kask.javaee.jsf.api.interceptors.CheckPrivelege;
import pl.gda.pg.eti.kask.javaee.jsf.business.model.entities.Beer;
import pl.gda.pg.eti.kask.javaee.jsf.business.model.entities.Beer_;
import pl.gda.pg.eti.kask.javaee.jsf.business.model.entities.Brewer;
import pl.gda.pg.eti.kask.javaee.jsf.business.model.entities.Brewery;
import pl.gda.pg.eti.kask.javaee.jsf.business.model.queries.BeerQueries;
import pl.gda.pg.eti.kask.javaee.jsf.business.model.queries.BrewerQueries;
import pl.gda.pg.eti.kask.javaee.jsf.business.model.queries.BreweryQueries;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@ApplicationScoped
public class BreweryService {
    private static final int MATURE_AGE = 18;
    private static final int MAX_AGE = 100;

    @PersistenceContext
    EntityManager em;

    @RequestScoped
    @Inject
    private HttpServletRequest request;


    @Inject
    private UserService userService;

    /* brewers */
    @CheckPrivelege
    public Brewer findBrewer(Long id) {
        return em.createNamedQuery(BrewerQueries.FIND_ONE, Brewer.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @CheckPrivelege
    public List<Brewer> findAllBrewers() {
        TypedQuery<Brewer> query = em.createNamedQuery(BrewerQueries.FIND_ALL, Brewer.class);
        return query.getResultList();
    }

    @Transactional
    @CheckPrivelege
    public Long updateBrewery(Brewery brewery) {
        brewery = em.merge(brewery);
        em.flush();
        return brewery.getId();
    }

    public Long saveOrUpdateBeer(Beer beer) {
        if (beer.getId() == null) {
            return saveBeer(beer);
        } else {
            return updateBeer(beer);
        }
    }

    @Transactional
    @CheckPrivelege
    public Long updateBeer(Beer beer) {
        beer = em.merge(beer);
        em.flush();
        return beer.getId();
    }

    public Long saveOrUpdateBrewer(Brewer brewer) {
        if (brewer.getId() == null) {
            return saveBrewer(brewer);
        } else {
            return updateBrewer(brewer);
        }
    }

    @Transactional
    @CheckPrivelege
    public Long saveBrewer(Brewer brewer) {
        for (Beer beer : brewer.getBeers()) {
            em.merge(beer);
        }
        brewer.setOwner(userService.findUser(userService.getLoginFromRequest(request)));
        em.persist(brewer);
        em.flush();
        return brewer.getId();
    }

    @Transactional
    @CheckPrivelege
    public Long updateBrewer(Brewer brewer) {
        brewer = em.merge(brewer);
        em.flush();
        return brewer.getId();
    }

    @Transactional
    @CheckPrivelege
    public void removeBrewer(Brewer brewer) {
        brewer = em.merge(brewer);
        em.remove(brewer);
    }

    /* breweries */
    @CheckPrivelege
    public Brewery findBrewery(Long id) {
        return em.createNamedQuery(BreweryQueries.FIND_ONE, Brewery.class)
                .setParameter("id", id)
                .getSingleResult();
    }


    @CheckPrivelege
    public Collection<Brewery> findAllBreweries() {
        TypedQuery<Brewery> query = em.createNamedQuery(BreweryQueries.FIND_ALL, Brewery.class);
        return query.getResultList();
    }

    @Transactional
    @CheckPrivelege
    public Long saveBrewery(Brewery brewery) {
        brewery.setOwner(userService.findUser(userService.getLoginFromRequest(request)));
        em.persist(brewery);
        em.flush();
        return brewery.getId();
    }

    @Transactional
    @CheckPrivelege
    public void removeBrewery(Brewery brewery) {
        brewery = em.merge(brewery);
        em.remove(brewery);
    }

    /* beers */
    @CheckPrivelege
    public Beer findBeer(Long id) {
        return em.createNamedQuery(BeerQueries.FIND_ONE, Beer.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @CheckPrivelege
    public Collection<Beer> findAllBeers() {
        return em.createNamedQuery(BeerQueries.FIND_ALL, Beer.class)
                .getResultList();
    }

    @Transactional
    @CheckPrivelege
    public Long saveBeer(Beer beer) {
        beer.setOwner(userService.findUser(userService.getLoginFromRequest(request)));
        em.persist(beer);
        em.flush();
        return beer.getId();
    }


    @Transactional
    @CheckPrivelege
    public void removeBeer(Beer beer) {
        beer = em.merge(beer);
        em.remove(beer);
    }

    @CheckPrivelege
    public Collection<Brewer> findBrewersByAge(int from, int to) {
        return em.createNamedQuery(BrewerQueries.FIND_BY_AGE, Brewer.class)
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

    @Transactional
    @CheckPrivelege
    public List<Beer> filterBeers(String idString, String name, String voltageString, String ibuString) {
        Long id = tryToParseLong(idString);
        Double voltage = tryToParseDouble(voltageString);
        Integer ibu = tryToParseInteger(ibuString);

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Beer> query = builder.createQuery(Beer.class);
        Root<Beer> beer = query.from(Beer.class);
        query.select(beer);

        if (id != null) {
            query.where(builder.equal(beer.get(Beer_.id), id));
        }
        if (name != null && !(name.trim().equals(""))) {
            query.where(builder.like(builder.lower(beer.get(Beer_.name)), "%" + name.toLowerCase() + "%"));
        }
        if (voltage != null) {
            query.where(builder.equal(beer.get(Beer_.voltage), voltage));
        }
        if (ibu != null) {
            query.where(builder.equal(beer.get(Beer_.IBU), ibu));
        }

        return em.createQuery(query).getResultList();
    }

    private Long tryToParseLong(String value) {
        try {
            return Long.valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }

    private Integer tryToParseInteger(String value) {
        try {
            return Integer.valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }

    private Double tryToParseDouble(String value) {
        try {
            return Double.valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }
}
