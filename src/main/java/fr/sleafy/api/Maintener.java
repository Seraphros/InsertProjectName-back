package fr.sleafy.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class Maintener {

    @NotNull
    @JsonProperty
    private String mail;

    @NotNull
    @JsonProperty
    private String firstName;

    @NotNull
    @JsonProperty
    private String lastName;

    @NotNull
    @JsonProperty
    private String pseudo;
}
