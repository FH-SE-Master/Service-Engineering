package at.fh.ooe.hagenberg.sve2.herzog.sso.service.rest.api.model.json;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 03/15/17
 */
@JsonTypeInfo(
        include = JsonTypeInfo.As.PROPERTY,
        use = JsonTypeInfo.Id.NAME,
        property = "@jsonType")
@JsonInclude(
        content = JsonInclude.Include.NON_NULL,
        value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(
        ignoreUnknown = true)
public class AbstractJsonModel {

    protected Map<String, Object> unknownProperties = new HashMap<String, Object>();

    @JsonIgnore
    public Map<String, Object> getUnkownProperties() {
        return unknownProperties;
    }

    @JsonAnySetter
    public void setUnknownProperties(String key,
                                     Object value) {
        unknownProperties.put(key, value);
    }
}
