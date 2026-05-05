package com.weather.app.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeatherService {
    private final HttpClient httpClient;
    private static final String BASE_URL = "http://www.7timer.info/bin/api.pl";

    public WeatherService() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public WeatherResponse getForecast(double lat, double lon) {
        String url = String.format("%s?lon=%.2f&lat=%.2f&product=civillight&output=json", BASE_URL, lon, lat);
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String jsonResponse = response.body();
                // Manual parsing begins here!
                return parseManualJson(jsonResponse); // Call our manual parser
            } else {
                System.err.println("Error: API returned status code " + response.statusCode());
                System.err.println("Response body: " + response.body());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Could not retrieve weather data: " + e.getMessage());
            return null;
        }
    }

    // --- MANUAL JSON PARSING METHOD ---
    private WeatherResponse parseManualJson(String jsonString) {
        String product = extractValue(jsonString, "\"product\":\"([^\"]+)\"");
        
        // Extract dataseries array content
        String dataseriesContent = extractValue(jsonString, "\"dataseries\":\\[([^\\]]+)\\]");
        
        List<Forecast> forecastList = new ArrayList<>();
        if (dataseriesContent != null) {
            // Split dataseries content into individual forecast objects
            Pattern forecastPattern = Pattern.compile("\\{[^{}]*?\\}"); // Matches an object within the array
            Matcher forecastMatcher = forecastPattern.matcher(dataseriesContent);

            while (forecastMatcher.find()) {
                String singleForecastJson = forecastMatcher.group();
                
                int timepoint = Integer.parseInt(extractValue(singleForecastJson, "\"timepoint\":(\\d+)"));
                double temp2m = Double.parseDouble(extractValue(singleForecastJson, "\"temp2m\":([\\d.-]+)"));
                
                String wind10mJson = extractValue(singleForecastJson, "\"wind10m\":(\\{[^}]+\\})");
                String windDirection = extractValue(wind10mJson, "\"direction\":\"([^\"]+)\"");
                double windSpeed = Double.parseDouble(extractValue(wind10mJson, "\"speed\":([\\d.-]+)"));
                
                Wind wind = new Wind(windDirection, windSpeed);
                forecastList.add(new Forecast(timepoint, temp2m, wind));
            }
        }

        return new WeatherResponse(product, forecastList);
    }

    // Helper method to extract a value using regex
    private String extractValue(String json, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}

