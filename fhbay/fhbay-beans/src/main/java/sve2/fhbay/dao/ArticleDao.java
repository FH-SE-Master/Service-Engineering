package sve2.fhbay.dao;

import sve2.fhbay.domain.Article;

import javax.ejb.Local;
import java.util.List;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/21/17
 */
@Local
public interface ArticleDao extends BaseDao<Article, Long> {

    List<Article> findByPatternAndCategory(String pattern, Long categoryId);
}
