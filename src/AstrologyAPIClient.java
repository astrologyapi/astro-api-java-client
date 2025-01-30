

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A generic client to interact with the Astrology API using Base64-encoded
 * credentials and supporting JSON and PDF endpoints.
 */
public class AstrologyAPIClient {

    // Default base URLs
    private static final String BASE_URL_JSON = "https://json.astrologyapi.com/v1/";
    private static final String BASE_URL_PDF  = "https://pdf.astrologyapi.com/v1/";

    private final String userID;
    private final String apiKey;
    private final String encodedCredentials;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    /**
     * Constructor for the AstrologyAPIClient.
     *
     * @param userID the user ID from AstrologyAPI
     * @param apiKey the API key from AstrologyAPI
     */
    public AstrologyAPIClient(String userID, String apiKey) {
        this.userID = userID;
        this.apiKey = apiKey;

        // Combine and encode credentials for basic auth
        String credentials = userID + ":" + apiKey;
        this.encodedCredentials = Base64.getEncoder()
                                        .encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

        // Basic HttpClient (Java 11+)
        this.httpClient = HttpClient.newHttpClient();

        // ObjectMapper for JSON parsing
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Get the base URL depending on whether it's a PDF endpoint or JSON endpoint.
     *
     * @param apiType "pdf" for PDF base URL, otherwise JSON.
     * @return base URL string
     */
    private String getBaseURL(String apiType) {
        if ("pdf".equalsIgnoreCase(apiType)) {
            return BASE_URL_PDF;
        }
        return BASE_URL_JSON;
    }

    /**
     * Internal method to handle the HTTP POST request and parse the response.
     *
     * @param resource the API endpoint resource
     * @param data form data (key-value pairs)
     * @param apiType "pdf" or "json"
     * @return a JsonNode if successful, or null on error
     */
    private JsonNode getResponse(String resource, Map<String, Object> data, String apiType)
            throws IOException, InterruptedException {

        String endpoint = getBaseURL(apiType) + resource;

        // Build form data key1=value1&key2=value2
        StringBuilder formDataBuilder = new StringBuilder();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if (formDataBuilder.length() > 0) {
                formDataBuilder.append("&");
            }
            formDataBuilder.append(entry.getKey())
                           .append("=")
                           .append(entry.getValue().toString());
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Authorization", "Basic " + this.encodedCredentials)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(formDataBuilder.toString()))
                .build();

        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        // Check status
        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            return objectMapper.readTree(response.body());
        } else {
            System.err.println("HTTP Error: " + response.statusCode() + " - " + response.body());
            return null;
        }
    }

    /**
     * Custom call for flexible usage.
     *
     * @param resource the API endpoint, e.g., "sun_sign_prediction/daily/aries"
     * @param data the form data map
     * @param apiType "json" or "pdf"
     * @return a JsonNode or null on error
     */
    public JsonNode customCall(String resource, Map<String, Object> data, String apiType) {
        try {
            return getResponse(resource, data, apiType);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Example convenience method that defaults to JSON endpoints.
     *
     * @param resource the JSON endpoint
     * @param data the form data map
     * @return a JsonNode or null on error
     */
    public JsonNode customCallJson(String resource, Map<String, Object> data) {
        return customCall(resource, data, "json");
    }

    /**
     * Example convenience method that uses the PDF base URL.
     */
    public JsonNode customCallPdf(String resource, Map<String, Object> data) {
        return customCall(resource, data, "pdf");
    }

    // Additional specialized methods (call, dailyHoroCall, matchMakingCall, etc.) can be added similarly.
}
