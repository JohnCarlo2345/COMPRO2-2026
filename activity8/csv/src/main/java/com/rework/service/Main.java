package com.rework.service;

import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    double[][] grades = new double[50][3];
    String[] subjectNames = new String[50];
    int counter = 0;
    Scanner input = new Scanner(System.in);
    private static final String JSON_FILE = "studentGrades.json";


    public void displayMenu() {
        System.out.println("=== Student Subject Portfolio ===");
        System.out.println("1. Enter Grade (Add new subject and grades)");
        System.out.println("2. Exit (Save data to JSON)");
        System.out.print("Select an option: ");
    }


    public void addSubjectAndGrades() {
        if (counter >= 50) {
            System.out.println("Maximum 50 subjects allowed!");
            return;
        }

        System.out.print("Enter Subject Name: ");
        subjectNames[counter] = input.nextLine();
        
        System.out.print("Enter Prelim Grade: ");
        grades[counter][0] = input.nextDouble();
        
        System.out.print("Enter Midterm Grade: ");
        grades[counter][1] = input.nextDouble();
        
        System.out.print("Enter Final Grade: ");
        grades[counter][2] = input.nextDouble();
        
        input.nextLine();
        counter++;
        System.out.println("Subject added successfully!");
    }


    public void saveToJSON() {
        JSONArray subjectArray = new JSONArray();
        
        for (int i = 0; i < counter; i++) {
            JSONObject subjectObj = new JSONObject();
            subjectObj.put("subjectName", subjectNames[i]);
            subjectObj.put("prelimGrade", grades[i][0]);
            subjectObj.put("midtermGrade", grades[i][1]);
            subjectObj.put("finalGrade", grades[i][2]);
            subjectArray.put(subjectObj);
        }

        try (FileWriter writer = new FileWriter(JSON_FILE)) {
            writer.write(subjectArray.toString(4));
            System.out.println("Data saved to " + JSON_FILE + " successfully!");
        } catch (IOException e) {
            System.out.println("Error saving data to JSON file.");
        }
    }


    public void loadFromJSON() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(JSON_FILE)));
            JSONArray subjectArray = new JSONArray(content);
            
            for (int i = 0; i < subjectArray.length(); i++) {
                JSONObject subjectObj = subjectArray.getJSONObject(i);
                subjectNames[i] = subjectObj.getString("subjectName");
                grades[i][0] = subjectObj.getDouble("prelimGrade");
                grades[i][1] = subjectObj.getDouble("midtermGrade");
                grades[i][2] = subjectObj.getDouble("finalGrade");
                counter++;
            }
            System.out.println("Existing data loaded from " + JSON_FILE + "!");
        } catch (IOException e) {
            System.out.println("No existing JSON file found - starting fresh.");
        } catch (org.json.JSONException e) {
            System.out.println("Invalid JSON file - starting fresh.");
        }
    }


    public static void main(String[] args) {
        Main portfolio = new Main();
        portfolio.loadFromJSON();
        int choice;

        do {
            portfolio.displayMenu();
            choice = portfolio.input.nextInt();
            portfolio.input.nextLine();

            switch (choice) {
                case 1:
                    portfolio.addSubjectAndGrades();
                    break;
                case 2:
                    portfolio.saveToJSON();
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid option! Please select 1 or 2.");
            }
            System.out.println();
        } while (choice != 2);
        
        portfolio.input.close();
        }
    }

