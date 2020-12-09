package fr.sleafy.services;

import de.ahus1.keycloak.dropwizard.KeycloakConfiguration;
import fr.sleafy.dao.ESPDao;
import fr.sleafy.security.DropwizardBearerTokenFilterImpl;
import io.dropwizard.setup.Environment;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;

public class SecurityService {

    DropwizardBearerTokenFilterImpl filter;

    public SecurityService(KeycloakConfiguration configuration, ESPDao espDao, Environment environment) {
        KeycloakDeployment keycloakDeployment = KeycloakDeploymentBuilder.build(configuration);
        filter = new DropwizardBearerTokenFilterImpl(keycloakDeployment, espDao);
        this.initiateURLSecurity();
        environment.jersey().register(filter);

    }

    private void initiateURLSecurity() {
        this.initiateAuthorizedURL();
        this.initiateBasicAuthURL();
    }

    private void initiateAuthorizedURL() {
        filter.addUrlToAuthorizedAuth("maintener");
        filter.addUrlToAuthorizedAuth("swagger");
    }

    private void initiateBasicAuthURL() {
        filter.addUrlToBasicAuth("esp/informations");
    }
}
