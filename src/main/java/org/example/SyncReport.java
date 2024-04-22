import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SyncReport {

    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
        // URL with the encoded query string
        String url = "http://localhost:8989/OpsHubWS/queryNode/executeQuery?query=%7BintegrationReportList(fetchAllAccessibleIntegrationItems%20:%20false%2CsearchText%20%3A%20%22%22%2Cpager%20%3A%20%7B%20numberOfRecords%20%3A%200%2CpageSize%20%3A%2010%2CrecordNumber%20%3A%200%20%7D%2CfilterList%20%3A%20%5B%5D%2CsortBy%20%3A%20%5B%7BsortBy%20%3A%20%22opshub_update_time%22%2Corder%20%3A%20-1%7D%5D%2CsyncReportType%20%3A%20%22inSyncEntities%22%2CintegrationId%20%3A%201)%7BpagerInfo%7BpageSize%2CrecordNumber%2CnumberOfRecords%7D%2Clist%7BeventSyncId%2CsourceEntityInfoId%2CtargetEntityInfoId%2CsourceSystemId%2CdestinationSystemId%2CintegrationId%2ClastUpdated%2CsourceEntityId%2CdestinationEntityId%2CsourceEntityProject%2CdestinationEntityProject%2CsourceEntityType%2CdestinationEntityType%2CnoOfFailures%2CopshubUpdateTime%2CsourceEntitySyncState%2CtargetEntitySyncState%7D%2CsessionKey%7D%7D";

        // Create HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Set authentication token in the request header
        String authToken = "JSESSIONID=E37981C8C57AFC04FE5A3D914CD4A073; XSRF-TOKEN=b9417b1d-bc55-4d94-b793-04775a28ee88";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Cookie", authToken)
                .GET()
                .build();

        // Send the request and handle the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Check if the response was successful
        if (response.statusCode() == 200) {
            // Print response body
            System.out.println("Response Body:");
            System.out.println(response.body());
        } else {
            // Print error message along with status code
            System.out.println("Failed to get a successful response. Status code: " + response.statusCode());
        }
    }
}
