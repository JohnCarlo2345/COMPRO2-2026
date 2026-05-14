package com.boardgame.service;

import com.boardgame.model.GameMove;

public class BattleshipMove extends GameMove {
    public BattleshipMove(int x, int y) {
        super(x, y);
    }

    public boolean isValid(Board board) {
        return board.isWithinBounds(x, y) 
            && board.getCell(x, y) != 'X' 
            && board.getCell(x, y) != 'O';
    }

    public void execute(Board board) {
        if (board.getCell(x, y) == 'S') {
            board.setCell(x, y, 'X');
        } else {
            board.setCell(x, y, 'O');
        }
    }
}

