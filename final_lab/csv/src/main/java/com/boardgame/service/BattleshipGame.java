package com.boardgame.service;

import java.util.Random;
import com.boardgame.model.Board;
import com.boardgame.model.GameMove;

public class BattleshipGame {
    private final Board p1Board;
    private final Board p2Board;

    public BattleshipGame() {
        p1Board = new Board();
        p2Board = new Board();
        placeShipsRandom(p1Board);
        placeShipsRandom(p2Board);
    }

    private void placeShipsRandom(Board board) {
        Random rand = new Random();
        int shipsToPlace = 5;
        while (shipsToPlace > 0) {
            int x = rand.nextInt(Board.SIZE);
            int y = rand.nextInt(Board.SIZE);
            if (board.getCell(x, y) == '~') {
                board.setCell(x, y, 'S');
                shipsToPlace--;
            }
        }
    }

    public boolean processMove(GameMove move, Board targetBoard) throws InvalidMoveException {
        if (!move.isValid(targetBoard))
            throw new InvalidMoveException("Invalid move at (" + move.getX() + "," + move.getY() + ")");
        move.execute(targetBoard);
        return targetBoard.getCell(move.getX(), move.getY()) == 'X';
    }

    public boolean isGameOver() {
        return p1Board.getRemainingShips() == 0 || p2Board.getRemainingShips() == 0;
    }

    public Board getP1Board() { return p1Board; }
    public Board getP2Board() { return p2Board; }
}


