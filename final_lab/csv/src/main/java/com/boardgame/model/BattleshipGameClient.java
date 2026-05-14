package com.boardgame.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class BattleshipGameClient {
    private static final String HOST = "localhost";
    private static final int PORT = 5000;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner sc = new Scanner(System.in)) {

            Board myBoard = new Board();
            Board enemyView = new Board();

            System.out.println("=== BATTLESHIP GAME ===");
            myBoard.print();

            String signal;
            while ((signal = in.readLine()) != null) {
                if (signal.equals("YOUR_TURN")) {
                    System.out.println("\n👉 Enter your move (x y):");
                    int x = sc.nextInt();
                    int y = sc.nextInt();
                    out.println(x + "," + y);

                    String result = in.readLine();
                    if (result.equals("HIT")) {
                        System.out.println("✅ HIT!");
                        enemyView.setCell(x, y, 'X');
                    } else {
                        System.out.println("❌ MISS!");
                        enemyView.setCell(x, y, 'O');
                    }
                }
                else if (signal.equals("WAIT")) {
                    System.out.println("⌛ Opponent's turn... please wait.");
                }
                else if (signal.startsWith("OPPONENT")) {
                    String[] parts = signal.split(" ");
                    int x = Integer.parseInt(parts[1]);
                    int y = Integer.parseInt(parts[2]);
                    String res = parts[3];

                    System.out.println("⚠️ Opponent shot (" + x + "," + y + ") → " + res);
                    myBoard.setCell(x, y, res.equals("HIT") ? 'X' : 'O');
                    myBoard.print();
                } 
                else if (signal.equals("GAME_OVER")) {
                    System.out.println("🏁 GAME OVER!");
                    break;
                }

                System.out.println("--- Enemy Board View ---");
                enemyView.print();
            }

        } catch (Exception e) {
        }
    }
}

