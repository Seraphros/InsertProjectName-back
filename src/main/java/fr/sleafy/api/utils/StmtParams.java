package fr.sleafy.api.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StmtParams {

    private int id;

    private String type;

    private String stringValue;

    private int intValue;

    public StmtParams(int id, String value) {
        this.id = id;
        this.type = "string";
        this.stringValue = value;
    }

    public StmtParams(int id, int value) {
        this.id = id;
        this.type = "int";
        this.intValue = value;
    }
}
