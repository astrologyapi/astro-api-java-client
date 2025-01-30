// App.java
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode; // from Jackson
// If not in a package, no 'package' declaration is needed.

public class Horoscope {
    public static void main(String[] args) {
        // Example usage for monthly horoscope:
        String userID = System.getenv("ASTROLOGY_API_USER_ID");
        String apiKey = System.getenv("ASTROLOGY_API_KEY");
        AstrologyAPIClient client = new AstrologyAPIClient(userID, apiKey);

        String zodiacName = "taurus";

        Map<String, Object> data = new HashMap<>();
        data.put("timezone", 5.5);

        JsonNode response = client.customCall(
            "horoscope_prediction/monthly/"+zodiacName,
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
