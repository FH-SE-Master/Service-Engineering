package sve2.fhbay.bean.dao;

import sve2.fhbay.dao.BidDao;
import sve2.fhbay.domain.Bid;
import sve2.fhbay.util.DateUtil;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 05/01/17
 */
@Stateless
public class BidDaoBean extends BaseDaoBean<Bid, Long> implements BidDao {

    @Override
    public Bid findBidByCustomerAndArticleId(Long articleId,
                                             Long customerId) {
        Objects.requireNonNull(articleId, "Article id must not be null");
        Objects.requireNonNull(customerId, "Customer id must not be null");

        try {
            return getEm().createQuery("SELECT bid FROM Bid bid where bid.buyer.id = :customerId and bid.article.id = :articleId", Bid.class)
                          .setParameter("articleId", articleId)
                          .setParameter("customerId", customerId)
                          .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Bid> findBidsByAndArticleIdAndEndDate(Long articleId, Date endDate) {
        Objects.requireNonNull(articleId, "Article id must not be null");
        Objects.requireNonNull(endDate, "EndDate must not be null");

        return getEm().createQuery("SELECT bid FROM Bid bid where bid.article.id = :articleId and bid.toDate = :endDate", Bid.class)
                      .setParameter("articleId", articleId)
                      .setParameter("endDate", endDate)
                      .getResultList();
    }

    @Override
    public Map<Double, Long> findBidStatisticsByArticleId(Long articleId) {
        Objects.requireNonNull(articleId, "Article id must not be null");

        final List<Bid> bids = getEm().createQuery("SELECT bid FROM Bid bid where bid.article.id = :articleId and article.startDate <= :date and article.endDate >= :date", Bid.class)
                                      .setParameter("articleId", articleId)
                                      .setParameter("date", DateUtil.now())
                                      .getResultList();

        final Double maxPrice = bids.stream().map(Bid::getBid).max(Double::compare).orElse(0.0);
        final Map<Double, Long> result = new HashMap<>();
        result.put(maxPrice, (long) bids.size());
        return result;
    }
}
