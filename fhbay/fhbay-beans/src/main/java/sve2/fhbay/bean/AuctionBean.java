package sve2.fhbay.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sve2.fhbay.bean.dao.ArticleDaoBean;
import sve2.fhbay.dao.ArticleDao;
import sve2.fhbay.domain.Article;
import sve2.fhbay.exceptions.IdNotFoundException;
import sve2.fhbay.interfaces.AuctionLocal;
import sve2.fhbay.interfaces.AuctionRemote;

import javax.annotation.Resource;
import javax.ejb.*;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/21/17
 */
@Stateless
public class AuctionBean implements AuctionLocal, AuctionRemote {

    @Resource
    private TimerService timer;
    @EJB
    private ArticleDao articleDao;

    private static final Logger log = LoggerFactory.getLogger(AuctionBean.class);

    @Override
    public void addFinishAuctionTimer(Article article) {
        // Expires one time at given date
        timer.createTimer(article.getEndDate(), article.getId());

    }

    @Timeout
    public void timerFinishedAuctionListener(Timer timer) {
        final Long articleId = (Long) timer.getInfo();
        final Article article = articleDao.findById(articleId);

        if (article == null) {
            throw new EJBException(new IdNotFoundException(Article.class.getName(), articleId));
        }

        log.info("#timerFinishedAuctionListener called for article: {}", articleId);
    }
}
