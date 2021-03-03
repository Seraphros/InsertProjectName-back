package fr.sleafy.security;

import fr.sleafy.api.ESP;
import fr.sleafy.api.utils.IDSecretKey;
import fr.sleafy.api.utils.Route;
import fr.sleafy.dao.ESPDao;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.AdapterDeploymentContext;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.NodesRegistrationManagement;
import org.keycloak.jaxrs.JaxrsBearerTokenFilterImpl;
import org.keycloak.jaxrs.JaxrsHttpFacade;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Slf4j
@PreMatching
@Priority(Priorities.AUTHENTICATION)
public class DropwizardBearerTokenFilterImpl extends JaxrsBearerTokenFilterImpl {

    public final List<Route> urlAuthorized;
    public final List<Route> urlBasic;
    private final ESPDao espDao;

    public DropwizardBearerTokenFilterImpl(KeycloakDeployment keycloakDeployment, ESPDao espDao) {
        deploymentContext = new AdapterDeploymentContext(keycloakDeployment);
        nodesRegistrationManagement = new NodesRegistrationManagement();

        urlAuthorized = new ArrayList<>();
        urlBasic = new ArrayList<>();

        this.espDao = espDao;
    }

    @Override
    public void filter(ContainerRequestContext request) throws IOException {
        SecurityContext securityContext = this.getRequestSecurityContext(request);
        JaxrsHttpFacade facade = new JaxrsHttpFacade(request, securityContext);
        boolean basicAuth = false;
        boolean authorized = false;

        if (request.getMethod().equals("OPTIONS")) {
            authorized = true;
        } else {
            for (Route route : urlBasic) {
                if (request.getUriInfo().getPath().startsWith(route.url) && request.getMethod().equals(route.method)) {
                    basicAuth = true;
                }
            }
            for (Route route : urlAuthorized) {
                if (request.getUriInfo().getPath().startsWith(route.url) && request.getMethod().equals(route.method)) {
                    authorized = true;
                }
            }
        }
        if (!authorized) {
            if (!this.handlePreauth(facade) && !basicAuth) {
                KeycloakDeployment resolvedDeployment = this.deploymentContext.resolveDeployment(facade);
                this.nodesRegistrationManagement.tryRegister(resolvedDeployment);
                this.bearerAuthentication(facade, request, resolvedDeployment);
            } else {
                if (!this.validateBasicAuth(request)) {
                    facade.getResponse().setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
                    facade.getResponse().end();
                }
            }
        }
    }

    private boolean validateBasicAuth(ContainerRequestContext request) {
        String authorization = request.getHeaderString("Authorization");

        if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
            // Authorization: Basic base64credentials
            String base64Credentials = authorization.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            // credentials = username:password
            final String[] values = credentials.split(":", 2);

            ESP espFound = espDao.getESPfromUUID(values[0]);
            if (espFound == null) {
                return false;
            }
            String encodedKey = IDSecretKey.get_SHA_512_SecurePassword(values[1]);
            return encodedKey.equals(espFound.getSecretKey());
        }
        return false;
    }

    public void addUrlToBasicAuth(Route route) {
        this.urlBasic.add(route);
    }

    public void addUrlToAuthorizedAuth(Route route) {
        this.urlAuthorized.add(route);
    }
}