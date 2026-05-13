package com.boardgame.service;

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

