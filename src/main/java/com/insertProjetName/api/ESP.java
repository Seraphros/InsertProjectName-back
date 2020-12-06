package com.insertProjetName.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ESP {

    @JsonProperty
    private int id;

    @JsonProperty
    private String uuid;

    @JsonProperty
    private int userId;

    @JsonProperty
    private String name;

    public ESP(String uuid, int userId) {
        this.uuid = uuid;
        this.userId = userId;
    }

    public ESP(int id, String uuid, int userId) {
        this.id = id;
        this.uuid = uuid;
        this.userId = userId;
    }

    public ESP(int id, String uuid, int userId, String name) {
        this.id = id;
        this.uuid = uuid;
        this.userId = userId;
        this.name = name;
    }
}
