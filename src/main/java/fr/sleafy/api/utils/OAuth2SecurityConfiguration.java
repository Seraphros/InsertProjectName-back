package fr.sleafy.api.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OAuth2SecurityConfiguration {

    @JsonProperty
    @NotNull
    private String flow;

    @JsonProperty
    @NotNull
    private String tokenUrl;

    @JsonProperty
    @NotNull
    private String authorizationUrl;

    @JsonProperty
    @NotNull
    private String key;

}
