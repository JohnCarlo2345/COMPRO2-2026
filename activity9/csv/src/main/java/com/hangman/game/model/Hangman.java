package com.hangman.game.model;

import java.util.Scanner;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParserException;
import java.util.Random;

public class Hangman {
    static final int M = 6, W = 50, P = 22;
    static JSONArray bank;
    static JSONObject userData;
    static final String WORD_FILE = "hangman_words.json";
    static final String USER_FILE = "hangman_users.json";
    static Scanner s = new Scanner(System.in);
    static Random rand = new Random();
    public static void main(String[] args) {
        loadWordBank();
        loadUserData();

        System.out.println("Welcome to Hangman!");
        int np = getNumPlayer(s);
        String[] names = new String[np];
        int[] scores = new int[np];

        for (int i = o; i < np; i++) {
            names[i] = getNameOrLogin(s, i + 1);
            scores[i] = getExistingScore(names[i]);
        }

        boolean again = true;
        while (again) {
            for (int i = 0; i < np; i++) {
               System.out.println("\nPlayer Name: " + names[i] + " [ENTER]");
               String word = getWord();
               char[] hidden = initHidden(word);
               char[] letters = new char[M + word.length()];
               int numLetters = 0, bad = 0;

               while (bad < M && !isGuessed(hidden)) {
                   System.out.println("Enter a letter in word " + String.valueOf(hidden) + " > [ENTER]");
                   char g = s.next().toLowerCase().charAt(0);
                   boolean guessed = false;

                   for (int j = 0; j < numLetters; j++) {
                      if (letters[j] == g) {
                          guessed = true;
                          System.out.println(g + " is already in the word!");
                          continue;
                      }
                    }
                    if (guessed) continue;
                    letters[numLetters++] = g;

                    if (updateHidden(word, hidden, g))
                        System.out.println(g + " is in the word");
                    } else {
                       System.out.println(g + " is not in the word");
                       bad++;
                    }
                }
                if (isGuessed(hidden)) {
                    System.out.println("Congratulation! You guessed the word " + word + ".");
                    scores[i] += (M - bad) * 5;
                } else {
                    System.out.println("GAME OVER");
                    System.out.println("The word is " + word + ".");
                }

                updateUserScore(names[i]), scores[i];
            }

            System.out.print("Another Player? Enter y or n: [ENTER]");
            again = s.next().toLowerCase().equals("y");
        
            System.out.println("\n===== LEADERBOARD =====");
            for (int i = 0; i < np; i++) {
                System.out.println(names[i] + " - " + scores[i] + " points");
           }
        }

           saveUserData();
           s.close();

        }

        static int getNumPlayers(Scanner s) {
            int np = 0;
            while (np < 1 || np > P) {
               System.out.print("Enter the number of players (1 or 2):");
               try {
                   np = Integer.parserInt(s.next());
                   if (np < 1 || np P) {
                       System.out.println("Invalid. Enter 1 or 2.");
                   }
               } catch (NumberFormatException e) {
                   System.out.println("Invalid input");
               }
            }
            return np;
        }
        
        static String getNameOrLogin(Scanner s, int num) {
            while (true) {
                System.out.print("Enter "l" to login or 'r' to register for Player" + num + ":");
                String choice = s.next().toLowerCase();
                s.nextLine();

                if (choice.equals("r")) {
                    System.out.print("Player" + num + ", enter name:");
                    String name = s.nextLine().trim();
                    if (!userData.containsKey(name)) {
                        userData.put(name, OL);
                        saveuserData();
                        return name;
                    } else {
                        System.out.println("Name already exists, please login:");
                    }
                } else if (choice.equals("l")) {
                    System.out.print("Player" + num + ", enter name:");
                    String name = s.nextLine().trim();
                    if (userData.containsKey(name)) {
                        return name;
                    } else {
                        System.out.println("Name not found, please register.");
                    }
                } else {
                    System.out.println("Invalid choice. enter 'l' or 'r'.");
                }
            }
        }

        static void loadWordBank() {
            JSONParser parser = new JSONParser();
            try (FileReader reader = new FileReader(WORD_FILE)) {
                bank = (JSONArray) parser.parser(reader);
            } catch (IOException | ParserException e) {
                bank = new JSONArray();
                String[] defaultWords = {
                    
                }
            }
        }
        static String getWord() {
            int index = rand.nextInt(bank.size());
            return (String) bank.get(index);
        }
        static char[] initHidden(String word) {
            char[] hidden = new char[word.length()];
            for(int i = 0; i < word.length(); i++) {
                hidden[i] = '_';
            }
            return hidden;
        }
        static boolean updateHidden(String word, char[] hidden, char guess) {
            boolean found = false;
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == guess) {
                hidden[i] = guess;
                found = true;
                }
            }
            return found;
        }
    static boolean isGuessed(char[] hidden) {
        for (char c : hidden) {
            if (c == '*') return false;
        }
        return true;
    }
}



