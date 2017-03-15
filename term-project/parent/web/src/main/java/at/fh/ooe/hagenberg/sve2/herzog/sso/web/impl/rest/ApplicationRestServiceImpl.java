package at.fh.ooe.hagenberg.sve2.herzog.sso.web.impl.rest;

import at.fh.ooe.hagenberg.sve2.herzog.sso.service.rest.api.ApplicationRestService;
import at.fh.ooe.hagenberg.sve2.herzog.sso.service.rest.api.model.json.AliveModel;
import at.fh.ooe.hagenberg.sve2.herzog.sso.web.api.application.model.ApplicationInfo;
import org.apache.deltaspike.core.api.common.DeltaSpike;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 03/15/17
 */
@Path("application")
@RequestScoped
public class ApplicationRestServiceImpl implements ApplicationRestService {

    @Inject
    private ApplicationInfo appInfo;

    @Inject
    @DeltaSpike
    private HttpServletRequest request;

    @Override
    public AliveModel getAlive() {
        final String infoUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath() + "/" + appInfo.getInfoUri();

        return new AliveModel(appInfo.getApplicationName(),
                              appInfo.getRestApiVersion(),
                              appInfo.getServerName(),
                              infoUrl,
                              appInfo.getAliveSince());
    }
}
