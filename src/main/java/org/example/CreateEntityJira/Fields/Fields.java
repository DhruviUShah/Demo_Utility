package org.example.CreateEntityJira.Fields;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Fields {
    public Project project = new Project(); // Default value for project
    public String summary = "Code Epic"; // Default value for summary
    public Description description = new Description(); // Default value for description
    public Issuetype issuetype = new Issuetype(); // Default value for issuetype
    public Reporter reporter = new Reporter(); // Default value for reporter


    // Default constructor
    public Fields() {
        // Initializing the Project, Description, Issuetype, and Reporter objects with default values
        project = new Project();
        description = new Description();
        issuetype = new Issuetype();
        reporter = new Reporter();
    }
}
