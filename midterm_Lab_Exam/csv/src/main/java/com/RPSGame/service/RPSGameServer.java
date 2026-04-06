package com.RPSGame.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class RPSGameServer {
    public static void main(String[] args) {
        final int PORT = 1234;

        ServerSocket gameServer;
        Socket player1;
        Socket player2;

        System.out.println("---- Rock-Paper-Scissors Server Starting Up ----");
        System.out.println("Waiting for TWO players to join...\n");

        try {
            gameServer = new ServerSocket(PORT);

            player1 = gameServer.accept();
            System.out.println("Player 1 joined from: " + player1.getInetAddress());
            sendMessageToPlayer(player1, "Hey you're PLAYER 1! Wait for Player 2 to join...");

            player2 = gameServer.accept();
            System.out.println("Player 2 joined from: " + player2.getInetAddress());
            sendMessageToPlayer(player2, "Hey you're PLAYER 2!");
            sendMessageToPlayer(player1, "Player 2 is here!");

            Player p1 = new Player("Player 1");
            Player p2 = new Player("Player 2");
            GameSession game = new GameSession(p1, p2);

            while (!game.isGameOver()) {
                sendMessageToPlayer(player1, "Choose your move (0=Rock, 1=Paper, 2=Scissors)");
                sendMessageToPlayer(player2, "Choose your move (0=Rock, 1=Paper, 2=Scissors)");

                String move1 = getPlayerChoice(player1);
                String move2 = getPlayerChoice(player2);

                p1.setCurrentMove(createMove(move1));
                p2.setCurrentMove(createMove(move2));

                String result = game.playRound();
                System.out.println(result);
                sendMessageToPlayer(player1, result);
                sendMessageToPlayer(player2, result);
            }

            String finalRes = game.getFinalResult();
            sendMessageToPlayer(player1, finalRes);
            sendMessageToPlayer(player2, finalRes);

            player1.close();
            player2.close();
            gameServer.close();
            System.out.println("\nGame over! Server shutting down.");

        } catch (IOException e) {
            System.out.println("Oops something broke with the server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void sendMessageToPlayer(Socket player, String message) throws IOException {
        PrintWriter out = new PrintWriter(player.getOutputStream(), true);
        out.println(message);
    }

    private static String getPlayerChoice(Socket player) throws IOException {
        try (Scanner in = new Scanner(player.getInputStream())) {
            return in.nextLine().trim();
        }
    }

    public static GameMove createMove(String type) {
        return switch (type) {
            case "0" -> new Rock();
            case "1" -> new Paper();
            case "2" -> new Scissors();
            default -> new Rock();
        };
    }
}

