// App.java
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode; // from Jackson
// If not in a package, no 'package' declaration is needed.

public class App {
    public static void main(String[] args) {
        // Example usage:
        String userID = System.getenv("ASTROLOGY_API_USER_ID");
        String apiKey = System.getenv("ASTROLOGY_API_KEY");
        AstrologyAPIClient client = new AstrologyAPIClient(userID, apiKey);

        String zodiacName = "aries";

        Map<String, Object> data = new HashMap<>();
        data.put("timezone", 5.5);

        JsonNode response = client.customCall(
            "sun_sign_prediction/daily/"+zodiacName,
            data,
            "json"
        );

        if (response != null) {
            System.out.println("Response:\n" + response.toPrettyString());
        } else {
            System.err.println("Failed to retrieve data.");
        }

        // Example usage for PDF:
        Map<String, Object> pdfData = new HashMap<>();
        pdfData.put("name", "Your Name");
        pdfData.put("day", 8);
        pdfData.put("month", 12);
        pdfData.put("year", 2023);
        pdfData.put("hour", 5);
        pdfData.put("minute", 30);
        pdfData.put("latitude", 12.34);
        pdfData.put("longitude", 56.78);
        pdfData.put("timezone", 5.5);
        pdfData.put("language", "en");

        JsonNode pdfResponse = client.customCallPdf(
            "premium_kundli_report",
            pdfData
            
        );

        if (pdfResponse != null) {
            System.out.println("PDF Response:\n" + pdfResponse.toPrettyString());
        } else {
            System.err.println("Failed to retrieve PDF data.");
        }
    
    }
}
