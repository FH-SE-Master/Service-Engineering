package sve2.fhbay.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/21/17
 */
@StaticMetamodel(Article.class)
public class Article_ {
    public static volatile SingularAttribute<Article, String> name;
    public static volatile SingularAttribute<Article, String> description;
}
