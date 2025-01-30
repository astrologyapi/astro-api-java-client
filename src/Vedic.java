// App.java
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode; // from Jackson
// If not in a package, no 'package' declaration is needed.

public class Vedic {
    public static void main(String[] args) {
        // Example usage for numerology:
        String userID = System.getenv("ASTROLOGY_API_USER_ID");
        String apiKey = System.getenv("ASTROLOGY_API_KEY");
        AstrologyAPIClient client = new AstrologyAPIClient(userID, apiKey);


        Map<String, Object> data = new HashMap<>();
        data.put("name", "John Doe");
        data.put("day", 8);
        data.put("month", 12);
        data.put("year", 2023);
        data.put("hour", 5);
        data.put("min", 30);
        data.put("lat", 12.34);
        data.put("lon", 56.78);
        data.put("tzone", 5.5);
      

        JsonNode response = client.customCall(
            
        "birth_details",
            data,
            "json"
        );

        if (response != null) {
            System.out.println("Response:\n" + response.toPrettyString());
        } else {
            System.err.println("Failed to retrieve data.");
        }
    
    }
}
