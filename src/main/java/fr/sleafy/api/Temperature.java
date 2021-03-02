package fr.sleafy.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Temperature {

    @JsonProperty
    private int id;

    private int espId;

    @JsonProperty
    @NotNull
    private String espUUID;

    @JsonProperty
    @NotNull
    private float value;

    @JsonProperty
    private Date time;

    public Temperature(float value) {
        this.value = value;
    }
}
