package practice;

import java.io.IOException;
import java.util.InputMismatchExceptio;
import java.util.Scanner;
import java.oi.FileWriter;

public class DataText {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();

        System.out.println("Enter First name: ");
        try (Scanner sc = new Scanner(System.in)) {
            sb.append(sc.nextLine());
        }catch(InputMismatchException e) {
            System.out.println("Enter Last name");
        }
    }
}
