package sve2.fhbay.bean.dao;

import sve2.fhbay.dao.ArticleDao;
import sve2.fhbay.domain.Article;
import sve2.fhbay.domain.Article_;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/21/17
 */
@Stateless
public class ArticleDaoBean extends BaseDaoBean<Article, Long> implements ArticleDao {

    @Override
    public List<Article> findByPatternAndCategory(String pattern,
                                                  Long categoryId) {
        // Version 1: JPQL
        //        final String queryStr = "SELECT a FROM Article a WHERE lower(a.name) like lower(:pattern) OR lower(a.description) like lower(:pattern)";
        //
        //        return getEm().createQuery(queryStr, Article.class).setParameter("pattern", "%" + pattern + "%").getResultList();

        // Version 2: Crtieria API
        final CriteriaBuilder cb = getEm().getCriteriaBuilder();
        final CriteriaQuery<Article> cq = cb.createQuery(Article.class);

        Root<Article> root = cq.from(Article.class);
        ParameterExpression<String> pe = cb.parameter(String.class);

        final CriteriaQuery<Article> query = cq.select(root)
                                               .where(cb.or(cb.like(cb.lower(root.get(Article_.name)), pe),
                                                            cb.like(cb.lower(root.get(Article_.description)), pe)));

        return getEm().createQuery(query).setParameter(pe, "%" + pattern + "%").getResultList();
    }
}
