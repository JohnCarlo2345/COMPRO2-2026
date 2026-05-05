package com.weather.app.model;

public class Wind {
    private String direction;
    private double speed;

    // Constructor to set values after manual parsing
    public Wind(String direction, double speed) {
        this.direction = direction;
        this.speed = speed;
    }

    // Getters
    public String getDirection() {
        return direction;
    }

    public double getSpeed() {
        return speed;
    }
}

