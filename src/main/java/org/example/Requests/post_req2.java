package org.example.Requests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class post_req2 {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void postReq(String postURL, String inputJson, String system, String authType) throws Exception {
        try {
            // Get credentials based on system
            String[] creds = getCredentials(system);
            String username = creds[0];
            String passwordOrToken = creds[1];

            HttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost(postURL);
            // Setting the headers
            post.setHeader("Authorization", getAuthorizationHeader(authType, username, passwordOrToken));
            if (system.equals("ADO")) {
                post.setHeader("Content-Type", "application/json-patch+json");
            } else if (system.equals("Jira")) {
                post.setHeader("Content-Type", "application/json");
            }

            // Set request body
            post.setEntity(new StringEntity(inputJson));
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
                System.out.println("Status: Error\n" + response.getStatusLine().getStatusCode());
                String responseBody = EntityUtils.toString(response.getEntity());
                System.out.println("Response Body:");
                System.out.println(responseBody + "\n");
            }
        } catch (Exception e) {
            System.out.println("Not created successfully\n");
            e.printStackTrace();
        }
    }

    private String getAuthorizationHeader(String authType, String username, String passwordOrToken) {
        if (authType.equalsIgnoreCase("basic")) {
            return getBasicAuthorizationHeader(username, passwordOrToken);
        } else if (authType.equalsIgnoreCase("bearer")) {
            return getBearerAuthorizationHeader(passwordOrToken);
        } else {
            throw new IllegalArgumentException("Unsupported authentication type: " + authType);
        }
    }

    private String getBasicAuthorizationHeader(String username, String password) {
        String loginCreds = username + ":" + password;
        String base64Credentials = Base64.getEncoder().encodeToString(loginCreds.getBytes());
        return "Basic " + base64Credentials;
    }

    private String getBearerAuthorizationHeader(String token) {
        return "Bearer " + token;
    }

    private String[] getCredentials(String systemName) throws IOException {
        String credsPath = "properties.systems.configurations/" + systemName + ".json";
        File credsFile = new File(credsPath);
        JsonNode credsNode = objectMapper.readTree(credsFile);
        String username = credsNode.get("username").asText();
        String passwordOrToken = credsNode.get("password").asText(); // Assuming for basic auth, adjust accordingly for bearer
        return new String[]{username, passwordOrToken};
    }
}

