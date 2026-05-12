package com.boardgame.service;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;

public class BattleshipGameServer {
    private static final int PORT = 5000;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Battleship Game Server running on port " + PORT);

            // Accept 2 players
            Socket p1Socket = serverSocket.accept();
            Socket p2Socket = serverSocket.accept();
            System.out.println("Both players connected!");

            // Streams
            BufferedReader p1In = new BufferedReader(new InputStreamReader(p1Socket.getInputStream()));
            PrintWriter p1Out = new PrintWriter(p1Socket.getOutputStream(), true);
            BufferedReader p2In = new BufferedReader(new InputStreamReader(p2Socket.getInputStream()));
            PrintWriter p2Out = new PrintWriter(p2Socket.getOutputStream(), true);

            // Game instance
            BattleshipGame game = new BattleshipGame();
            boolean isP1Turn = true;

            // Persistence: save to history.csv
            BufferedWriter log = new BufferedWriter(new FileWriter("history.csv", true));
            log.write("Timestamp,Player,X,Y,Result\n");

            // Ping-Pong turn loop
            while (!game.isGameOver()) {
                try {
                    if (isP1Turn) {
                        p1Out.println("YOUR_TURN");
                        p2Out.println("WAIT");

                        String moveStr = p1In.readLine();
                        if (moveStr == null) throw new ConnectionLostException("Player 1 disconnected");

                        String[] xy = moveStr.split(",");
                        int x = Integer.parseInt(xy[0]);
                        int y = Integer.parseInt(xy[1]);

                        GameMove move = new BattleshipMove(x, y);
                        boolean hit = game.processMove(move, game.getP2Board());

                        log.write(LocalDateTime.now() + ",P1," + x + "," + y + "," + (hit ? "HIT" : "MISS") + "\n");
                        p1Out.println(hit ? "HIT" : "MISS");
                        p2Out.println("OPPONENT " + x + " " + y + " " + (hit ? "HIT" : "MISS"));
                    } 
                    else {
                        p2Out.println("YOUR_TURN");
                        p1Out.println("WAIT");

                        String moveStr = p2In.readLine();
                        if (moveStr == null) throw new ConnectionLostException("Player 2 disconnected");

                        String[] xy = moveStr.split(",");
                        int x = Integer.parseInt(xy[0]);
                        int y = Integer.parseInt(xy[1]);

                        GameMove move = new BattleshipMove(x, y);
                        boolean hit = game.processMove(move, game.getP1Board());

                        log.write(LocalDateTime.now() + ",P2," + x + "," + y + "," + (hit ? "HIT" : "MISS") + "\n");
                        p2Out.println(hit ? "HIT" : "MISS");
                        p1Out.println("OPPONENT " + x + " " + y + " " + (hit ? "HIT" : "MISS"));
                    }

                    isP1Turn = !isP1Turn;

                } catch (InvalidMoveException e) {
                    System.out.println("Error: " + e.getMessage());
                } catch (ConnectionLostException e) {
                    System.err.println("Connection lost: " + e.getMessage());
                    break;
                }
            }

            log.write(LocalDateTime.now() + ",GAME_OVER,,,\n");
            log.close();
            p1Out.println("GAME_OVER");
            p2Out.println("GAME_OVER");
            System.out.println("Game finished. History saved.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
