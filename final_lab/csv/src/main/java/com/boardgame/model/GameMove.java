package com.boardgame.model;

import com.boardgame.service.Board;
import com.boardgame.service.InvalidMoveException;

public abstract class GameMove {
    protected int x;
    protected int y;

    public GameMove(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract boolean isValid(Board board);
    public abstract void execute(Board board) throws InvalidMoveException;

    public int getX() { return x; }
    public int getY() { return y; }
}



