package com.boardgame.service;

public class Board {
    public static final int SIZE = 10;
    public static final char EMPTY = '.';
    public static final char SHIP = 'S';
    public static final char HIT = 'X';
    public static final char MISS = 'O';

    private char[][] grid;
    private boolean[][] shipPositions;
    private int remainingShips;

    public Board() {
        grid = new char[SIZE][SIZE];
        shipPositions = new boolean[SIZE][SIZE];
        remainingShips = 5; // Total 5 ships

        // Fill board with empty cells
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = EMPTY;
            }
        }
    }

    public void setCell(int x, int y, char c) {
        grid[x][y] = c;
        if (c == SHIP) {
            shipPositions[x][y] = true;
        }
    }

    public char getCell(int x, int y) {
        return grid[x][y];
    }

    public boolean hasShip(int x, int y) {
        return shipPositions[x][y];
    }

    public void shipSunk() {
        if (remainingShips > 0) {
            remainingShips--;
        }
    }

    public int getRemainingShips() {
        return remainingShips;
    }

    // Show numbers on top and left side
    public void print() {
        // Print top numbers (X-axis)
        System.out.print("  "); // Space for left side numbers
        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        // Print each row with left number
        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + " "); // Left side number
            for (int j = 0; j < SIZE; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
}

