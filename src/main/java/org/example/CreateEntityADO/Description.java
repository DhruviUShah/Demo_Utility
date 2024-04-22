package org.example.CreateEntityADO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Description {
    private String op="add";
    private String path="fields/System.Description";
    private Object from;
    private String value;
}
