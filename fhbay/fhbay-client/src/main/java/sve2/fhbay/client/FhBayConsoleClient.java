package sve2.fhbay.client;

import sve2.fhbay.domain.Customer;
import sve2.fhbay.exceptions.IdNotFoundException;
import sve2.fhbay.interfaces.CustomerAdmin;

import javax.naming.NamingException;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 03/10/17
 */
public class FhBayConsoleClient {

    private static CustomerAdmin custAdmin;
    private static final String CUSTOMER_ADMIN_REMOTE_NAME = "fhbay/fhbay-beans/CustomerAdminRemoteBean!sve2.fhbay.interfaces.CustomerAdminRemote";

    private static void testCustomerAdmin() {
        try {
            System.out.println("--------------- save customer ---------------");
            System.out.println("--------------- save customer ---------------");
            Customer cust1 = new Customer("Armin", "Kogler", "akogler", "kogi", "akogler@gmx.at");
            //			cust1.setBillingAddress(new Address("4232", "Hagenberg", "Hauptstraße 117"));
            //			cust1.addShippingAddress(new Address("5555", "Mostbusch", "Linzerstraße 15"));
            //			cust1.addPhone(new Phone("mobile", "+43", "(0) 555 333"));
            //			cust1.addPhone(new Phone("business", "+43", "(0) 444 111"));
            //			cust1.addPhone("mobile", new Phone("+43", "(0) 555 333"));
            //			cust1.addPhone("business", new Phone("+43", "(0) 444 111"));
            //			cust1.addPaymentData(new CreditCard("Himmelbrunner", "010448812", DateUtil.getDate(2007, 07, 1)));

            Customer cust2 =
                    new Customer("Noriaki", "Kasei", "nkasai", "japan", "nori@gmx.jp");
            //			cust2.setBillingAddress(new Address("4020", "Linz", "Landstraße 50"));
            //			cust2.addShippingAddress(new Address("1000", "Wien", "Haudumgasse 87a"));
            //			cust2.addShippingAddress(new Address("5000", "Salzburg", "Moritzwinkel 5"));
            //			cust2.addPhone(new Phone("mobile", "+43", "(0) 222 111"));
            //			cust2.addPhone("mobile", new Phone("+43", "(0) 222 111"));

            Long cust1Id = custAdmin.saveCustomer(cust1);
            @SuppressWarnings("unused")
            Long cust2Id = custAdmin.saveCustomer(cust2);

            System.out.println("--------------- findAllCustomers ---------------");
            for (Customer c : custAdmin.findAllCustomers()) {
                System.out.println(c);

                //        if (! c.getPhones().isEmpty()) {
                //          System.out.println("  phone numbers:");
                //          for (Phone phone : c.getPhones())
                //            System.out.println("     " + phone);
                //          for (Entry<String, Phone> entry : c.getPhones().entrySet())
                //            System.out.println("     " + entry.getKey() + ": " + entry.getValue());
                //        }
                //        if (! c.getShippingAddresses().isEmpty()) {
                //          System.out.println("  shipping addresses:");
                //          for (Address a : c.getShippingAddresses())
                //            System.out.println("     " + a);
                //        }
                //        if (! c.getPaymentData().isEmpty()) {
                //          System.out.println("  payment data:");
                //          for (PaymentData pd : c.getPaymentData())
                //            System.out.println("     " + pd);
                //        }
            }

            System.out.println("--------------- findCustomerById ---------------");
            System.out.println(custAdmin.findCustomerById(cust1Id));
        } catch (IdNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws NamingException {
        custAdmin = JndiUtil.getRemoteObject(CUSTOMER_ADMIN_REMOTE_NAME);

        testCustomerAdmin();
    }
}
