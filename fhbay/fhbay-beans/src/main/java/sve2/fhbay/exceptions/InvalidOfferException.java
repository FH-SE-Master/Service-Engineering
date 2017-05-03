package sve2.fhbay.exceptions;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 05/03/17
 */
public class InvalidOfferException extends RuntimeException {

    public InvalidOfferException() {
    }

    public InvalidOfferException(String message) {
        super(message);
    }

    public InvalidOfferException(String message,
                                 Throwable cause) {
        super(message, cause);
    }

    public InvalidOfferException(Throwable cause) {
        super(cause);
    }

    public InvalidOfferException(String message,
                                 Throwable cause,
                                 boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
