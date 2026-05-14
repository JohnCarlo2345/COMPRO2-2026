package com.boardgame.model;

public class Board {
    public static final int SIZE = 10;
    private final char[][] grid; // '~'=water, 'S'=ship, 'X'=hit, 'O'=miss

    public Board() {
        grid = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = '~';
            }
        }
    }

    public boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < SIZE && y >= 0 && y < SIZE;
    }

    public char getCell(int x, int y) { return grid[x][y]; }
    public void setCell(int x, int y, char state) { grid[x][y] = state; }

    public void print() {
        System.out.println("  0 1 2 3 4 5 6 7 8 9");
        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int getRemainingShips() {
        int count = 0;
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (grid[i][j] == 'S') count++;
        return count;
    }
}

