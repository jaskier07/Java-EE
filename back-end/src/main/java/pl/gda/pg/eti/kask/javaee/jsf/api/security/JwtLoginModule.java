package pl.gda.pg.eti.kask.javaee.jsf.api.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.security.Principal;
import java.security.acl.Group;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JwtLoginModule implements LoginModule {

    public static final String SIGNING_KEY = "czNjcjN0";

    private Subject subject;
    private CallbackHandler callbackHandler;

    private Principal identity;
    private Group roles;

    @Override
    public void initialize(final Subject subject, final CallbackHandler callbackHandler,
                           final Map<String, ?> sharedState, final Map<String, ?> options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
    }

    @Override
    public boolean login() throws LoginException {
        try {
            String jwt = getJwt();
            Jws<Claims> claims = Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(jwt);

            identity = getIdentity(claims);
            roles = getRoles(claims);

            return true;

        } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new FailedLoginException("Invalid token");
        } catch (ExpiredJwtException e) {
            throw new CredentialExpiredException("Token expired");
        }
    }

    @Override
    public boolean commit() throws LoginException {
        Set<Principal> principals = subject.getPrincipals();
        principals.add(identity);
        principals.add(roles);
        return true;
    }

    @Override
    public boolean abort() throws LoginException {
        identity = roles = null;
        return true;
    }

    @Override
    public boolean logout() throws LoginException {
        identity = roles = null;
        return true;
    }

    private Principal getIdentity(Jws<Claims> claims) {
        String username = claims.getBody().getSubject();
        return () -> username;
    }

    private Group getRoles(Jws<Claims> claims) {
        List<String> roleNames = claims.getBody().get("roles", List.class);
        RolesGroup roles = new RolesGroup(roleNames);
        return roles;
    }

    private String getJwt() throws LoginException {
        NameCallback callback = new NameCallback("prompt");
        
        try {
            callbackHandler.handle(new Callback[]{callback});
            String name = callback.getName();

            if (name == null) {
                throw new LoginException();
            }
            
            return name;

        } catch (IOException | UnsupportedCallbackException e) {
            throw new LoginException();
        }
    }
}
