package fr.sleafy;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.ahus1.keycloak.dropwizard.KeycloakConfiguration;
import fr.sleafy.api.utils.Database;
import fr.sleafy.api.Maintener;
import fr.sleafy.api.utils.OAuth2SecurityConfiguration;
import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SleafyBackConfiguration extends Configuration {

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;

    @Valid
    @JsonProperty
    private Maintener maintenedBy;

    @Valid
    @JsonProperty("database")
    private Database database;

    @Valid
    @JsonProperty("keycloakConfiguration")
    private KeycloakConfiguration keycloakConfiguration;

    @JsonProperty("oauth2Config")
    @Valid
    @NotNull
    private OAuth2SecurityConfiguration oAuth2SecurityConfiguration;
}
