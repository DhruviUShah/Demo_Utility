package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.CreateEntityADO.CreateEntityADO;
import org.example.CreateEntityJira.CreateEntityJira;
import org.example.CreateEntityJira.Fields.*;
import org.example.Requests.post_request;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a system: ADO/Jira:");

        String inputString = scanner.nextLine();

        try {
            // Checking for the systems
            if (inputString.equals("Jira")) {
                CreateEntityJira createEntityJira = new CreateEntityJira();
                Fields fields = new Fields();
                fields.setProject(new Project());
//               fields.setKey(new Project().getKey());
                fields.setSummary("Default summary from Jira");
                Description description = new Description();
//                description.setContent(new Content());
                fields.setDescription(description);
                fields.setIssuetype(new Issuetype());
                fields.setReporter(new Reporter());
                createEntityJira.setFields(fields);

                // Print the CreateEntityJira object
//                System.out.println("CreateEntityJira object with default values:");
//                System.out.println(fields);
//                System.out.println("CreateEntityJira object with default values:");
//                System.out.println("Project Key: " + createEntityJira.getFields().getProject().getKey());
//                System.out.println("Summary: " + createEntityJira.getFields().getSummary());
//                System.out.println("Description Type: " + createEntityJira.getFields().getDescription().getType());
//                System.out.println("Description Version: " + createEntityJira.getFields().getDescription().getVersion());
//                System.out.println("Description Content Type: " + createEntityJira.getFields().getDescription().getContent().getType());
//                System.out.println("Description Content Text: " + createEntityJira.getFields().getDescription().getContent().getText());
//                System.out.println("Issuetype Name: " + createEntityJira.getFields().getIssuetype().getName());
//                System.out.println("Reporter Name: " + createEntityJira.getFields().getReporter().getName());
                ObjectMapper objectMapper = new ObjectMapper();
                String inputJson = objectMapper.writeValueAsString(createEntityJira);
                System.out.println(inputJson);
              post_request.postReq(inputJson,inputString);
            } else if(inputString.equals("ADO")) {
                CreateEntityADO createEntityADO = new CreateEntityADO();
                ObjectMapper objectMapper = new ObjectMapper();
                String inputJsonADO = objectMapper.writeValueAsString(createEntityADO.getFields());
                System.out.println(inputJsonADO);
                post_request.postReq(inputJsonADO,inputString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
