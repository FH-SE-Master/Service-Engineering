package sve2.fhbay.exceptions;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 05/01/17
 */
public class InvalidBidTimeRangeException extends RuntimeException {

    public InvalidBidTimeRangeException() {
    }

    public InvalidBidTimeRangeException(String message) {
        super(message);
    }

    public InvalidBidTimeRangeException(String message,
                                        Throwable cause) {
        super(message, cause);
    }

    public InvalidBidTimeRangeException(Throwable cause) {
        super(cause);
    }

    public InvalidBidTimeRangeException(String message,
                                        Throwable cause,
                                        boolean enableSuppression,
                                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
