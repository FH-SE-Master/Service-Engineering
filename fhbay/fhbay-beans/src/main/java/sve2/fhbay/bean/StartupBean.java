package sve2.fhbay.bean;

import org.slf4j.Logger;
import sve2.fhbay.dao.CustomerDao;
import sve2.fhbay.domain.Article;
import sve2.fhbay.domain.Customer;
import sve2.fhbay.interfaces.ArticleAdminLocal;
import sve2.fhbay.interfaces.AuctionLocal;
import sve2.fhbay.util.DateUtil;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import java.util.Date;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 05/03/17
 */
@Startup
@Singleton
public class StartupBean {

    @EJB
    private CustomerDao customerDao;
    @EJB
    private ArticleAdminLocal articleAdmin;
    @EJB
    private AuctionLocal auctionBean;

    private Logger log;

    @PostConstruct
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void onStartup() throws Throwable {
        Customer cust1 = new Customer("Armin", "Kogler", "akogler", "kogi", "akogler@gmx.at");
        Customer cust2 = new Customer("Noriaki", "Kasei", "nkasai", "japan", "nori@gmx.jp");
        Customer cust3 = new Customer("Heinz", "Dobler", "dobi", "dobi", "dobler.heinz@gmx.jp");
        Customer cust4 = new Customer("Johann", "Heinzelreiter", "dobi", "dobi", "dobler.heinz@gmx.jp");

        customerDao.persist(cust1);
        customerDao.persist(cust2);
        customerDao.persist(cust3);
        customerDao.persist(cust4);


        final Date now = DateUtil.now();

        Article art1 = new Article("DDR2 ECC", "Neuwertiger Speicherbaustein", 100.0, now, DateUtil.addSeconds(now, 5));
        Article art2 = new Article("Samsung T166", "Samsung Spinpoint T166, 500GB, SATA", 100.0, now, DateUtil.addSeconds(now, 5));
        Article art3 = new Article("OCZ Agility 2 T166", "SSD mit neuartiger Flash-Technologie", 100.0, now, DateUtil.addSeconds(now, 5));

        articleAdmin.offerArticle(art1, cust1.getId());
        articleAdmin.offerArticle(art2, cust2.getId());
        articleAdmin.offerArticle(art3, cust3.getId());

        // Bids article one
        auctionBean.placeBid(art1.getId(), cust4.getId(), 100.0, DateUtil.addSeconds(now, 2));
        auctionBean.placeBid(art1.getId(), cust3.getId(), 101.0, DateUtil.addSeconds(now, 3));
        auctionBean.placeBid(art1.getId(), cust2.getId(), 200.0, DateUtil.addSeconds(now, 6));
        auctionBean.placeBid(art1.getId(), cust1.getId(), 300.0, DateUtil.addSeconds(now, 6));

        // Bids article two
        auctionBean.placeBid(art2.getId(), cust4.getId(), 100.0, DateUtil.addSeconds(now, 2));
        auctionBean.placeBid(art2.getId(), cust3.getId(), 101.0, DateUtil.addSeconds(now, 3));
        auctionBean.placeBid(art2.getId(), cust2.getId(), 200.0, DateUtil.addSeconds(now, 4));
        auctionBean.placeBid(art2.getId(), cust1.getId(), 300.0, DateUtil.addSeconds(now, 6));

        // Bids article two
        auctionBean.placeBid(art3.getId(), cust4.getId(), 100.0, DateUtil.addSeconds(now, 2));
        auctionBean.placeBid(art3.getId(), cust3.getId(), 101.0, DateUtil.addSeconds(now, 3));
        auctionBean.placeBid(art3.getId(), cust2.getId(), 200.0, DateUtil.addSeconds(now, 4));
        auctionBean.placeBid(art3.getId(), cust1.getId(), 300.0, DateUtil.addSeconds(now, 3));
    }
}
