package com.RPSGame.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class RPSGameClient {
    public static void main(String[] args) {
        try {
            try (Scanner userInput = new Scanner(System.in)) {
                System.out.print("Enter server IP address (type 'localhost' if playing on same computer): ");
                String serverIP = userInput.nextLine();
                final int PORT = 1234;

                try (Socket clientSocket = new Socket(serverIP, PORT)) {
                    System.out.println("Connected to game server!\n");
                    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
                    String serverMessage;
                    while ((serverMessage = inFromServer.readLine()) != null) {
                        System.out.println("SERVER: " + serverMessage);
                        if (serverMessage.contains("choose")) {
                            System.out.print("Your choice: ");
                            String myChoice = userInput.nextLine();
                            outToServer.println(myChoice);
                        }
                        if (serverMessage.startsWith("GAME OVER")) {
                            break;
                        }
                    }
                    clientSocket.close();
                    userInput.close();
                }
            }

        } catch (IOException e) {
            System.out.println("Problem connecting to server: " + e.getMessage());
        }
    }
}
