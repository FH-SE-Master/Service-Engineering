package sve2.fhbay.interfaces;

import sve2.fhbay.domain.Article;

import javax.ejb.Local;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/21/17
 */
@Local
public interface AuctionLocal extends Auction {

    void addFinishAuctionTimer(Article article);
}
