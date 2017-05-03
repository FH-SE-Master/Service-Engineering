package sve2.fhbay.interfaces;

import sve2.fhbay.domain.Bid;
import sve2.fhbay.exceptions.BidToLowException;
import sve2.fhbay.exceptions.IdNotFoundException;
import sve2.fhbay.exceptions.InvalidBidTimeRangeException;

import javax.ejb.Remote;
import java.util.Date;
import java.util.Map;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/21/17
 */
@Remote
public interface AuctionRemote extends Auction {    Bid placeBid(Long articleId,
                                                                 Long customerId,
                                                                 Double price,
                                                                 Date toDate) throws IdNotFoundException, InvalidBidTimeRangeException, BidToLowException;

    Map<Double, Long> currentBidState(Long articleId);
}
