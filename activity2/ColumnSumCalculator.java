package activity2;

import java.util.Scanner;
public class ColumnSumCalculator {
    public static double sumColumn(double[][] matrix, int columnIndex) {
        double sum = 0;
        for (double[] row : matrix) {
            sum += row[columnIndex];
        }
        return sum;
    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        final int ROWS = 3;
        final int COLUMNS = 4;
        double[][] matrix = new double[ROWS][COLUMNS];
        System.out.println("Enter a 3-by-4 matrix row by row:");
        for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                matrix[i][j] = input.nextDouble();
            }
        }
    }
}
