package sve2.fhbay.exceptions;

import javax.ejb.ApplicationException;
import java.io.Serializable;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/07/17
 */
@ApplicationException(rollback = true)
public class IdNotFoundException extends Exception implements Serializable {

    public IdNotFoundException() {
    }

    public IdNotFoundException(String type,
                               Serializable id) {
        super(String.format("Type %sa with id=%s not found", type, id));
    }

    public IdNotFoundException(String message,
                               Throwable cause) {
        super(message, cause);
    }

    public IdNotFoundException(Throwable cause) {
        super(cause);
    }

    public IdNotFoundException(String message,
                               Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
