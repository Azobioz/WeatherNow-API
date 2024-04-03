import java.math.BigDecimal;
import java.net.*;
import java.net.http.*;
import java.io.*;
import java.util.Scanner;

import org.json.*;

public class WeatherAPI {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException, JSONException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a city name");

        String city = scanner.next();
        scanner.close();

        String API_KEY = "";
        URI uri = new URI(String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric",
                city, API_KEY));

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        client.close();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        client.close();

        String json = response.body();

        JSONObject obj = new JSONObject(json);                      //org.json library
        try {
            JSONArray weatherArray = obj.getJSONArray("weather");
            JSONObject mainObj = obj.getJSONObject("main");
            for (int i = 0; i < weatherArray.length(); i++) {
                JSONObject weatherObj = weatherArray.getJSONObject(i);
                String main = weatherObj.getString("main");
                String description = weatherObj.getString("description");
                System.out.println("Weather in " + city + ": " + main);
                System.out.println("Description: " + description);
            }
            BigDecimal temp = mainObj.getBigDecimal("temp");
            System.out.println("Temperature: " + temp);
        }
        catch (Exception ex){
            System.out.println("There is no such city name");
            System.out.println("Error: " + ex);
        }


    }
}
