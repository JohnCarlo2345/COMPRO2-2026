package com.boardgame.service;

import com.boardgame.model.GameMove;
import com.boardgame.service.Board;
import com.boardgame.service.InvalidMoveException;

public class BattleshipMove extends GameMove {

    public BattleshipMove(int x, int y) {
        super(x, y);
    }

    public boolean isValid(Board board) {
        if (x < 0 || x >= Board.SIZE || y < 0 || y >= Board.SIZE) {
            return false;
        }
        return board.getCell(x, y) == Board.EMPTY || board.getCell(x, y) == Board.SHIP;
    }

    public void execute(Board board) throws InvalidMoveException {
        if (!isValid(board)) {
            throw new InvalidMoveException("Invalid move at (" + x + "," + y + ")");
        }
        if (board.hasShip(x, y)) {
            board.setCell(x, y, Board.HIT);
            board.shipSunk();
        } else {
            board.setCell(x, y, Board.MISS);
        }
    }
}


