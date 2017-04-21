package sve2.fhbay.bean;

import sve2.fhbay.dao.ArticleDao;
import sve2.fhbay.dao.CustomerDao;
import sve2.fhbay.domain.Article;
import sve2.fhbay.domain.Customer;
import sve2.fhbay.exceptions.IdNotFoundException;
import sve2.fhbay.interfaces.ArticleAdminLocal;
import sve2.fhbay.interfaces.ArticleAdminRemote;
import sve2.fhbay.interfaces.AuctionLocal;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/21/17
 */
@Stateless
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class ArticleAdminBean implements ArticleAdminLocal, ArticleAdminRemote {

    @EJB
    private ArticleDao articleDao;
    @EJB
    private CustomerDao customerDao;
    @EJB
    private AuctionLocal auctionBean;

    @Override
    public Article findArticleById(Long id) throws IdNotFoundException {
        final Article article = articleDao.findById(id);
        if (article == null) {
            throw new IdNotFoundException(Article.class.getName(), id);
        }

        return article;
    }

    @Override
    public List<Article> findAllMatchingArticles(Long categoryId,
                                                 String pattern,
                                                 boolean includeSubCategories) throws IdNotFoundException {
        // Category category = categoryDao.findById(categoryId);
        return articleDao.findByPatternAndCategory(pattern, null);
    }

    @Override
    public Long offerArticle(Article article,
                             Long sellerId) throws IdNotFoundException {
        final Customer customer = customerDao.findById(sellerId);
        if (customer == null) {
            throw new IdNotFoundException(Customer.class.getName(), sellerId);
        }

        article.setSeller(customer);
        articleDao.persist(article);

        auctionBean.addFinishAuctionTimer(article);

        return article.getId();
    }

    // Recursively search for categories
    // TODO: Find way to avoid recursion
    //    private Set<List> findAllMatchingArticles(/**Category cateogry*/,String pattern,  boolean includeSubCategories) {
    //
    //    }

    @WebMethod
    public Article[] getAllMatchingArticles(String pattern,
                                            Long categoryId) {
        final List<Article> articles = articleDao.findByPatternAndCategory(pattern, categoryId);
        return articles.toArray(new Article[articles.size()]);
    }
}
