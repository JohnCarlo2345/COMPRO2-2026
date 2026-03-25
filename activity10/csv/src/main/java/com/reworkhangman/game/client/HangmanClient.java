package com.reworkhangman.game.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class HangmanClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            String serverMessage;
            while ((serverMessage = in.readLine()) != null) {
                switch (serverMessage) {
                    case "SEND_CREDENTIALS":
                        System.out.print("Enter username: ");
                        out.println(scanner.nextLine());
                        System.out.print("Enter password: ");
                        out.println(scanner.nextLine());
                        break;
                    case "AUTH_FAILED":
                        System.out.println("Authentication failed! Invalid username/password.");
                        return;
                    case "AUTH_SUCCESS":
                        System.out.println(in.readLine()); // Welcome message
                        break;
                    case "GAME_READY":
                        System.out.println("\nGame started!");
                        System.out.println(in.readLine()); // Masked word
                        System.out.println(in.readLine()); // Attempts left
                        break;
                    case "SEND_GUESS":
                        System.out.print("\nEnter your guess (single letter): ");
                        out.println(scanner.nextLine().trim());
                        break;
                    case "INVALID_INPUT":
                    case "ALREADY_GUESSED":
                        System.out.println("Error: " + serverMessage);
                        break;
                    case "GUESS_CORRECT":
                        System.out.println("Correct guess!");
                        System.out.println(in.readLine()); // Updated masked word
                        System.out.println(in.readLine()); // Remaining attempts
                        break;
                    case "GUESS_WRONG":
                        System.out.println("Wrong guess!");
                        System.out.println(in.readLine()); // Updated masked word
                        System.out.println(in.readLine()); // Remaining attempts
                        break;
                    case "GAME_WON":
                    case "GAME_LOST":
                        System.out.println("\n" + serverMessage);
                        return;
                    default:
                        System.out.println(serverMessage);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

