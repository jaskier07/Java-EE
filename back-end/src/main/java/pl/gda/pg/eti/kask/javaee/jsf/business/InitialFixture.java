package pl.gda.pg.eti.kask.javaee.jsf.business;

import pl.gda.pg.eti.kask.javaee.jsf.business.entities.Beer;
import pl.gda.pg.eti.kask.javaee.jsf.business.entities.Brewer;
import pl.gda.pg.eti.kask.javaee.jsf.business.entities.Brewery;
import pl.gda.pg.eti.kask.javaee.jsf.business.entities.User;
import pl.gda.pg.eti.kask.javaee.jsf.business.services.SecurityService;
import pl.gda.pg.eti.kask.javaee.jsf.utils.DateUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;

@ApplicationScoped
public class InitialFixture {

    @PersistenceContext
    EntityManager em;

    @Inject
    SecurityService userService;

    @Transactional
    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        Beer piwo1 = new Beer("Książęce Czerwony Lager", 1.7, 11);
        Beer piwo2 = new Beer("Książęce Złote", 2.7, 21);
        Beer piwo3 = new Beer("Książęce Niebieskie", 3.7, 3);
        Beer piwo4 = new Beer("Książęce Seledynowe", 4.7, 41);
        Beer piwo5 = new Beer("Książęce Oberżyna", 5.1, 51);
        Beer piwo6 = new Beer("Harnaś", 5.7, 81);
        Beer piwo7 = new Beer("Specjal", 6.5, 23);
        Beer piwo8 = new Beer("Tyskie Jasne Pełne", 4.9, 12);
        Beer piwo9 = new Beer("Książęce Złote Pszeniczne", 5.2, 14);
        Beer piwo10 = new Beer("Braniewo Chmielowe", 5.0, 21);
        Beer piwo11 = new Beer("Specjal X", 8.0, 32);
        Beer piwo12 = new Beer("Książęce IPA", 4.0, 1);
        Beer piwo13 = new Beer("Specjal bursztynowy", 4.5, 13);
        Beer piwo14 = new Beer("Specjal specjalny", 3.5, 113);
        Beer piwo15 = new Beer("Specjal Mocne", 2.5, 53);
        Beer piwo16 = new Beer("Specjal Cienias", 1.5, 13);
        Beer piwo17 = new Beer("Miodna Pszczółka", 1.5, 13);

        Brewery specialBrewery = new Brewery("Browar Specjal", 23, DateUtils.parseDate("2015-01-01"), new HashSet<>(Arrays.asList(piwo7, piwo13)));
        Brewery tyskieBrewery = new Brewery("Browar w Tychach", 432, DateUtils.parseDate("1410-07-15"), new HashSet<>(Arrays.asList(piwo1, piwo2)));

        Brewer brewerJan = new Brewer("Jan Kowalski", 24, new HashSet<>(Arrays.asList(piwo7, piwo1, piwo2, piwo13)));
        Brewer brewerMarcin = new Brewer("Marcin Płażyński", 56, new HashSet<>(Arrays.asList(piwo16, piwo15, piwo14)));

        em.persist(piwo1);
        em.persist(piwo2);
        em.persist(piwo3);
        em.persist(piwo4);
        em.persist(piwo5);
        em.persist(piwo6);
        em.persist(piwo7);
        em.persist(piwo8);
        em.persist(piwo9);
        em.persist(piwo10);
        em.persist(piwo11);
        em.persist(piwo12);
        em.persist(piwo13);
        em.persist(piwo14);
        em.persist(piwo15);
        em.persist(piwo16);
        em.persist(piwo17);

        em.flush();

        em.persist(specialBrewery);
        em.persist(tyskieBrewery);

        em.persist(brewerJan);
        em.persist(brewerMarcin);

        User admin = new User("admin", "admin", Arrays.asList("ADMIN", "USER"));
        User user = new User("user", "user", Arrays.asList("USER"));

        em.persist(admin);
        em.persist(user);

    }
}
