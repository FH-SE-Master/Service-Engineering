package sve2.fhbay.dao;

import sve2.fhbay.domain.Customer;

import javax.ejb.Local;
import java.util.List;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 03/10/17
 */
@Local
public interface SimpleCustomerDao {

    void persist(Customer customer);

    Customer findById(Long id);

    List<Customer> findAll();
}
