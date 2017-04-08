package sve2.fhbay.interfaces;

import sve2.fhbay.domain.Customer;

import javax.ejb.Remote;
import java.io.Serializable;
import java.util.List;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 03/10/17
 */
@Remote
public interface SimpleCustomerAdminRemote extends Serializable {

    Long saveCustomer(Customer customer);

    Customer findCustomerById(Long id);

    List<Customer> findAllCustomers();
}
