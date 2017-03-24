package at.fh.ooe.hagenberg.sve2.herzog.sso.web.api.application;

import at.fh.ooe.hagenberg.sve2.herzog.sso.service.rest.api.model.ApplicationInfo;
import at.fh.ooe.hagenberg.sve2.herzog.sso.web.api.application.model.NavigationRules;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.ProjectStage;
import javax.inject.Named;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 03/15/17
 */
@ApplicationScoped
public class ApplicationProducer {

    @Produces
    @ApplicationScoped
    @Named("global_app_info")
    public ApplicationInfo produceApplicationInfo() {
        return new ApplicationInfo() {
            @Override
            public String getApplicationName() {
                return ServletContextInitializedListener.APP_NAME;
            }

            @Override
            public String getApplicationVersion() {
                return ServletContextInitializedListener.APP_VERSION;
            }

            @Override
            public String getServerName() {
                return ServletContextInitializedListener.SERVER_NAME;
            }

            @Override
            public String getRestApiVersion() {
                return ServletContextInitializedListener.REST_API_VERSION;
            }

            @Override
            public String getInfoUri() {
                return ServletContextInitializedListener.INFO_URI;
            }

            @Override
            public LocalDateTime getAliveSince() {
                return ServletContextInitializedListener.STARTED_AT;
            }

            @Override
            public boolean isDevelopment() {
                return ProjectStage.Development.equals(ServletContextInitializedListener.PROJECT_STAGE);
            }

            @Override
            public Date getAliveSinceAsDate() {
                ZonedDateTime zdt = getAliveSince().atZone(ZoneId.systemDefault());
                return Date.from(zdt.toInstant());
            }
        };
    }

    @Produces
    @ApplicationScoped
    @Named("global_nav_rules")
    public NavigationRules produceNavigationRules() {
        return new NavigationRules() {
            @Override
            public String getToLanding() {
                return "to-landing";
            }
        };
    }
}
