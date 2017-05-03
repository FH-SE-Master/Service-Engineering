package sve2.fhbay.exceptions;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 05/01/17
 */
public class BidToLowException extends RuntimeException {

    public BidToLowException() {
    }

    public BidToLowException(String message) {
        super(message);
    }

    public BidToLowException(String message,
                             Throwable cause) {
        super(message, cause);
    }

    public BidToLowException(Throwable cause) {
        super(cause);
    }

    public BidToLowException(String message,
                             Throwable cause,
                             boolean enableSuppression,
                             boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
