package com.insertProjetName.api.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class Database {

    @JsonProperty
    @NotEmpty
    private String url;

    @JsonProperty
    @NotEmpty
    private String user;

    @JsonProperty
    @NotEmpty
    private String password;
}
