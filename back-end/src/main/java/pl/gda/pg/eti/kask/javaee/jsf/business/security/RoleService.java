package pl.gda.pg.eti.kask.javaee.jsf.business.security;

import pl.gda.pg.eti.kask.javaee.jsf.business.model.entities.Role;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class RoleService {
    @PersistenceContext
    EntityManager em;

}
