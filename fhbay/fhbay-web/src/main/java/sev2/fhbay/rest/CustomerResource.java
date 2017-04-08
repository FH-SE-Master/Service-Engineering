package sev2.fhbay.rest;

import sve2.fhbay.domain.Customer;
import sve2.fhbay.interfaces.CustomerAdminLocal;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.List;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/07/17
 */
@Path("/customers")
public class CustomerResource implements Serializable {

    @EJB
    private CustomerAdminLocal customerAdmin;

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> getCustomerById() {
        return customerAdmin.findAllCustomers();
    }
}
