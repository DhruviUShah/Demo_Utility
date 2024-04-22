package org.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class activate_integrations {
    public static void main(String[] args) {
        try {
            // URL for the POST request
            String url = "http://localhost:8989/OpsHubWS/rest/api/1.0/integrations/activate";

            // JSON data to send
            String jsonInputString = "{ \"integrations\": [ { \"entityPairs\": [ { \"id\": 4, \"settings\": { \"backward\": { \"id\": 8 }, \"forward\": { \"id\": 7 } } } ], \"id\": 4 } ] }";

            // Create URL object
            URL obj = new URL(url);

            // Open connection
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // Set the request method
            con.setRequestMethod("POST");

            // Set basic authentication header
            String username = "admin";
            String password = "password";
            String userCredentials = username + ":" + password;
            String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));
            con.setRequestProperty("Authorization", basicAuth);

            // Set the request header
            con.setRequestProperty("Content-Type", "application/json");

            // Enable output and set it
            con.setDoOutput(true);
            try (DataOutputStream outputStream = new DataOutputStream(con.getOutputStream())) {
                outputStream.write(jsonInputString.getBytes());
            }

            // Get the response code
            int responseCode = con.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Read the response
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                // Print the response
                System.out.println("Response: " + response.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
