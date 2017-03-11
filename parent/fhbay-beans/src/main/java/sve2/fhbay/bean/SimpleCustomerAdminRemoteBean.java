package sve2.fhbay.bean;

import sve2.fhbay.dao.SimpleCustomerDao;
import sve2.fhbay.domain.Customer;
import sve2.fhbay.interfaces.SimpleCustomerAdminRemote;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.util.List;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 03/10/17
 */
@Stateless
@Remote(SimpleCustomerAdminRemote.class)
public class SimpleCustomerAdminRemoteBean implements SimpleCustomerAdminRemote {

    @EJB
    private SimpleCustomerDao dao;

    @Override
    public Long saveCustomer(Customer customer) {
        System.out.println("saveCustomer called. customer.id" + customer.getId());
        dao.persist(customer);
        return customer.getId();
    }

    @Override
    public Customer findCustomerById(Long id) {
        System.out.println("findCustomer called");
        return dao.findById(id);
    }

    @Override
    public List<Customer> findAllCustomers() {
        System.out.println("findAllCustomers called");
        return dao.findAll();
    }
}
