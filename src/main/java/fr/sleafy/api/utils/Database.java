package fr.sleafy.api.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class Database {

    @JsonProperty
    @NotNull
    private String url;

    @JsonProperty
    @NotNull
    private String user;

    @JsonProperty
    @NotNull
    private String password;
}
