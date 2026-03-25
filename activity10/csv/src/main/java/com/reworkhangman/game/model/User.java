package com.reworkhangman.game.model;

public class User {
    private String username;
    private String password;
    private int score;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.score = 0;
    }

    // Getters and Setters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
}


