package at.fh.ooe.hagenberg.sve2.herzog.sso.web.impl.pages.rest;

import org.apache.deltaspike.core.api.scope.ViewAccessScoped;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Typed;
import javax.inject.Named;
import java.io.Serializable;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 03/16/17
 */
@ViewAccessScoped
@Typed(RestPage.class)
@Named("restPage")
public class RestPage implements Serializable {

    @PostConstruct
    public void postConstruct() {
    }
}
