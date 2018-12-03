//package pl.gda.pg.eti.kask.javaee.jsf.api.filters.implementations;
//
//import javax.annotation.Priority;
//import javax.ws.rs.Priorities;
//import javax.ws.rs.container.ContainerRequestContext;
//import javax.ws.rs.container.ContainerResponseContext;
//import javax.ws.rs.container.ContainerResponseFilter;
//import javax.ws.rs.ext.Provider;
//
//import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
//import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
//
//@Provider
//@Priority(Priorities.ENTITY_CODER)
//public class ContentTypeFilter implements ContainerResponseFilter {
//
//    @Override
//    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)  {
//        responseContext.getHeaders().putSingle(CONTENT_TYPE, APPLICATION_JSON_TYPE.withCharset("utf-8").toString());
//    }
//}
