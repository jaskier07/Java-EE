package pl.gda.pg.eti.kask.javaee.jsf.business.services;

import pl.gda.pg.eti.kask.javaee.jsf.business.model.entities.User;
import pl.gda.pg.eti.kask.javaee.jsf.business.model.queries.UserQueries;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ApplicationScoped
public class UserService {
    private static final String HEADER_LOGIN = "login";
    private static final String HEADER_SECRET = "secret";

    @PersistenceContext
    EntityManager em;

    public List<User> findAllUsers() {
        return em.createNamedQuery(UserQueries.FIND_ALL, User.class)
                .getResultList();
    }

    public User findUser(String login) {
        return findUserByLogin(login);
    }

    public List<String> findAllUserLogins() {
        return em.createNamedQuery(UserQueries.FIND_ALL_LOGINS, String.class)
                .getResultList();
    }

    public User findUserByLogin(String login) {
        TypedQuery<User> query = em.createNamedQuery(UserQueries.FIND_BY_LOGIN, User.class);
        query.setParameter("login", login);
        return query.getSingleResult();
    }

    public String getLoginFromRequest(HttpServletRequest request) {
        return request.getHeader(HEADER_LOGIN);
    }

    public String getSecretFromRequest(HttpServletRequest request) {
        return request.getHeader(HEADER_SECRET);
    }
}
