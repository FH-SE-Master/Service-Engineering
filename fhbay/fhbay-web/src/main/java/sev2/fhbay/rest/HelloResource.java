package sev2.fhbay.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.io.Serializable;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/07/17
 */
@Path("/hello")
public class HelloResource implements Serializable {

    @GET
    @Path("get")
    @Produces("text/plain")
    public String getHelloMessage() {
        return "hello from JAX-RS";
    }
}
