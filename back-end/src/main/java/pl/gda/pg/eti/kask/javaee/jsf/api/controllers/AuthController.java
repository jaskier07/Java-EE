/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gda.pg.eti.kask.javaee.jsf.api.controllers;

import pl.gda.pg.eti.kask.javaee.jsf.api.filters.Authorize;
import pl.gda.pg.eti.kask.javaee.jsf.business.services.SecurityService;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * @author stawrul
 */
@RequestScoped
@Path("/login")
@Authorize
public class AuthController {

    @Inject
    SecurityService userService;


    @POST
    @Path("/login")
    @PermitAll
    public Response login(@QueryParam("login") String login,
                          @QueryParam("password") String password,
                          @Context HttpServletRequest request) {

        return userService.handleLogin(login, password);
    }

    @POST
    @Path("/logout")
    @PermitAll
    public Response logout(@QueryParam("login") String login,
                          @QueryParam("secret") String secret,
                          @Context HttpServletRequest request) {

        return userService.logout(login, secret);
    }

    @POST
    @Path("/password")
    @PermitAll
    public Response changePassword(
                                   @QueryParam("login") String login,
                                   @QueryParam("oldPsswd") String oldPsswd,
                          @QueryParam("newPsswd") String newPsswd,
                          @Context HttpServletRequest request) {

        return userService.changePassword(login, oldPsswd, newPsswd);
    }
}
