package com.boardgame.service;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import com.boardgame.model.BattleshipGame;
import com.boardgame.model.ConnectionLostException;
import com.boardgame.model.GameMove;

public class BattleshipGameServer {
    private static final int PORT = 5000;
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Battleship Game Server running on port " + PORT);

            Socket p1Socket = serverSocket.accept();
            System.out.println("Player 1 connected");
            Socket p2Socket = serverSocket.accept();
            System.out.println("Player 2 connected");

            BufferedReader p1In = new BufferedReader(new InputStreamReader(p1Socket.getInputStream()));
            PrintWriter p1Out = new PrintWriter(p1Socket.getOutputStream(), true);
            BufferedReader p2In = new BufferedReader(new InputStreamReader(p2Socket.getInputStream()));
            PrintWriter p2Out = new PrintWriter(p2Socket.getOutputStream(), true);

            BattleshipGame game = new BattleshipGame();
            boolean isP1Turn = true;

            // Save FULL stats to CSV
            BufferedWriter log = new BufferedWriter(new FileWriter("history.csv", true));
            log.write("Timestamp,Player,X,Y,Result,P1_ShipsSunk,P1_Hits,P1_Misses,P2_ShipsSunk,P2_Hits,P2_Misses\n");

            while (!game.isGameOver()) {
                try {
                    if (isP1Turn) {
                        p1Out.println("YOUR_TURN");
                        p2Out.println("WAIT");

                        String moveStr = p1In.readLine();
                        if (moveStr == null) {
                            throw new ConnectionLostException("Player 1 disconnected");
                        }

                        String[] xy = moveStr.split(",");
                        int x = Integer.parseInt(xy[0]);
                        int y = Integer.parseInt(xy[1]);

                        GameMove move = new BattleshipMove(x, y);
                        boolean hit = game.processMove(move, game.getP2Board(), true);

                        // Save all stats to CSV
                        String lb = game.getLeaderboard();
                        String[] lines = lb.split("\n");
                        int p1Sunk = Integer.parseInt(lines[2].split(": ")[1]);
                        int p1Hit = Integer.parseInt(lines[3].split(": ")[1]);
                        int p1Miss = Integer.parseInt(lines[4].split(": ")[1]);
                        int p2Sunk = Integer.parseInt(lines[7].split(": ")[1]);
                        int p2Hit = Integer.parseInt(lines[8].split(": ")[1]);
                        int p2Miss = Integer.parseInt(lines[9].split(": ")[1]);

                        log.write(LocalDateTime.now() + ",P1," + x + "," + y + "," + (hit ? "HIT" : "MISS") + "," + p1Sunk + "," + p1Hit + "," + p1Miss + "," + p2Sunk + "," + p2Hit + "," + p2Miss + "\n");

                        // Send result and leaderboard
                        p1Out.println(hit ? "HIT" : "MISS");
                        p1Out.println(game.getLeaderboard());
                        p2Out.println("OPPONENT " + x + " " + y + " " + (hit ? "HIT" : "MISS"));
                        p2Out.println(game.getLeaderboard());
                    } 
                    else {
                        p2Out.println("YOUR_TURN");
                        p1Out.println("WAIT");

                        String moveStr = p2In.readLine();
                        if (moveStr == null) {
                            throw new ConnectionLostException("Player 2 disconnected");
                        }

                        String[] xy = moveStr.split(",");
                        int x = Integer.parseInt(xy[0]);
                        int y = Integer.parseInt(xy[1]);

                        GameMove move = new BattleshipMove(x, y);
                        boolean hit = game.processMove(move, game.getP1Board(), false);

                        // Save all the stats to CSV
                        String lb = game.getLeaderboard();
                        String[] lines = lb.split("\n");
                        int p1Sunk = Integer.parseInt(lines[2].split(": ")[1]);
                        int p1Hit = Integer.parseInt(lines[3].split(": ")[1]);
                        int p1Miss = Integer.parseInt(lines[4].split(": ")[1]);
                        int p2Sunk = Integer.parseInt(lines[7].split(": ")[1]);
                        int p2Hit = Integer.parseInt(lines[8].split(": ")[1]);
                        int p2Miss = Integer.parseInt(lines[9].split(": ")[1]);

                        log.write(LocalDateTime.now() + ",P2," + x + "," + y + "," + (hit ? "HIT" : "MISS") + "," + p1Sunk + "," + p1Hit + "," + p1Miss + "," + p2Sunk + "," + p2Hit + "," + p2Miss + "\n");

                        p2Out.println(hit ? "HIT" : "MISS");
                        p2Out.println(game.getLeaderboard());
                        p1Out.println("OPPONENT " + x + " " + y + " " + (hit ? "HIT" : "MISS"));
                        p1Out.println(game.getLeaderboard());
                    }

                    isP1Turn = !isP1Turn;

                } catch (InvalidMoveException e) {
                    System.out.println("Invalid move: " + e.getMessage());
                } catch (ConnectionLostException e) {
                    System.out.println("Connection error: " + e.getMessage());
                    break;
                }
            }

            log.write(LocalDateTime.now() + ",GAME_OVER,,,,,,,,,,\n");
            log.close();

            // Send final result
            p1Out.println("GAME_OVER");
            p1Out.println(game.getLeaderboard());
            p2Out.println("GAME_OVER");
            p2Out.println(game.getLeaderboard());
            System.out.println("Game finished. History saved.");

        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}

