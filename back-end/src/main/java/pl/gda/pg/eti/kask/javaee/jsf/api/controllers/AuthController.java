/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gda.pg.eti.kask.javaee.jsf.api.controllers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.jbosslog.JBossLog;
import pl.gda.pg.eti.kask.javaee.jsf.api.filters.Authorize;
import pl.gda.pg.eti.kask.javaee.jsf.business.entities.User;
import pl.gda.pg.eti.kask.javaee.jsf.business.services.UserService;
import pl.gda.pg.eti.kask.javaee.jsf.utils.CryptUtils;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Date;

import static java.time.ZonedDateTime.now;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

/**
 * @author stawrul
 */
@Path("/login")
public class AuthController {

    //BASE64(s3cr3t) = czNjcjN0
    public static final String SIGNING_KEY = "czNjcjN0";

    @Inject
    UserService userService;



    @POST
    @Path("/token")
    @PermitAll
    public Response issueToken(@QueryParam("login") String login,
                               @QueryParam("password") String password,
                               @Context HttpServletRequest request) {
        try {
            System.out.println(login + ", " + password);
            request.login(login, password);
        } catch (ServletException e) {
            e.printStackTrace();
            throw new NotAuthorizedException(e, "Form");
        }

        User user = userService.findCurrentUser();
        String token = buildJWT(user);

        return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();
    }

    private String buildJWT(User user) {
        Date issuedAt = new Date();
        Date expirationAt = Date.from(now().plusMinutes(15).toInstant());

        String jwtToken = Jwts.builder()
                .setSubject(user.getLogin())
                .setIssuer("Java EE Example Enterprise App")
                .setIssuedAt(issuedAt)
                .setExpiration(expirationAt)
                .claim("roles", user.getRoles())
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();

        return jwtToken;
    }

}
