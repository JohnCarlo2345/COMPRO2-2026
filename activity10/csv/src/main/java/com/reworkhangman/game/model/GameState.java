package com.reworkhangman.game.model;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private String targetWord;
    private List<Character> guessedLetters;
    private int remainingAttempts;
    private boolean gameWon;

    public GameState(String targetWord) {
        this.targetWord = targetWord.toUpperCase();
        this.guessedLetters = new ArrayList<>();
        this.remainingAttempts = 6; // Standard hangman attempts
        this.gameWon = false;
    }

    // Getters and helper methods
    public String getTargetWord() { return targetWord; }
    public int getRemainingAttempts() { return remainingAttempts; }
    public boolean isGameWon() { return gameWon; }
    public boolean isGameOver() { return remainingAttempts <= 0 || gameWon; }

    public void decrementAttempts() { if (remainingAttempts > 0) remainingAttempts--; }
    public void addGuessedLetter(char c) { guessedLetters.add(Character.toUpperCase(c)); }
    public boolean hasGuessedLetter(char c) { return guessedLetters.contains(Character.toUpperCase(c)); }

    // Update game state after guess
    public boolean checkGuess(char c) {
        c = Character.toUpperCase(c);
        if (hasGuessedLetter(c)) return false;
        addGuessedLetter(c);

        if (targetWord.contains(String.valueOf(c))) {
            // Check if all letters are guessed
            gameWon = targetWord.chars().allMatch(ch -> guessedLetters.contains((char) ch));
            return true;
        } else {
            decrementAttempts();
            return false;
        }
    }


