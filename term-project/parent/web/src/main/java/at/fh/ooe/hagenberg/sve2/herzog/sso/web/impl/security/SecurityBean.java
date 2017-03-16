package at.fh.ooe.hagenberg.sve2.herzog.sso.web.impl.security;

import org.apache.deltaspike.core.api.common.DeltaSpike;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Typed;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 03/16/17
 */
@RequestScoped
@Typed(SecurityBean.class)
@Named("securityBean")
public class SecurityBean implements Serializable {

    @Inject
    @DeltaSpike
    private HttpServletRequest request;

    public void onLogout() {
        final HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        if (request.getUserPrincipal() != null) {
            try {
                request.logout();
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }
    }
}
