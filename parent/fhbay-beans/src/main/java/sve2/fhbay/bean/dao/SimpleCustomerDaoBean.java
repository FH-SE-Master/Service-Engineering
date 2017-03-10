package sve2.fhbay.bean.dao;

import sve2.fhbay.dao.SimpleCustomerDao;
import sve2.fhbay.domain.Customer;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.List;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 03/10/17
 */
//@Stateless
//@Local(SimpleCustomerDao.class)
public class SimpleCustomerDaoBean implements SimpleCustomerDao {

    @Override
    public void persist(Customer customer) {
        System.out.println("persist called");
    }

    @Override
    public Customer findById(Long id) {
        System.out.println("findById called");
        return null;
    }

    @Override
    public List<Customer> findAll() {
        System.out.println("findAll called");
        return null;
    }
}
