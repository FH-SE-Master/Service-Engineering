package at.fh.ooe.hagenberg.sve2.herzog.sso.web.api.application.listener;

import at.fh.ooe.hagenberg.sve2.herzog.sso.service.rest.api.RestApiInfo;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.time.LocalDateTime;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 03/15/17
 */
@WebListener("servletContextInitializedListener")
public class ServletContextInitializedListener implements ServletContextListener {

    public static String APP_NAME;
    public static String REST_API_VERSION;
    public static String SERVER_NAME;
    public static String INFO_URI;
    public static LocalDateTime STARTED_AT;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        STARTED_AT = LocalDateTime.now();
        APP_NAME = sce.getServletContext().getInitParameter("APP_NAME");
        SERVER_NAME = sce.getServletContext().getInitParameter("SERVER_NAME");
        INFO_URI = sce.getServletContext().getInitParameter("INFO_URI");
        REST_API_VERSION = RestApiInfo.API_VERSION;
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
