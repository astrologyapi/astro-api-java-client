// App.java
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode; // from Jackson
// If not in a package, no 'package' declaration is needed.

public class MatchMaking {
    public static void main(String[] args) {
        // Example usage for match making:
        String userID = System.getenv("ASTROLOGY_API_USER_ID");
        String apiKey = System.getenv("ASTROLOGY_API_KEY");
        AstrologyAPIClient client = new AstrologyAPIClient(userID, apiKey);


        Map<String, Object> data = new HashMap<>();
        data.put("m_day", 8);
        data.put("m_month", 12);
        data.put("m_year", 2023);
        data.put("m_hour", 5);
        data.put("m_min", 30);
        data.put("m_lat", 12.34);
        data.put("m_lon", 56.78);
        data.put("m_tzone", 5.5);
      
        data.put("f_day", 8);
        data.put("f_month", 12);
        data.put("f_year", 2023);
        data.put("f_hour", 5);
        data.put("f_min", 30);
        data.put("f_lat", 12.34);
        data.put("f_lon", 56.78);        
        data.put("f_tzone", 5.5);
        

        JsonNode response = client.customCall(
            
        "match_ashtakoot_points",
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
