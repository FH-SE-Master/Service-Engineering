package sve2.fhbay.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sve2.fhbay.dao.ArticleDao;
import sve2.fhbay.dao.BidDao;
import sve2.fhbay.dao.CustomerDao;
import sve2.fhbay.domain.Article;
import sve2.fhbay.domain.ArticleState;
import sve2.fhbay.domain.Bid;
import sve2.fhbay.domain.Customer;
import sve2.fhbay.exceptions.*;
import sve2.fhbay.interfaces.AuctionLocal;
import sve2.fhbay.interfaces.AuctionRemote;
import sve2.fhbay.util.DateUtil;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.ejb.Timer;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/21/17
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AuctionBean implements AuctionLocal, AuctionRemote {

    @Resource
    private TimerService timer;
    @EJB
    private ArticleDao articleDao;
    @EJB
    private CustomerDao customerDao;
    @EJB
    private BidDao bidDao;

    private static final Logger log = LoggerFactory.getLogger(AuctionBean.class);

    @Override
    public void addFinishAuctionTimer(Article article) {
        timer.getAllTimers().stream().filter(t -> article.getId().equals(t.getInfo())).forEach(Timer::cancel);
        // Expires one time at given date
        timer.createTimer(article.getEndDate(), article.getId());
    }

    @Override
    public void removeAuctionTimer(Long articleId) {
        Objects.requireNonNull(articleId, "Article id must not be null");
        timer.getTimers().stream().filter(timer -> articleId.equals(timer.getInfo())).forEach(Timer::cancel);
    }

    @Override
    public void onAuctionFinished(long articleId) {

    }

    @Override
    public Bid placeBid(Long articleId,
                        Long customerId,
                        Double price,
                        Date toDate) throws IdNotFoundException, InvalidBidTimeRangeException, BidToLowException {
        Objects.requireNonNull(articleId, "Article id must not be null");
        Objects.requireNonNull(customerId, "Customer id must not be null");
        Objects.requireNonNull(price, "Price must ot be null");
        Objects.requireNonNull(toDate, "Bid toDate must ot be null");

        final Date now = DateUtil.now();
        final Article article = articleDao.findById(articleId);

        if (article == null) {
            throw new IdNotFoundException(Article.class.getName(), articleId);
        }

        if (article.getEndDate().compareTo(now) <= 0) {
            throw new AuctionAlreadyEndException("Auction has already end");
        }

        if (toDate.compareTo(now) <= 0) {
            throw new InvalidBidTimeRangeException("ToDate must not be after now ");
        }

        final Customer customer = customerDao.findById(customerId);

        if (customer == null) {
            throw new IdNotFoundException(Customer.class.getName(), customerId);
        }

        if (article.getInitialPrice().compareTo(price) > 0) {
            throw new BidToLowException(String.format("Price must be at least %f", price));
        }

        Bid bid = bidDao.findBidByCustomerAndArticleId(articleId, customerId);

        if (bid == null) {
            bid = new Bid(price, now, toDate, customer, article);

            // Set end date to auction end date
            if (article.getEndDate().compareTo(toDate) <= 0) {
                bid.setToDate(article.getEndDate());
            }

            bidDao.persist(bid);
        } else {
            bid.setBid(price);
            bidDao.merge(bid);
        }

        return bidDao.findById(bid.getId());
    }

    @Override
    public Map<Double, Long> currentBidState(Long articleId) {
        Objects.requireNonNull(articleId, "Article id must not be null");

        return bidDao.findBidStatisticsByArticleId(articleId);
    }

    @Timeout
    public void timerFinishedAuctionListener(Timer timer) {
        final Long articleId = (Long) timer.getInfo();
        final Article article = articleDao.findById(articleId);

        if (article == null) {
            throw new EJBException(new IdNotFoundException(Article.class.getName(), articleId));
        }

        final List<Bid> bids = bidDao.findBidsByAndArticleIdAndEndDate(articleId, article.getEndDate());
        // Valid bids for article are present
        if (!bids.isEmpty()) {
            final Bid bid;
            // Only one bid has been made
            if (bids.size() == 1) {
                bid = bids.get(0);

                article.setFinalPrice(article.getInitialPrice());
                article.setBuyer(bid.getBuyer());

                bid.setWin(Boolean.TRUE);

                articleDao.merge(article);
                bidDao.merge(bid);
            }
            // Multiple bids has been made
            else {
                bids.sort(Comparator.comparing(Bid::getBid).reversed());
                bid = bids.get(0);
                Double price = bids.get(1).getBid() + 1;

                article.setState(ArticleState.SOLD);
                article.setFinalPrice(price);
                article.setBuyer(bid.getBuyer());

                bid.setWin(Boolean.TRUE);

                articleDao.merge(article);
                bidDao.merge(bid);
            }

            log.info("Article with id '{}' sold for '{}' to customer with id '{}'", articleId, article.getFinalPrice(), article.getBuyer().getId());
        }
        // no bid until end of auction present
        else {
            article.setState(ArticleState.UNSALEABLE);
            article.setFinalPrice(0.0);

            articleDao.merge(article);
            log.info("Article with id '{}' unsellable", articleId);
        }

        log.info("Auction finished for article with id '{}'", articleId);
    }
}
