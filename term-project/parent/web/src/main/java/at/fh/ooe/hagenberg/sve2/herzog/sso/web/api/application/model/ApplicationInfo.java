package at.fh.ooe.hagenberg.sve2.herzog.sso.web.api.application.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Calendar;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 03/15/17
 */
public interface ApplicationInfo extends Serializable {

    String getApplicationName();

    String getRestApiVersion();

    String getServerName();

    String getInfoUri();

    LocalDateTime getAliveSince();
}
