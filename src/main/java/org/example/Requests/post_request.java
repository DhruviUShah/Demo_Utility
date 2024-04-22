package org.example.Requests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class post_request {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static String[] getAuthorizationDetails(String systemName) {
        String credsPath = "src/main/java/properties/systems/configurations/"+systemName+".json";
        try {
            File credsFile = new File(credsPath);
            JsonNode credsNode = objectMapper.readTree(credsFile);
            String authType = credsNode.get("authType").asText();

            String username = credsNode.get("username").asText();
            String passwordOrToken = credsNode.get("password").asText();
//            System.out.println(username);
//            System.out.println(passwordOrToken);
            String postURL = credsNode.get("postURL").asText();
            return new String[]{authType, username, passwordOrToken, postURL};
        } catch (IOException e) {
            e.printStackTrace();
            return new String[]{"", "", "", ""};
        }
    }

    public static void postReq(String inputJson, String systemName) throws Exception {
        try {
            HttpClient client = HttpClients.createDefault();
            String[] authDetails = getAuthorizationDetails(systemName);
            String postURL = authDetails[3];
            HttpPost post = new HttpPost(postURL);
            String authType = authDetails[0];
            String username = authDetails[1];
            String passwordOrToken = authDetails[2];

            // Setting the headers
            post.setHeader("Authorization", getAuthorizationHeader(authType, username, passwordOrToken, systemName));
            if (systemName.equals("ADO")) {
                post.setHeader("Content-Type", "application/json-patch+json");
            } else if (systemName.equals("Jira")) {
                post.setHeader("Content-Type", "application/json");
            }


            // Set request body
            StringEntity stringEntity = new StringEntity(inputJson, ContentType.APPLICATION_JSON);
            post.setEntity(stringEntity);
            // Execute HTTP Post request
            HttpResponse response = client.execute(post);
            // Print response
            System.out.println("----------------------------------------------------");
            if (response.getStatusLine().getStatusCode() == 201) {
                System.out.println("Entity created successfully");
                String responseBody = EntityUtils.toString(response.getEntity());
                System.out.println("Response Body:");
                System.out.println(responseBody + "\n");
                System.out.println(response.getEntity().getContent() + "\n");
            } else {
                System.out.println("Status: \n" + response.getStatusLine().getStatusCode());
                String responseBody = EntityUtils.toString(response.getEntity());
                System.out.println("Response Body:");
                System.out.println(responseBody + "\n");
            }
        } catch (Exception e) {
            System.out.println("Not created successfully\n");
            e.printStackTrace();
        }
    }

    private static String getAuthorizationHeader(String authType, String username, String passwordOrToken, String systemName) {
        if (authType.equalsIgnoreCase("basic")) {
            return getBasicAuthorizationHeader(username, passwordOrToken);
        } else if (authType.equalsIgnoreCase("bearer")) {
            return getBearerAuthorizationHeader(passwordOrToken);
        } else {
            throw new IllegalArgumentException("Unsupported authentication type: " + authType);
        }
    }

    private static String getBasicAuthorizationHeader(String username, String password) {
        String loginCreds = username + ":" + password;
        String base64Credentials = Base64.getEncoder().encodeToString(loginCreds.getBytes());
        return "Basic " + base64Credentials;
    }

    private static String getBearerAuthorizationHeader(String token) {
        return "Bearer " + token;
    }
}

