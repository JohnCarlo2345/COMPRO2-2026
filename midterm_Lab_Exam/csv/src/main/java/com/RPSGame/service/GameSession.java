package com.RPSGame.service;

public class GameSession {
    private final Player p1;
    private final Player p2;
    private int roundsPlayed;

    public GameSession(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.roundsPlayed = 0;
    }

    public String playRound() {
        roundsPlayed++;
        GameMove m1 = p1.getCurrentMove();
        GameMove m2 = p2.getCurrentMove();

        int result = m1.compare(m2);
        String outcome;

        switch (result) {
            case 1 -> {
                p1.incrementScore();
                outcome = p1.getName() + " WINS this round!";
            }
            case -1 -> {
                p2.incrementScore();
                outcome = p2.getName() + " WINS this round!";
            }
            default -> outcome = "It's a TIE!";
        }

        return outcome + " | SCORE: " + p1.getName() + " " + p1.getScore() + " - " + p2.getScore() + " " + p2.getName();
    }

    public boolean isGameOver() {
        return roundsPlayed >= 10;
    }

    public String getFinalResult() {
        if (p1.getScore() > p2.getScore()) return "GAME OVER! " + p1.getName() + " WINS THE GAME!";
        else if (p2.getScore() > p1.getScore()) return "GAME OVER! " + p2.getName() + " WINS THE GAME!";
        else return "GAME OVER! IT'S A DRAW!";
    }
}

