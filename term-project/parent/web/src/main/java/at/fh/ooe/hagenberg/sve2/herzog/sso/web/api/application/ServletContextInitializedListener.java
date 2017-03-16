package at.fh.ooe.hagenberg.sve2.herzog.sso.web.api.application;

import at.fh.ooe.hagenberg.sve2.herzog.sso.service.rest.api.RestApiInfo;

import javax.faces.application.ProjectStage;
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

    static String APP_NAME;
    static String APP_VERSION;
    static String REST_API_VERSION;
    static String SERVER_NAME;
    static String INFO_URI;
    static LocalDateTime STARTED_AT;
    static ProjectStage PROJECT_STAGE;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        STARTED_AT = LocalDateTime.now();
        APP_NAME = sce.getServletContext().getInitParameter("APP_NAME");
        APP_VERSION = sce.getServletContext().getInitParameter("APP_VERSION");
        SERVER_NAME = sce.getServletContext().getInitParameter("SERVER_NAME");
        INFO_URI = sce.getServletContext().getInitParameter("INFO_URI");
        PROJECT_STAGE = ProjectStage.valueOf(sce.getServletContext().getInitParameter("javax.faces.PROJECT_STAGE"));
        REST_API_VERSION = RestApiInfo.API_VERSION;
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Nothing to do
    }
}
