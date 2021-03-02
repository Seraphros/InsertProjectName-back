package fr.sleafy.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ESP {

    @JsonProperty
    private int id;

    @JsonProperty
    private String uuid;

    @JsonIgnore
    private String secretKey;

    @JsonProperty
    private String user;

    @JsonProperty
    private String name;

    @JsonProperty
    private Boolean humiditySensor = false;

    @JsonProperty
    private Boolean heatSensor = false;

    @JsonProperty
    private int hygometrie = 0;

    @JsonProperty
    private Boolean watering = false;

    @JsonProperty
    private int wateringFrequency = 0;

    @JsonProperty
    private int wateringDuration = 10;

    @JsonProperty
    private int sleepTime = 60;


    public ESP(String uuid, String userId) {
        this.uuid = uuid;
        this.user = userId;
    }

    public ESP(int id, String uuid, String userId) {
        this.id = id;
        this.uuid = uuid;
        this.user = userId;
    }

    public ESP(int id, String uuid, String userId, String name) {
        this.id = id;
        this.uuid = uuid;
        this.user = userId;
        this.name = name;
    }

    public ESP(String uuid, String userId, String secretKey) {
        this.uuid = uuid;
        this.user = userId;
        this.secretKey = secretKey;
    }

    public ESP(String test) {

    }


}
