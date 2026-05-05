package com.weather.app.model;

public class Forecast {
    private int timepoint;
    private double temp2m;
    private Wind wind10m;

    // Constructor to set values after manual parsing
    public Forecast(int timepoint, double temp2m, Wind wind10m) {
        this.timepoint = timepoint;
        this.temp2m = temp2m;
        this.wind10m = wind10m;
    }

    // Getters
    public int getTimepoint() {
        return timepoint;
    }

    public double getTemp2m() { // Access using the raw JSON field name
        return temp2m;
    }

    public Wind getWind10m() { // Access using the raw JSON field name
        return wind10m;
    }
}


