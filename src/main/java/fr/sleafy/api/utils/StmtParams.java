package fr.sleafy.api.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StmtParams {

    private int id;

    private String type;

    private String stringValue;

    private float floatValue;

    private int intValue;

    private boolean boolValue;

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

    public StmtParams(int id, float value) {
        this.id = id;
        this.type = "float";
        this.floatValue = value;
    }
    public StmtParams(int id, boolean value) {
        this.id = id;
        this.type = "boolean";
        this.boolValue = value;
    }
}
