package sve2.fhbay.interfaces;

import sve2.fhbay.domain.Customer;
import sve2.fhbay.exceptions.IdNotFoundException;

import java.io.Serializable;
import java.util.List;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/07/17
 */
public interface CustomerAdmin extends Serializable {
    Long saveCustomer(Customer customer);

    Customer findCustomerById(Long id) throws IdNotFoundException;

    List<Customer> findAllCustomers();
}
