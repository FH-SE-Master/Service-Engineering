package at.fh.ooe.hagenberg.sve2.herzog.sso.service.rest.api.model.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 03/15/17
 */
@JsonTypeName("aliveModel")
public class AliveModel extends AbstractJsonModel {

    @JsonProperty("applicationName")
    private final String applicationName;

    @JsonProperty("apiVersion")
    private final String apiVersion;

    @JsonProperty("applicationServer")
    private final String applicationServer;

    @JsonProperty("infoUrl")
    private final String infoUrl;

    @JsonProperty("aliveSince")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private final LocalDateTime aliveSince;

    @JsonCreator
    public AliveModel(String applicationName,
                      String apiVersion,
                      String applicationServer,
                      String infoUrl,
                      LocalDateTime aliveSince) {
        this.applicationName = applicationName;
        this.apiVersion = apiVersion;
        this.applicationServer = applicationServer;
        this.infoUrl = infoUrl;
        this.aliveSince = aliveSince;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public String getApplicationServer() {
        return applicationServer;
    }

    public String getInfoUrl() {
        return infoUrl;
    }

    public LocalDateTime getAliveSince() {
        return aliveSince;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AliveModel that = (AliveModel) o;
        return Objects.equals(applicationName, that.applicationName) &&
                Objects.equals(apiVersion, that.apiVersion) &&
                Objects.equals(applicationServer, that.applicationServer) &&
                Objects.equals(infoUrl, that.infoUrl) &&
                Objects.equals(aliveSince, that.aliveSince);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicationName, apiVersion, applicationServer, infoUrl, aliveSince);
    }
}
