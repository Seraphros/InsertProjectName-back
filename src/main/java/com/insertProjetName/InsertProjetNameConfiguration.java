package com.insertProjetName;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.insertProjetName.api.Maintener;
import io.dropwizard.Configuration;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;

@Getter
@Setter
public class InsertProjetNameConfiguration extends Configuration {

    @Valid
    @JsonProperty
    private Maintener maintenedBy;

}
