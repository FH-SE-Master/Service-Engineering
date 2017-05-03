package sve2.fhbay.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 03/10/17
 */
@Entity
@NoArgsConstructor
@ToString(doNotUseGetters = true, exclude = {"bids", "boughtArticles", "offeredArticles"})
public class Customer implements Serializable {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String firstname;

    @Column(nullable = false)
    @Getter
    @Setter
    private String lastname;

    @Column(nullable = false)
    @Getter
    @Setter
    private String username;

    @Column(nullable = false)
    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String email;

//    @OneToMany(mappedBy = "buyer", fetch = FetchType.LAZY)
//    @OrderBy("fromDate, toDate, id ASC")
//    @Getter
//    @Setter
//    private SortedSet<Bid> bids = new TreeSet<>();
//
//    @OneToMany(mappedBy = "buyer", fetch = FetchType.LAZY)
//    @OrderBy("startDate, id ASC")
//    @Getter
//    @Setter
//    private SortedSet<Article> boughtArticles = new TreeSet<>();
//
//    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY)
//    @OrderBy("startDate, id ASC")
//    @Getter
//    @Setter
//    private SortedSet<Article> offeredArticles = new TreeSet<>();

    public Customer(String firstname,
                    String lastname,
                    String username,
                    String password,
                    String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
