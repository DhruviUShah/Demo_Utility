package org.example.CreateEntityJira.Fields;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Getter
@Setter

public class Description {
    public String type = "doc"; // Default value for type
    public int version = 1; // Default value for version
    public ArrayList<InnerContent> content = new ArrayList<>(); // Default value for content

}