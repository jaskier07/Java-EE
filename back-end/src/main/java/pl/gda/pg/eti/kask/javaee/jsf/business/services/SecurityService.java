/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gda.pg.eti.kask.javaee.jsf.business.services;

import pl.gda.pg.eti.kask.javaee.jsf.business.entities.User;
import pl.gda.pg.eti.kask.javaee.jsf.business.entities.queries.UserQueries;
import pl.gda.pg.eti.kask.javaee.jsf.utils.CryptUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.Arrays;
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
    private Map<String, UUID> loggedUsers = new HashMap<>();

    @PersistenceContext
    EntityManager em;

    public boolean checkPriviledge(String login, UUID uuid, String expectedRole) {
        if (!checkIfUserLoggedIn(login)) {
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

    public UUID tryToLogUser(String login, String password) {
        User user = findUser(login);
        if (user != null && user.getPassword().equals(CryptUtils.sha256(password))) {
            UUID secret = UUID.randomUUID();
            loggedUsers.put(login, secret);
            return secret;
        } else {
            return null;
        }
    }

    public Response handleLogin(String login, String password) {
        if (checkIfUserLoggedIn(login)) {
            return Response.ok().header(AUTHORIZATION, "OK").header("login", login).header("secret", loggedUsers.get(login)).build();
        }

        UUID secret = tryToLogUser(login, password);
        if (secret == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        return Response.ok().header(AUTHORIZATION, "OK").header("login", login).header("secret", secret).build();
    }

    @Transactional
    public Response handleRegister(String login, String password) {
        List<String> users = findAllUserLogins();
        if (users.contains(login)) {
            return Response.status(Response.Status.CONFLICT).header("notUniqueLogin", true).build();
        }
        em.persist(new User(login, CryptUtils.sha256(password), Arrays.asList("USER")));
        return Response.ok().status(Response.Status.CREATED).build();
    }

    @Transactional
    public Response changePassword(String login, String oldPsswd, String newPsswd) {
        User user = findUser(login);
        if (user == null) {
            return handleError();
        }
        if (!user.getPassword().equals(CryptUtils.sha256(oldPsswd))) {
            return handleError();
        }
        user.setPassword(CryptUtils.sha256(newPsswd));
        em.merge(user);
        return Response.ok().build();
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


    public List<User> findAllUsers() {
        return em.createNamedQuery(UserQueries.FIND_ALL, User.class).getResultList();
    }

    public User findUser(String login) {
        return findUserByLogin(login);
    }

    private List<String> findAllUserLogins() {
        return em.createNamedQuery(UserQueries.FIND_ALL_LOGINS, String.class)
                .getResultList();
    }


    private User findUserByLogin(String login) {
        TypedQuery<User> query = em.createNamedQuery(UserQueries.FIND_BY_LOGIN, User.class);
        query.setParameter("login", login);
        return query.getSingleResult();
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

    public boolean checkIfUserLoggedIn(String login) {
        return loggedUsers.containsKey(login);
    }

}
