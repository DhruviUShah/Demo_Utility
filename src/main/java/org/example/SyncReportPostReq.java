package org.example;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;


public class SyncReportPostReq {

    public static void main(String[] args) throws Exception {
        String url = "http://localhost:8989/OpsHubWS/queryNode/user/basicLogin";
        String basicAuthCredentials = "admin:password";
        String encodedCredentials = Base64.getEncoder().encodeToString(basicAuthCredentials.getBytes(StandardCharsets.UTF_8));

        // Build the POST request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("User-Agent", "PostmanRuntime/7.37.3")
                .header("Accept", "*/*")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Authorization", "Basic " + encodedCredentials)
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        // Create an HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Send the request and get the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Print the response code and body
        System.out.println("Response Code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());

        // Print all cookies obtained
        Map<String, List<String>> headers = response.headers().map();
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            if (entry.getKey() != null && entry.getKey().equalsIgnoreCase("Set-Cookie")) {
                List<String> cookies = entry.getValue();
                for (String cookie : cookies) {
                    System.out.println("Cookie: " + cookie);
                }
            }
        }
    }
}
