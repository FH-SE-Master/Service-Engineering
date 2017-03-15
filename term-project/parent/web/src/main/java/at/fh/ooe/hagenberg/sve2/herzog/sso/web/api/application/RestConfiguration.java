package at.fh.ooe.hagenberg.sve2.herzog.sso.web.api.application;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 03/15/17
 */
@ApplicationPath("/rest")
public class RestConfiguration extends Application {
    public RestConfiguration() {
    }

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<>();
//        classes.add()

        return classes;
    }
}
