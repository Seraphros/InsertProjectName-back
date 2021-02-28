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
    private Integer id;

    @JsonProperty
    private String uuid;

    @JsonIgnore
    private String secretKey;

    @JsonProperty
    private String user;

    @JsonProperty
    private String name;

    public ESP(String uuid, String user) {
        this.uuid = uuid;
        this.user = user;
    }

    public ESP(int id, String uuid, String user) {
        this.id = id;
        this.uuid = uuid;
        this.user = user;
    }

    public ESP(int id, String uuid, String user, String name) {
        this.id = id;
        this.uuid = uuid;
        this.user = user;
        this.name = name;
    }

    public ESP(String uuid, String user, String secretKey) {
        this.uuid = uuid;
        this.user = user;
        this.secretKey = secretKey;
    }


}
