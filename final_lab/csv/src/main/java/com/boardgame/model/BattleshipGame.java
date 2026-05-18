package com.boardgame.model;

import com.boardgame.service.Board;
import com.boardgame.service.InvalidMoveException;

public class BattleshipGame {
    private Board p1Board;
    private Board p2Board;

    // Player 1 Stats
    private int p1ShipsSunk = 0;
    private int p1Hits = 0;
    private int p1Misses = 0;

    // Player 2 Stats
    private int p2ShipsSunk = 0;
    private int p2Hits = 0;
    private int p2Misses = 0;

    public BattleshipGame() {
        p1Board = new Board();
        p2Board = new Board();
        placeShipsFixed(p1Board);
        placeShipsFixed(p2Board);
    }

    // Fixed positions
    private void placeShipsFixed(Board board) {
        board.setCell(1, 1, 'S');
        board.setCell(2, 3, 'S');
        board.setCell(4, 5, 'S');
        board.setCell(6, 7, 'S');
        board.setCell(8, 2, 'S');
    }

    public boolean processMove(GameMove move, Board targetBoard, boolean isPlayer1Turn) throws InvalidMoveException {
        if (!move.isValid(targetBoard)) {
            throw new InvalidMoveException("Invalid move at (" + move.getX() + "," + move.getY() + ")");
        }
        move.execute(targetBoard);
        boolean hit = (targetBoard.getCell(move.getX(), move.getY()) == 'X');

        // Update Stats
        if (isPlayer1Turn) {
            if (hit) {
                p1Hits++;
                p1ShipsSunk++;
            } else {
                p1Misses++;
            }
        } else {
            if (hit) {
                p2Hits++;
                p2ShipsSunk++;
            } else {
                p2Misses++;
            }
        }
        return hit;
    }

    public boolean isGameOver() {
        return p1Board.getRemainingShips() == 0 || p2Board.getRemainingShips() == 0;
    }

    // LEADERBOARD: Ships Sunk + Hits + Misses
    public String getLeaderboard() {
        return "===== LEADERBOARD =====" + "\n" +
               "PLAYER 1" + "\n" +
               "Ships Sunk: " + p1ShipsSunk + "\n" +
               "Hits: " + p1Hits + "\n" +
               "Misses: " + p1Misses + "\n" +
               "-----------------------" + "\n" +
               "OPPONENT" + "\n" +
               "Ships Sunk: " + p2ShipsSunk + "\n" +
               "Hits: " + p2Hits + "\n" +
               "Misses: " + p2Misses + "\n" +
               "=======================";
    }

    public Board getP1Board() {
        return p1Board;
    }

    public Board getP2Board() {
        return p2Board;
    }
}

