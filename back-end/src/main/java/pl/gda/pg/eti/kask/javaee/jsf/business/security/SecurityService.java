/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gda.pg.eti.kask.javaee.jsf.business.security;

import pl.gda.pg.eti.kask.javaee.jsf.business.model.entities.User;
import pl.gda.pg.eti.kask.javaee.jsf.business.services.UserService;
import pl.gda.pg.eti.kask.javaee.jsf.utils.CryptUtils;
import pl.gda.pg.eti.kask.javaee.jsf.utils.UserUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

@ApplicationScoped
public class SecurityService {
    private Map<String, UUID> loggedUsers = new HashMap<>();

    @PersistenceContext
    private EntityManager em;

    @RequestScoped
    @Inject
    private HttpServletRequest request;

    @Inject
    private UserService userService;

    public boolean verifyUser(String login, UUID uuid) {
        if (!checkIfUserLoggedIn(login)) {
            return handleErrorBoolean();
        }
        if (!loggedUsers.get(login).equals(uuid)) {
            return handleErrorBoolean();
        }
        return true;
    }

    public boolean verifyUser() {
        String login = userService.getLoginFromRequest(request);
        String uuidString = userService.getSecretFromRequest(request);
        if (login == null || uuidString == null) {
            return false;
        }
        UUID uuid = UUID.fromString(uuidString);
        return verifyUser(login, uuid);
    }

    public UUID tryToLogUser(String login, String password) {
        User user = userService.findUser(login);
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
        List<String> users = userService.findAllUserLogins();
        if (users.contains(login)) {
            return Response.status(Response.Status.CONFLICT).header("notUniqueLogin", true).build();
        }
        em.persist(new User(login, CryptUtils.sha256(password), UserUtils.createUserRole()));
        return Response.ok().status(Response.Status.CREATED).build();
    }

    @Transactional
    public Response changePassword(String login, String oldPsswd, String newPsswd) {
        User user = userService.findUser(login);
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
        User user = userService.findUser(login);
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

    public boolean checkIfUserLoggedIn(String login) {
        return loggedUsers.containsKey(login);
    }

}
