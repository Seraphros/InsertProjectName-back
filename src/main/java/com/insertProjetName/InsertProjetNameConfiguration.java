package com.insertProjetName;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.insertProjetName.api.utils.Database;
import com.insertProjetName.api.Maintener;
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
