package sve2.fhbay.interfaces;

import sve2.fhbay.domain.Customer;
import sve2.fhbay.exceptions.IdNotFoundException;

import javax.ejb.Remote;
import java.io.Serializable;
import java.util.List;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 03/10/17
 */
@Remote
public interface CustomerAdminRemote extends CustomerAdmin {
}
