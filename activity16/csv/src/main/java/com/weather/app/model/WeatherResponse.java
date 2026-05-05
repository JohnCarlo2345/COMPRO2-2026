package com.weather.app.model;

import java.util.List;

public class WeatherResponse {
    private String product;
    private List<Forecast> dataseries;

    // Constructor to set values after manual parsing
    public WeatherResponse(String product, List<Forecast> dataseries) {
        this.product = product;
        this.dataseries = dataseries;
    }

    // Getters
    public String getProduct() {
        return product;
    }

    public List<Forecast> getDataseries() {
        return dataseries;
    }
}

