package sve2.fhbay.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@Table(name = "ARTICLE")
@NoArgsConstructor
@ToString(doNotUseGetters = true, exclude = {"bids"})
public class Article implements Serializable, Comparable<Article> {

    private static final long serialVersionUID = -1L;

    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Long id;

    @Column(nullable = false)
    @Getter
    @Setter
    private String name;

    @Column(nullable = false)
    @Getter
    @Setter
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SELLER_CUSTOMER_ID")
    @Getter
    @Setter
    private Customer seller;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BUYER_CUSTOMER_ID")
    @Getter
    @Setter
    private Customer buyer;

    @Column(nullable = false)
    @Getter
    @Setter
    private Double initialPrice;

    @Column
    @Getter
    @Setter
    private Double finalPrice;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date startDate;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date endDate;

    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private ArticleState state = ArticleState.OFFERED;

//    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
//    @OrderBy("fromDate, toDate, id ASC")
//    @Getter
//    @Setter
//    private SortedSet<Bid> bids = new TreeSet<>();

    public Article(String name,
                   String description,
                   Double initialPrice,
                   Date startDate,
                   Date endDate) {
        this.name = name;
        this.description = description;
        this.initialPrice = initialPrice;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public int compareTo(Article otherArticle) {
        int result;
        if ((result = getStartDate().compareTo(otherArticle.getStartDate())) == 0) {
            result = this.getId().compareTo(otherArticle.getId());
        }

        return result;
    }
}
