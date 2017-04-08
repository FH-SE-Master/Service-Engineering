package sve2.fhbay.dao;

import sve2.fhbay.domain.Customer;

import javax.ejb.Local;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 03/10/17
 */
@Local
public interface CustomerDao extends BaseDao<Customer, Long> {
}
