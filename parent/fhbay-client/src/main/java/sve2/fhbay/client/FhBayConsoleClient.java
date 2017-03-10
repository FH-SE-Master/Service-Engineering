package sve2.fhbay.client;

import sve2.fhbay.domain.Customer;
import sve2.fhbay.interfaces.SimpleCustomerAdminRemote;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 03/10/17
 */
public class FhBayConsoleClient {

    private static void testSimpleCustomerAdmin() throws NamingException {
        String jndiName = "fhbay-beans/SimpleCustomerAdminRemoteBean!sve2.fhbay.interfaces.SimpleCustomerAdminRemote";

        final Context ctx = new InitialContext();
        SimpleCustomerAdminRemote remoteBean = (SimpleCustomerAdminRemote) ctx.lookup(jndiName);

        final Customer customer1 = new Customer("Thomas", "Herzog", "cchet", "paswword", "thomas.herzog@fh-ooe.at");
        final Customer customer2 = new Customer("Hugo", "Mayer", "hudo", "paswword", "hugo.mayer@fh-ooe.at");

        final Long customerId1 = remoteBean.saveCustomer(customer1);
        final Long customerId2 = remoteBean.saveCustomer(customer2);

        System.out.println("Customer-id: " + customerId1);
        System.out.println("Customer-id: " + customerId2);
    }

    public static void main(String[] args) throws NamingException {
        testSimpleCustomerAdmin();
    }
}
