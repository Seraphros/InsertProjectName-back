package fr.sleafy;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.sleafy.api.utils.Database;
import fr.sleafy.api.Maintener;
import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;

@Getter
@Setter
public class InsertProjetNameConfiguration extends Configuration {

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;

    @Valid
    @JsonProperty
    private Maintener maintenedBy;

    @Valid
    @JsonProperty("database")
    private Database database;

}
