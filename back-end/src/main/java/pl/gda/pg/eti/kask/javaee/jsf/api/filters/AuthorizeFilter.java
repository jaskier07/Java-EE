/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gda.pg.eti.kask.javaee.jsf.api.filters;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import static javax.ws.rs.core.Response.status;

/**
 * @author stawrul
 */
@Provider
@Authorize
@Priority(Priorities.AUTHENTICATION)
public class AuthorizeFilter implements ContainerResponseFilter {

    @Context
    HttpServletRequest request;

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {



        responseContext.getHeaders().add("Access-Control-Expose-Headers", "*");
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, X-Codingpedia");
    }
}
