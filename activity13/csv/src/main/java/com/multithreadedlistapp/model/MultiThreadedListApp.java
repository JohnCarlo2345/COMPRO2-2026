package com.multithreadedlistapp.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MultiThreadedListApp {
    static List<String> data = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        // load data
        data.add("Item 1");
        data.add("Item 2");
        data.add("Item 3");

        // thread 1
        Thread saver = new Thread(() -> {
            // this is the run method, put here the things you want to do
            while(true) {
                // save the list
                saveToDisk();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });

        Thread fetcher = new Thread(() -> {
            while(true) {
                // read file
                readFile();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });

        // set both as daemon so they close when main is terminated
        saver.setDaemon(true);
        fetcher.setDaemon(true);
        saver.start();
        fetcher.start();

        // MENU
        try {
            Thread.sleep(20000); // Let threads run for 20 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void saveToDisk() {
        // write to file
        try {
            FileWriter writer = new FileWriter("data.txt");
            for(String item : data) {
                writer.write(item + "\n");
            }
            writer.close();
            System.out.println("Data saved to file!");
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public static void readFile() {
        // read from file
        try {
            File file = new File("data.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            System.out.println("\n--- Reading from file ---");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}

