package com.boardgame.model;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import com.boardgame.service.Board;

public class BattleshipGameClient {
    private static final String HOST = "localhost";
    private static final int PORT = 5000;

    public static void main(String[] args) throws NumberFormatException, IOException {
        try (Socket socket = new Socket(HOST, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner sc = new Scanner(System.in)) {

            Board myBoard = new Board();
            Board enemyView = new Board();

            System.out.println("=== BATTLESHIP GAME ===");
            System.out.println("Your Board:");
            myBoard.print();

            String signal;
            while ((signal = in.readLine()) != null) {
                if (signal.equals("YOUR_TURN")) {
                    System.out.println("YOUR TURN! Enter X Y (example: 3 5):");
                    int x = sc.nextInt();
                    int y = sc.nextInt();
                    out.println(x + "," + y);

                    String result = in.readLine();
                    // Read all leaderboard lines
                    String lb1 = in.readLine();
                    String lb2 = in.readLine();
                    String lb3 = in.readLine();
                    String lb4 = in.readLine();
                    String lb5 = in.readLine();
                    String lb6 = in.readLine();
                    String lb7 = in.readLine();
                    String lb8 = in.readLine();
                    String lb9 = in.readLine();
                    String lb10 = in.readLine();

                    if (result.equals("HIT")) {
                        System.out.println("Result: HIT");
                        enemyView.setCell(x, y, 'X');
                    } else {
                        System.out.println("Result: MISS");
                        enemyView.setCell(x, y, 'O');
                    }
                    
                    // Show FULL Leaderboard
                    System.out.println(lb1);
                    System.out.println(lb2);
                    System.out.println(lb3);
                    System.out.println(lb4);
                    System.out.println(lb5);
                    System.out.println(lb6);
                    System.out.println(lb7);
                    System.out.println(lb8);
                    System.out.println(lb9);
                    System.out.println(lb10);
                } 
                else if (signal.equals("WAIT")) {
                    System.out.println("Opponent's turn... please wait.");
                } 
                else if (signal.startsWith("OPPONENT")) {
                    String[] parts = signal.split(" ");
                    int x = Integer.parseInt(parts[1]);
                    int y = Integer.parseInt(parts[2]);
                    String res = parts[3];
                    
                    // Read all leaderboard lines
                    String lb1 = in.readLine();
                    String lb2 = in.readLine();
                    String lb3 = in.readLine();
                    String lb4 = in.readLine();
                    String lb5 = in.readLine();
                    String lb6 = in.readLine();
                    String lb7 = in.readLine();
                    String lb8 = in.readLine();
                    String lb9 = in.readLine();
                    String lb10 = in.readLine();

                    System.out.println("Opponent shot (" + x + "," + y + ") -> " + res);
                    myBoard.setCell(x, y, res.equals("HIT") ? 'X' : 'O');
                    System.out.println("Your Board:");
                    myBoard.print();
                    
                    // Show FULL Leaderboard
                    System.out.println(lb1);
                    System.out.println(lb2);
                    System.out.println(lb3);
                    System.out.println(lb4);
                    System.out.println(lb5);
                    System.out.println(lb6);
                    System.out.println(lb7);
                    System.out.println(lb8);
                    System.out.println(lb9);
                    System.out.println(lb10);
                }
            }
        }
    }
}
