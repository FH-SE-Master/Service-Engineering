package sve2.fhbay.bean;

import sve2.fhbay.dao.CustomerDao;
import sve2.fhbay.domain.Customer;
import sve2.fhbay.exceptions.IdNotFoundException;
import sve2.fhbay.interfaces.CustomerAdminLocal;
import sve2.fhbay.interfaces.CustomerAdminRemote;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 03/10/17
 */
@Stateless
public class CustomerAdminRemoteBean implements CustomerAdminRemote, CustomerAdminLocal {

    @EJB
    private CustomerDao dao;

    @Override
    public Long saveCustomer(Customer customer) {
        System.out.println("saveCustomer called. customer.id" + customer.getId());
        if (customer.getId() != null) {
            dao.persist(customer);
        } else {
            dao.merge(customer);
        }

        return customer.getId();
    }

    @Override
    public Customer findCustomerById(Long id) throws IdNotFoundException {
        System.out.println("findCustomer called");
        final Customer customer = dao.findById(id);
        if (customer == null) {
            throw new IdNotFoundException(Customer.class.getName(), id);
        }

        return customer;
    }

    @Override
    public List<Customer> findAllCustomers() {
        System.out.println("findAllCustomers called");
        return dao.findAll();
    }
}
