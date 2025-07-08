import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class WeatherAPIApplication {

    private static final String API_KEY = "YOUR_API_KEY"; // Replace with your actual API key
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s";

    public static void main(String[] args) {
        String city = "London"; // Default city
        if (args.length > 0) {
            city = args[0]; // Use command line argument if provided
        }
        
        try {
            String weatherData = fetchWeatherData(city);
            if (weatherData != null) {
                displayWeatherData(weatherData);
                System.out.println("\nCertificate of Completion will be issued on your internship end date.");
                System.out.println("C O D T E C H");
            }
        } catch (Exception e) {
            System.err.println("Error fetching weather data: " + e.getMessage());
        }
    }

    private static String fetchWeatherData(String city) throws Exception {
        String apiUrl = String.format(API_URL, city, API_KEY);
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        
        if (connection.getResponseCode() != 200) {
            throw new RuntimeException("Failed to fetch data: HTTP " + connection.getResponseCode());
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        connection.disconnect();
        
        return response.toString();
    }

    private static void displayWeatherData(String jsonResponse) {
        JSONObject json = new JSONObject(jsonResponse);
        
        String cityName = json.getString("name");
        JSONObject main = json.getJSONObject("main");
        double tempKelvin = main.getDouble("temp");
        double tempCelsius = tempKelvin - 273.15;
        int humidity = main.getInt("humidity");
        JSONObject weather = json.getJSONArray("weather").getJSONObject(0);
        String description = weather.getString("description");
        
        System.out.println("\n=== WEATHER REPORT ===");
        System.out.println("City: " + cityName);
        System.out.printf("Temperature: %.2f°C\n", tempCelsius);
        System.out.println("Humidity: " + humidity + "%");
        System.out.println("Conditions: " + description);
        System.out.println("=====================");
    }
}
