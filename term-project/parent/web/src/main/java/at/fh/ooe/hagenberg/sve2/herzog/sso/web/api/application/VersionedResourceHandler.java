package at.fh.ooe.hagenberg.sve2.herzog.sso.web.api.application;

import at.fh.ooe.hagenberg.sve2.herzog.sso.web.api.application.model.ApplicationInfo;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.omnifaces.resourcehandler.DefaultResourceHandler;
import org.omnifaces.resourcehandler.RemappedResource;

import javax.faces.application.ProjectStage;
import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 03/16/17
 */
public class VersionedResourceHandler extends DefaultResourceHandler {

    private final ApplicationInfo info;
    private final boolean developmentMode;

    private final List<String> APP_LIBRARIES = Arrays.asList("css", "js");

    public VersionedResourceHandler(ResourceHandler wrapped) {
        super(wrapped);
        developmentMode = FacesContext.getCurrentInstance().isProjectStage(ProjectStage.Development);
        info = BeanProvider.getContextualReference(ApplicationInfo.class);
    }

    @Override
    public Resource decorateResource(Resource resource) {
        if (!APP_LIBRARIES.contains(resource.getLibraryName())) {
            return resource;
        }

        final String requestPath;
        if (developmentMode) {
            requestPath = resource.getRequestPath() + "&v=" + info.getAliveSince().format(DateTimeFormatter.ISO_DATE_TIME);
        } else {
            requestPath = resource.getRequestPath() + "&v=" + info.getApplicationVersion();
        }

        return new RemappedResource(resource, requestPath);

    }

    @Override
    public void handleResourceRequest(FacesContext context) throws IOException {
        super.handleResourceRequest(context);
    }
}
