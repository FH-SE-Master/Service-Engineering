package sve2.fhbay.dao;

import sve2.fhbay.domain.Bid;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 05/01/17
 */
@Local
public interface BidDao extends BaseDao<Bid, Long> {

    Bid findBidByCustomerAndArticleId(Long articleId, Long customerId);

    List<Bid> findBidsByAndArticleIdAndEndDate(Long articleId, Date endDate);

    Map<Double, Long> findBidStatisticsByArticleId(Long articleId);
}
