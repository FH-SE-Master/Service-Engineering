package at.fh.ooe.hagenberg.sve2.herzog.sso.service.rest.api.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 03/15/17
 */
public interface ApplicationInfo extends Serializable {

    String getApplicationName();

    String getApplicationVersion();

    String getRestApiVersion();

    String getServerName();

    String getInfoUri();

    LocalDateTime getAliveSince();

    Date getAliveSinceAsDate();

    boolean isDevelopment();
}
