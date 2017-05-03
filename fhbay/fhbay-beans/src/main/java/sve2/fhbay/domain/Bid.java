package sve2.fhbay.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 05/01/17
 */
@Entity
@Table(name = "BID")
@NoArgsConstructor
@ToString(doNotUseGetters = true)
public class Bid implements Comparable<Bid>, Serializable {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Long id;

    @Column(nullable = false)
    @Getter
    @Setter
    private Double bid;

    @Column(nullable = false)
    @Getter
    @Setter
    private Boolean win = Boolean.FALSE;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date fromDate;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date toDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BUYER_CUSTOMER_ID")
    @Getter
    @Setter
    private Customer buyer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ARTICLE_ID")
    @Getter
    @Setter
    private Article article;

    public Bid(Double bid,
               Date fromDate,
               Date toDate,
               Customer buyer,
               Article article) {
        this.bid = bid;
        this.win = win;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.buyer = buyer;
        this.article = article;
    }

    @Override
    public int compareTo(Bid o) {
        int result;
        if ((result = fromDate.compareTo(o.getFromDate())) == 0) {
            if ((result = toDate.compareTo(o.getToDate())) == 0) {
                result = id.compareTo(o.getId());
            }
        }
        return result;
    }
}
