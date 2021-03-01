package fr.sleafy.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
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
