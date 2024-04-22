package org.example.CreateEntityJira;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.CreateEntityJira.Fields.Fields;

public class JiraMain {

    public static void main(String[] args) {
        CreateEntityJira createEntityJira = new CreateEntityJira();

        // Accessing the fields member variable
        Fields fields = createEntityJira.getFields();

        // Print the CreateEntityJira object
        System.out.println("CreateEntityJira object with default values:");
        System.out.println(fields);
    }


}