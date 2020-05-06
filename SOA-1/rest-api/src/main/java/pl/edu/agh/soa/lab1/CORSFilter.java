package pl.edu.agh.soa.lab1;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

//@Provider
//public class CORSFilter implements ContainerResponseFilter {
//
//    @Override
//    public void filter(final ContainerRequestContext requestContext,
//                       final ContainerResponseContext cres) throws IOException {
//        cres.getHeaders().add("Access-Control-Allow-Origin", "*");
//        cres.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
//    }
//
//}
