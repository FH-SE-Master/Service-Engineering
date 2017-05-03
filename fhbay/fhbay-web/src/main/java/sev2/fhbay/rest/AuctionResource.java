package sev2.fhbay.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sve2.fhbay.domain.Article;
import sve2.fhbay.domain.Bid;
import sve2.fhbay.exceptions.BidToLowException;
import sve2.fhbay.exceptions.IdNotFoundException;
import sve2.fhbay.exceptions.InvalidBidTimeRangeException;
import sve2.fhbay.exceptions.InvalidOfferException;
import sve2.fhbay.interfaces.ArticleAdminLocal;
import sve2.fhbay.interfaces.AuctionRemote;

import javax.ejb.EJB;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/21/17
 */
@Path("/auction")
public class AuctionResource implements Serializable {

    @EJB
    private AuctionRemote auctionBean;
    @EJB
    private ArticleAdminLocal adminBean;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");

    @AllArgsConstructor
    public static final class ResponseModel {
        @Getter
        private final String message;
        @Getter
        private final String error;
    }

    @Path("placeBid")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response placeBid(@FormParam("articleId") @NotNull Long articleId,
                             @FormParam("customerId") @NotNull Long customerId,
                             @FormParam("bid") @NotNull Double price,
                             @FormParam("toDate") @NotNull String toDate) {
        try {
            final Bid bid = auctionBean.placeBid(articleId, customerId, price, DATE_FORMAT.parse(toDate));
            return Response.ok(new ResponseModel(String.format("Bid with id %d has been placed", bid.getId()), null)).build();
        } catch (IdNotFoundException e) {
            return Response.serverError().entity(new ResponseModel("Article or customer with id not found", e.getMessage())).build();
        } catch (BidToLowException be) {
            return Response.serverError().entity(new ResponseModel("Bid is to ow", be.getMessage())).build();
        } catch (InvalidBidTimeRangeException ibe) {
            return Response.serverError().entity(new ResponseModel("Bid end time is invalid", ibe.getMessage())).build();
        } catch (ParseException pe) {
            return Response.serverError().entity(new ResponseModel("Bid endTime has invalid format", pe.getMessage())).build();
        } catch (Throwable e) {
            e.printStackTrace();
            return Response.serverError().entity(new ResponseModel("Unknown error occurred", e.getMessage())).build();
        }
    }

    @Path("offer")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response offerArticle(@FormParam("name") @NotNull String name,
                                 @FormParam("description") @NotNull String description,
                                 @FormParam("price") @NotNull Double price,
                                 @FormParam("start") @NotNull String startDate,
                                 @FormParam("end") @NotNull String endDate,
                                 @FormParam("customerId") @NotNull Long customerId) {
        try {
            if (price <= 0) {
                throw new InvalidOfferException("Price must not be lower than 0");
            }
            final Date start = DATE_FORMAT.parse(startDate);
            final Date end = DATE_FORMAT.parse(endDate);
            if (start.compareTo(end) >= 0) {
                throw new InvalidOfferException("StartDate must not overflow endDate");
            }
            final Article article = new Article(name, description, price, start, end);
            final Long id = adminBean.offerArticle(article, customerId);
            return Response.ok(new ResponseModel(String.format("Article has been offered with id %d", id), null)).build();
        } catch (IdNotFoundException e) {
            return Response.serverError().entity(new ResponseModel("Article or customer with id not found", e.getMessage())).build();
        } catch (ParseException pe) {
            return Response.serverError().entity(new ResponseModel("Bid endTime has invalid format", pe.getMessage())).build();
        } catch (Throwable e) {
            return Response.serverError().entity(new ResponseModel("Unknown error occurred", e.getMessage())).build();
        }
    }

    @Path("stats")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response getStats(@FormParam("articleId") @NotNull Long articleId) {
        try {
            final Map<Double, Long> result = auctionBean.currentBidState(articleId);
            return Response.ok(result).build();
        } catch (Throwable e) {
            e.printStackTrace();
            return Response.serverError().entity(new ResponseModel("Unknown error occurred", e.getMessage())).build();
        }
    }

    @Path("articleInfo")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response getArticleInfo(@FormParam("articleId") @NotNull Long articleId) {
        try {
            final Article article = adminBean.findArticleById(articleId);
            return Response.ok(article).build();
        } catch (IdNotFoundException e) {
            return Response.serverError().entity(new ResponseModel("Article with id not found", e.getMessage())).build();
        } catch (Throwable e) {
            return Response.serverError().entity(new ResponseModel("Unknown error occurred", e.getMessage())).build();
        }
    }
}
