package sev2.fhbay.rest;

import sve2.fhbay.domain.Article;
import sve2.fhbay.exceptions.IdNotFoundException;
import sve2.fhbay.interfaces.ArticleAdminLocal;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/21/17
 */
@Path("/articles")
public class ArticleResource implements Serializable {

    @EJB
    private ArticleAdminLocal bean;

    @Path("/find")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Article> findArticlesByPattern(@QueryParam("pattern") String pattern) {
        try {
            return bean.findAllMatchingArticles(null, pattern, false);
        } catch (IdNotFoundException e) {
            return Collections.emptyList();
        }
    }
}
