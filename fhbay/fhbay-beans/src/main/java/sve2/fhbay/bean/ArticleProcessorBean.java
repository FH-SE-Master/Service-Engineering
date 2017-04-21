package sve2.fhbay.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sve2.fhbay.domain.Article;
import sve2.fhbay.interfaces.ArticleAdminLocal;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/21/17
 */
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/FhBayQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class ArticleProcessorBean implements MessageListener {

    @EJB
    private ArticleAdminLocal articleAdmin;

    private static final Random random = new Random();
    private static final Logger log = LoggerFactory.getLogger(ArticleAdminBean.class);

    @Override
    public void onMessage(Message message) {

        final MapMessage msg = (MapMessage) message;
        try {
            final Article article = messageToArticle(msg);
            final Long sellerId = msg.getLongProperty("sellerId");

            log.info("Begin of processing of article [{}] started at '{}'", article.getName(), Calendar.getInstance().getTime().toString());
            Thread.sleep(1000 + random.nextInt(500));

            articleAdmin.offerArticle(article, sellerId);
            log.info("End of processing of article [{}] started at '{}'", article, Calendar.getInstance().getTime().toString());
        } catch (Exception e) {
            throw new EJBException();
        }
    }

    private Article messageToArticle(MapMessage msg)
            throws JMSException {

        Article article = new Article();
        article.setName(msg.getString("name"));
        article.setDescription(msg.getString("description"));
        article.setStartDate(new Date(msg.getLong("startDate")));
        article.setEndDate(new Date(msg.getLong("endDate")));
        article.setInitialPrice(msg.getDouble("initialPrice"));

        return article;
    }

}
