package activity2;

import java.util.Scanner;
public class DiagonalSumCalculator {
    public static double sumMajorDiagonal(double[][] matrix) {
        double sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            sum += matrix[i][j];
        }
        return sum;
    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        final int SIZE = 4;
        double[][] matrix = new double[SIZE][SIZE];
        System.out.println("Enter a 4-by-4 matrix row by row:");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
            }
        }
    }
}
