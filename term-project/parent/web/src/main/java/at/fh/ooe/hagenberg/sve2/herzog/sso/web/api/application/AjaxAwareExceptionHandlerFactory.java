package at.fh.ooe.hagenberg.sve2.herzog.sso.web.api.application;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 03/15/17
 */
public class AjaxAwareExceptionHandlerFactory extends ExceptionHandlerFactory {

    private ExceptionHandlerFactory parent;

    public AjaxAwareExceptionHandlerFactory(ExceptionHandlerFactory parent) {
        this.parent = parent;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        ExceptionHandler result = parent.getExceptionHandler();
        result = new AjaxAwareExceptionHandler(result);
        return result;
    }

}
