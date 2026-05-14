package com.boardgame.service;

import com.boardgame.model.Board;
import com.boardgame.model.GameMove;

public class BattleshipMove extends GameMove {
    public BattleshipMove(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean isValid(Board board) {
        return board.isWithinBounds(x, y)
            && board.getCell(x, y) != 'X'
            && board.getCell(x, y) != 'O';
    }

    @Override
    public void execute(Board board) {
        if (board.getCell(x, y) == 'S') {
            board.setCell(x, y, 'X'); // Hit
        } else {
            board.setCell(x, y, 'O'); // Miss
        }
    }
}

