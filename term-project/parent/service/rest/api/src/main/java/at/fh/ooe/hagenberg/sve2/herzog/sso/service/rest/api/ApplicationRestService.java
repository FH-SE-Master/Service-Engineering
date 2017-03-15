package at.fh.ooe.hagenberg.sve2.herzog.sso.service.rest.api;

import at.fh.ooe.hagenberg.sve2.herzog.sso.service.rest.api.model.json.AliveModel;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 03/15/17
 */
public interface ApplicationRestService extends Serializable {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("alive")
    AliveModel getAlive();
}
