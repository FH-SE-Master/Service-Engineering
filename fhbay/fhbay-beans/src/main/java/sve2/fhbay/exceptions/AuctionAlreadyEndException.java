package sve2.fhbay.exceptions;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 05/01/17
 */
public class AuctionAlreadyEndException extends RuntimeException {

    public AuctionAlreadyEndException() {
    }

    public AuctionAlreadyEndException(String message) {
        super(message);
    }

    public AuctionAlreadyEndException(String message,
                                      Throwable cause) {
        super(message, cause);
    }

    public AuctionAlreadyEndException(Throwable cause) {
        super(cause);
    }

    public AuctionAlreadyEndException(String message,
                                      Throwable cause,
                                      boolean enableSuppression,
                                      boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
