package pl.gda.pg.eti.kask.javaee.jsf.api.security;

import pl.gda.pg.eti.kask.javaee.jsf.business.entities.User;
import pl.gda.pg.eti.kask.javaee.jsf.business.services.UserService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.security.Principal;

@Named
@SessionScoped
public class AuthContext implements Serializable {

    @EJB
    UserService userService;

    private ExternalContext getExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public Principal getUserPrincipal() {
        return getExternalContext().getUserPrincipal();
    }

    public boolean isUserInRole(String role) {
        return getExternalContext().isUserInRole(role);
    }

    public boolean isAuthenticated() {
        return getUserPrincipal() != null;
    }

    public String getUserLogin() {
        return getUserPrincipal().getName();
    }

    public User getCurrentUser() {
        return userService.findCurrentUser();
    }

}
