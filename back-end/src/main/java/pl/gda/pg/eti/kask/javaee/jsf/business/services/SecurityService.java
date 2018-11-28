/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gda.pg.eti.kask.javaee.jsf.business.services;

import pl.gda.pg.eti.kask.javaee.jsf.business.entities.User;

import javax.ejb.EJBAccessException;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.constraints.Null;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

@ApplicationScoped
public class SecurityService {
    //BASE64(s3cr3t) = czNjcjN0
    public static final String HEADER_LOGIN = "login";
    public static final String HEADER_SECRET = "secret";
    public static final String SIGNING_KEY = "czNjcjN0";
    private Map<String, UUID> loggedUsers = new HashMap<>();

    @PersistenceContext
    EntityManager em;

    public boolean checkPriviledge(String login, UUID uuid, String expectedRole) {
        if (!loggedUsers.containsKey(login)) {
          return  handleErrorBoolean();
        }
        if (!loggedUsers.get(login).equals(uuid)) {
            return  handleErrorBoolean();
        }
        if (findUser(login).getRoles().contains(expectedRole)) {
            return true;
        }
        return handleErrorBoolean();
    }

    public boolean checkPriviledge(HttpServletRequest httpRequest, String expectedRole) {
       String loginOpt = httpRequest.getHeader(HEADER_LOGIN);
        String uuidOpt = httpRequest.getHeader(HEADER_SECRET);
        if (loginOpt == null || uuidOpt == null) {
            return false;
        }
        String login = loginOpt;
        UUID uuid = UUID.fromString(uuidOpt);
       return checkPriviledge(login, uuid, expectedRole);
    }

    public boolean checkIfUserLoggedIn(String login) {
        return loggedUsers.containsKey(login);
    }

    public UUID tryToLogUser(String login, String password) {
        User user = findUser(login);
        if (user != null && user.getPassword().equals(password)) {
            UUID secret = UUID.randomUUID();
            loggedUsers.put(login, secret);
            return secret;
        } else {
            return null;
        }
    }

    public Response handleLogin(String login, String password) {
        if (loggedUsers.containsKey(login)) {
            return Response.ok().header(AUTHORIZATION, "OK").header("login", login).header("secret", loggedUsers.get(login)).build();
        }

        UUID secret = tryToLogUser(login, password);
        if (secret == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        return Response.ok().header(AUTHORIZATION, "OK").header("login", login).header("secret", secret).build();
    }

    @Transactional
    public Response changePassword(String login, String oldPsswd, String newPsswd) {
        User user = findUser(login);
        if (user == null) {
            return handleError();
        }
        if (!user.getPassword().equals(oldPsswd)) {
            return handleError();
        }
        user.setPassword(newPsswd);
        em.merge(user);
        return Response.ok().build();
    }


//    @RolesAllowed(User.Roles.ADMIN)
    public List<User> findAllUsers() {
        return em.createNamedQuery(User.Queries.FIND_ALL, User.class).getResultList();
    }

//    @RolesAllowed(User.Roles.ADMIN)
    public User findUser(String login) {
        return findUserByLogin(login);
    }

//    @RolesAllowed({User.Roles.ADMIN, User.Roles.USER})
//    public User findCurrentUser() {
//        String login = sessionCtx.getCallerPrincipal().getName();
//        return findUserByLogin(login);
//    }

    private User findUserByLogin(String login) {
        TypedQuery<User> query = em.createNamedQuery(User.Queries.FIND_BY_LOGIN, User.class);
        query.setParameter("login", login);
        return query.getSingleResult();
    }


    public Response logout(String login, String secret) {
        if (!loggedUsers.containsKey(login)) {
            return Response.ok().build();
        }
        User user = findUser(login);
        if (!loggedUsers.get(login).equals(UUID.fromString(secret))) {
            return handleError();
        }
        loggedUsers.remove(login);
        return Response.ok().build();
    }

    private void throwError() {
        throw new NullPointerException();
    }

    private Response handleError() {
        throwError();
        return null;
    }

    private boolean handleErrorBoolean() {
        throwError();
        return false;
    }
}
