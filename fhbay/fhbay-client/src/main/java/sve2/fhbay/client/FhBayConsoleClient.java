package sve2.fhbay.client;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQMapMessage;
import sve2.fhbay.domain.Article;
import sve2.fhbay.domain.Customer;
import sve2.fhbay.exceptions.IdNotFoundException;
import sve2.fhbay.interfaces.ArticleAdminRemote;
import sve2.fhbay.interfaces.CustomerAdmin;
import sve2.fhbay.util.DateUtil;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.NamingException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 03/10/17
 */
public class FhBayConsoleClient {

    private static CustomerAdmin custAdmin;
    private static final String CUSTOMER_ADMIN_REMOTE_NAME = "fhbay/fhbay-beans/CustomerAdminRemoteBean!sve2.fhbay.interfaces.CustomerAdminRemote";
    private static final String ARTICLE_ADMIN_REMOTE_NAME = "/fhbay/fhbay-beans/ArticleAdminBean!sve2.fhbay.interfaces.ArticleAdminRemote";
    private static final String QUEUE_REMOTE_NAME = "/jms/queue/FhBayQueue";
    private static final String QUEUE_CONNECTION_FACTORY_REMOTE_NAME = "/jms/RemoteConnectionFactory";

    private static void testCustomerAdmin() {
        try {
            System.out.println("--------------- save customer ---------------");
            System.out.println("--------------- save customer ---------------");
            Customer cust1 = new Customer("Armin", "Kogler", "akogler", "kogi", "akogler@gmx.at");

            Customer cust2 =
                    new Customer("Noriaki", "Kasei", "nkasai", "japan", "nori@gmx.jp");

            Long cust1Id = custAdmin.saveCustomer(cust1);
            @SuppressWarnings("unused")
            Long cust2Id = custAdmin.saveCustomer(cust2);

            System.out.println("--------------- findAllCustomers ---------------");
            for (Customer c : custAdmin.findAllCustomers()) {
                System.out.println(c);
            }

            System.out.println("--------------- findCustomerById ---------------");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private static ArticleAdminRemote articleAdmin;

    private static void testArticleAdmin() throws NamingException {
        Customer[] customers = custAdmin.findAllCustomers().toArray(new Customer[]{});
        if (customers.length <= 1)
            return;
        Long cust1Id = customers[0].getId();
        Long cust2Id = customers[1].getId();

        System.out.println("------------- saveArticle ----------------------");

        try {
            Date now = DateUtil.now();
            Article art1 =
                    new Article("DDR2 ECC", "Neuwertiger Speicherbaustein", 100.0, now,
                                DateUtil.addSeconds(now, 3));
            Long art1Id = articleAdmin.offerArticle(art1, cust1Id);

            Article art2 =
                    new Article("Samsung T166", "Samsung Spinpoint T166, 500GB, SATA", 150.99, now,
                                DateUtil.addSeconds(now, 4));
            articleAdmin.offerArticle(art2, cust2Id);

            Article art3 =
                    new Article("OCZ Agility 2 T166", "SSD mit neuartiger Flash-Technologie", 768.99,
                                now, DateUtil.addSeconds(now, 5));
            articleAdmin.offerArticle(art3, cust2Id);

            System.out.println("------------- findArticleById ------------------");
            System.out.printf("art1=%s\n", articleAdmin.findArticleById(art1Id));

            System.out.println("------------- findAllMatchingArticles ----------");
            System.out.println("Articles matching \"neu\"");
            List<Article> matchingArticles =
                    articleAdmin.findAllMatchingArticles(null, "neu", true);
            if (matchingArticles != null && !matchingArticles.isEmpty())
                for (Article art : matchingArticles)
                    System.out.printf("%s - %s%n", art, art.getDescription());
            else
                System.out.println("No matching artilces found.");
        } catch (IdNotFoundException e) {
            e.printStackTrace();
        }
    }


    private static void articleToMessage(MapMessage msg,
                                         Article article)
            throws JMSException {
        msg.setString("name", article.getName());
        msg.setString("description", article.getDescription());
        msg.setDouble("initialPrice", article.getInitialPrice());
        msg.setLong("startDate", article.getStartDate().getTime());
        msg.setLong("endDate", article.getStartDate().getTime());
    }

    // add user (ApplicationRealm):
    //	 username=jboss,
    //	 password=fhbay-mdb1,
    //	 group=guest
    private static void testArticleProcessor() throws NamingException {
        Customer[] customers = custAdmin.findAllCustomers().toArray(new Customer[]{});
        if (customers.length == 0)
            return;
        Long sellerId = customers[0].getId();

        String username = JndiUtil.getProperty(Context.SECURITY_PRINCIPAL);
        String password = JndiUtil.getProperty(Context.SECURITY_CREDENTIALS);

        Queue queue = JndiUtil.getRemoteObject(QUEUE_REMOTE_NAME);
        ConnectionFactory factory = JndiUtil.getRemoteObject(QUEUE_CONNECTION_FACTORY_REMOTE_NAME);
        // Hack for checking if host is properly set on a localhost environment which it isn't when wildfly is hosted in a docker container
        if (ActiveMQConnectionFactory.class.isAssignableFrom(factory.getClass())) {
            final Map<String, Object> params = ((ActiveMQConnectionFactory) factory).getServerLocator().getStaticTransportConfigurations()[0].getParams();
            if (!"localhost".equalsIgnoreCase((String)params.get("host"))) {
                ((ActiveMQConnectionFactory) factory).getServerLocator().getStaticTransportConfigurations()[0].getParams().put("host", "localhost");
            }
        }

        try (JMSContext jmsCtx = factory.createContext(username, password)) {
            JMSProducer producer = jmsCtx.createProducer();

            MapMessage articleMsg = jmsCtx.createMapMessage();
            articleMsg.setLongProperty("sellerId", sellerId);

            for (int i = 0; i < 3; i++) {
                Article newArticle = new Article("name_" + i,
                                                 "description_" + i,
                                                 1.0 + i,
                                                 Calendar.getInstance().getTime(),
                                                 Calendar.getInstance().getTime());
                articleToMessage(articleMsg, newArticle);

                producer.send(queue, articleMsg);
                System.out.println(String.format("Message [%s] sent", articleMsg.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws NamingException {
        custAdmin = JndiUtil.getRemoteObject(CUSTOMER_ADMIN_REMOTE_NAME);
        articleAdmin = JndiUtil.getRemoteObject(ARTICLE_ADMIN_REMOTE_NAME);

        testCustomerAdmin();
        testArticleProcessor();
        testArticleAdmin();
    }
}
