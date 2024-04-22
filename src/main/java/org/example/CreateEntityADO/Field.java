package org.example.CreateEntityADO;

public class Field {
    private String op = "add";
    private String path;
    private Object from;
    private String value;

    public Field(String path, String value) {
        this.path = path;
        this.value = value;
    }
}
