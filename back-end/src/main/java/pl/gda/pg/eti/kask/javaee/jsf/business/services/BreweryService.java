package pl.gda.pg.eti.kask.javaee.jsf.business.services;

import pl.gda.pg.eti.kask.javaee.jsf.business.entities.Beer;
import pl.gda.pg.eti.kask.javaee.jsf.business.entities.Brewer;
import pl.gda.pg.eti.kask.javaee.jsf.business.entities.Brewery;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transaction;
import javax.transaction.Transactional;
import java.util.Collection;

@ApplicationScoped
public class BreweryService {

    @PersistenceContext
    EntityManager em;

    /* brewers */
    public Brewer findBrewer(Long id) {
        return em.createQuery("select b from Brewer b where b.id = :id", Brewer.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public Collection<Brewer> findAllBrewers() {
        TypedQuery<Brewer> query = em.createNamedQuery(Brewer.Queries.FIND_ALL, Brewer.class);
        return query.getResultList();
    }

    @Transactional
    public Long saveBrewer(Brewer brewer) {
        if (brewer.getId() == null) {
            em.persist(brewer);
            em.flush();
        } else {
            brewer = em.merge(brewer);
        }
        return brewer.getId();
    }

    @Transactional
    public void removeBrewer(Brewer brewer) {
        brewer = em.merge(brewer);
        em.remove(brewer);
    }

    /* breweries */
    public Brewery findBrewery(Long id) {
        return em.createQuery("select b from Brewery b where b.id = :id", Brewery.class)
                .setParameter("id", id)
                .getSingleResult();
    }


    public Collection<Brewery> findAllBreweries() {
        TypedQuery<Brewery> query = em.createNamedQuery(Brewery.Queries.FIND_ALL, Brewery.class);
        return query.getResultList();
    }

    @Transactional
    public Long saveBrewery(Brewery brewery) {
        if (brewery.getId() == null) {
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
        return em.createQuery("select b from Beer b where b.id = :id", Beer.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public Collection<Beer> findAllBeers() {
        return em.createNamedQuery(Beer.Queries.FIND_ALL, Beer.class)
                .getResultList();
    }

    @Transactional
    public Long saveBeer(Beer beer) {
        if (beer.getId() == null) {
            em.persist(beer);
            em.flush();
        } else {
            beer = em.merge(beer);
        }
        return beer.getId();
    }


    @Transactional
    public void removeBeer(Beer beer) {
        beer = em.merge(beer);
        em.remove(beer);
    }

    public Collection<Brewer> findBrewersByAge(int from, int to) {
        return em.createQuery("select b from Brewer b where (b.age >= :from and b.age < :to)")
                .setParameter("from", normalizeValue(from))
                .setParameter("to", normalizeValue(to))
                .getResultList();
    }

    private int normalizeValue(int value) {
        if (value < 18) {
            return 18;
        } else if (value > 100) {
            return 100;
        } else {
            return value;
        }
    }
}
