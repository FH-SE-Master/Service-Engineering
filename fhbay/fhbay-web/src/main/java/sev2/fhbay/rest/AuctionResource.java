package sev2.fhbay.rest;

import sve2.fhbay.domain.Bid;
import sve2.fhbay.exceptions.BidToLowException;
import sve2.fhbay.exceptions.IdNotFoundException;
import sve2.fhbay.exceptions.InvalidBidTimeRangeException;
import sve2.fhbay.interfaces.AuctionRemote;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/21/17
 */
@Path("/auction")
public class AuctionResource implements Serializable {

    @EJB
    private AuctionRemote auctionBean;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");

    @Path("placeBid")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response placeBid(@FormParam("articleId") Long articleId,
                             @FormParam("customerId") Long customerId,
                             @FormParam("bid") Double price,
                             @FormParam("toDate") String toDate) {
        try {
            final Bid bid = auctionBean.placeBid(articleId, customerId, price, DATE_FORMAT.parse(toDate));
            return Response.ok(bid).build();
        } catch (IdNotFoundException e) {
            return Response.serverError().entity("{\"error\": \"Article or customer with id not found\"}").build();
        } catch (BidToLowException be) {
            return Response.serverError().entity("{\"error\": \"Bid is to low\"}").build();
        } catch (InvalidBidTimeRangeException ibe) {
            return Response.serverError().entity("{\"error\": \"Bid time range is out of bounds\"}").build();
        } catch (ParseException pe) {
            return Response.serverError().entity("{\"error\": \"Date values are in invalid format\"}").build();
        } catch (Throwable e) {
            e.printStackTrace();
            return Response.serverError().entity(String.format("{\"error\": \"Unknown error occurred: %s\"}", e.getMessage())).build();
        }
    }

    @Path("stats")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response placeBid(@FormParam("articleId") Long articleId) {
        try {
            final Map<Double, Long> result = auctionBean.currentBidState(articleId);
            return Response.ok(result).build();
        } catch (Throwable e) {
            e.printStackTrace();
            return Response.serverError().entity(String.format("{\"error\": \"Unknown error occurred: %s\"}", e.getMessage())).build();
        }
    }
}
