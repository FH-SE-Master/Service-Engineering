package sve2.fhbay.bean.dao;

import sve2.fhbay.dao.CustomerDao;
import sve2.fhbay.domain.Customer;

import javax.ejb.Stateless;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/07/17
 */
@Stateless
public class CustomerDaoBean extends BaseDaoBean<Customer, Long> implements CustomerDao {
}
