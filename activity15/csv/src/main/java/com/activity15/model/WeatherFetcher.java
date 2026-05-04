package com.activity15.model;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class WeatherFetcher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 2. User Input
        System.out.print("Enter Latitude: ");
        double lat = scanner.nextDouble();
        System.out.print("Enter Longitude: ");
        double lon = scanner.nextDouble();

        // 3. Build the Request
        String urlString = "https://api.open-meteo.com/v1/forecast?latitude=" + lat + "&longitude=" + lon + "&current=temperature_2m";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .GET()
                .build();

        // 4. Send and Receive & 5. Error Handling
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // Check status code
            int statusCode = response.statusCode();
            System.out.println("\nStatus Code: " + statusCode);
            if (statusCode == 200) {
                // Success: Display raw JSON
                System.out.println("\n--- Raw JSON Data ---");
                System.out.println(response.body());
            } else {
                // Error: Display message
                System.out.println("\nError! Request failed with status code: " + statusCode);
                System.out.println("Something went wrong. Please check your input or try again later.");
            }

        } catch (IOException | InterruptedException e) {
            // Handle network errors
            System.out.println("\nError connecting to the server: " + e.getMessage());
            System.out.println("Please check your internet connection.");
        }
        scanner.close();
    }
}

