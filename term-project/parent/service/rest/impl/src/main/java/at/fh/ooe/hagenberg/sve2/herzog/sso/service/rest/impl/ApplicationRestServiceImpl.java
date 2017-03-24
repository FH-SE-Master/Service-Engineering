package at.fh.ooe.hagenberg.sve2.herzog.sso.service.rest.impl;

import at.fh.ooe.hagenberg.sve2.herzog.sso.service.rest.api.ApplicationRestService;
import at.fh.ooe.hagenberg.sve2.herzog.sso.service.rest.api.model.ApplicationInfo;
import at.fh.ooe.hagenberg.sve2.herzog.sso.service.rest.api.model.json.AliveModel;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

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
    @Context
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
