package com.insertProjetName.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class Maintener {

    @NotEmpty
    @JsonProperty
    private String mail;

    @NotEmpty
    @JsonProperty
    private String firstName;

    @NotEmpty
    @JsonProperty
    private String lastName;

    @NotEmpty
    @JsonProperty
    private String pseudo;
}
