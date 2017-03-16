package at.fh.ooe.hagenberg.sve2.herzog.sso.web.api.application.producer;

import at.fh.ooe.hagenberg.sve2.herzog.sso.web.api.application.model.ApplicationInfo;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import java.time.LocalDateTime;

import static at.fh.ooe.hagenberg.sve2.herzog.sso.web.api.application.listener.ServletContextInitializedListener.*;

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
                return APP_NAME;
            }

            @Override
            public String getApplicationVersion() {
                return APP_VERSION;
            }

            @Override
            public String getServerName() {
                return SERVER_NAME;
            }

            @Override
            public String getRestApiVersion() {
                return REST_API_VERSION;
            }

            @Override
            public String getInfoUri() {
                return INFO_URI;
            }

            @Override
            public LocalDateTime getAliveSince() {
                return STARTED_AT;
            }
        };
    }
}
