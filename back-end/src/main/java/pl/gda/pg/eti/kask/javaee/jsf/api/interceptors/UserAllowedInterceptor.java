package pl.gda.pg.eti.kask.javaee.jsf.api.interceptors;

import pl.gda.pg.eti.kask.javaee.jsf.business.model.entities.Beer;
import pl.gda.pg.eti.kask.javaee.jsf.business.model.entities.Brewer;
import pl.gda.pg.eti.kask.javaee.jsf.business.model.entities.Brewery;
import pl.gda.pg.eti.kask.javaee.jsf.business.model.entities.User;
import pl.gda.pg.eti.kask.javaee.jsf.business.security.Permission;
import pl.gda.pg.eti.kask.javaee.jsf.business.services.UserService;

import javax.annotation.Priority;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Field;

@Interceptor
@CheckPrivelege
@Priority(Interceptor.Priority.APPLICATION)
public class UserAllowedInterceptor implements Serializable {

    @Inject
    private UserService userService;

    @RequestScoped
    @Inject
    private HttpServletRequest request;

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {
        String enteringMethodName = context.getMethod().getName();

        String login = userService.getLoginFromRequest(request);
        if (login == null) {
            throw new NullPointerException();
        }
        User user = userService.findUserByLogin(login);
        if (user == null) {
            throw new NullPointerException();
        }
        try {
            if (isUserInRole(user, enteringMethodName, context)) {
                return context.proceed();
            } else {
                throw new NullPointerException();
            }
        } catch (Exception e) {
            throw new NullPointerException();
        }
    }

    private boolean isUserInRole(User user, String enteringMethodName, InvocationContext context) throws NoSuchFieldException, IllegalAccessException {
        Field field = user.getRole().getClass().getDeclaredField(enteringMethodName);
        field.setAccessible(true);
        Permission userPermission = (Permission)field.get(user.getRole());
        if (userPermission.equals(Permission.GRANTED)) {
            return true;
        }  else if (userPermission.equals(Permission.IF_OWNER)) {
            return isOwner(user, context);
        }
        else {
            return false;
        }
    }

    private boolean isOwner(User user, InvocationContext context) {
        Object[] parameters = context.getParameters();
        for (Object parameter : parameters) {
            if (parameter instanceof Beer) {
                 Beer beer = (Beer)parameter;
                 return beer.getOwner().equals(user);
            } else if (parameter instanceof Brewer) {
                Brewer brewer = (Brewer)parameter;
                return brewer.getOwner().equals(user);
            } else if (parameter instanceof Brewery) {
                Brewery brewery = (Brewery)parameter;
                return brewery.getOwner().equals(user);
            }
        }

        return false;
    }


}
