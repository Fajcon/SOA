package pl.edu.agh.soa.lab1;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ejb.Stateless;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/test")
public class Hello {
    @GET
    @Path("/hello")
    @Produces(MediaType.APPLICATION_JSON)
    public String hello() {
        return "hello!";
    }
}