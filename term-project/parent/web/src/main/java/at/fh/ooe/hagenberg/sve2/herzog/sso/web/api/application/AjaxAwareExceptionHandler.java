package at.fh.ooe.hagenberg.sve2.herzog.sso.web.api.application;

import org.omnifaces.exceptionhandler.FullAjaxExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.FacesException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Iterator;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 03/15/17
 */
public class AjaxAwareExceptionHandler extends FullAjaxExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(AjaxAwareExceptionHandler.class);
    private static final String ERROR_LOCATION = "/error/500.xhtml";
    private static final String OLD_VIEW_ID = "OLD_VIEW_ID";

    public AjaxAwareExceptionHandler(ExceptionHandler wrapped) {
        super(wrapped);
    }

    @Override
    public void handle() throws FacesException {
        final FacesContext context = FacesContext.getCurrentInstance();
        // Let omnifaces deal with exceptions occurred on ajax requests
        if (context == null || context.getPartialViewContext().isAjaxRequest()) {
            super.handle();
            return;
        }

        // Exceptions on GET request, we will deal with it
        final Iterator<ExceptionQueuedEvent> unhandledExceptionQueuedEvents = getUnhandledExceptionQueuedEvents().iterator();

        // There's no unhandled exception.
        if (unhandledExceptionQueuedEvents.hasNext()) {
            ExceptionQueuedEventContext exceptionContext = (ExceptionQueuedEventContext) unhandledExceptionQueuedEvents.next().getSource();
            final String viewId = (context.getViewRoot() != null) ? context.getViewRoot().getViewId() : "unknown";
            logException(context, exceptionContext.getException(), ERROR_LOCATION, "Error on GET request for view: " + viewId);

            try {
                final HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
                context.getExternalContext().redirect(request.getContextPath() + ERROR_LOCATION);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void logException(FacesContext context,
                                Throwable exception,
                                String location,
                                String message,
                                Object... parameters) {
        LOG.error(String.format(message, location), exception);
    }
}