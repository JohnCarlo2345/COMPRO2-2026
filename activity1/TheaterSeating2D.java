package activity1;

public class TheaterSeating2D {
    public static void main(String[] args) {
        int[][] theater = new int[5][8];

        int row1 = 1, col1 = 3;
        if (isValidSeat(theater, row1, col1) && theater[row1][col1] == 0) {
            theater[row1][col1] = 1;
            System.out.println("Seat (" + row1 + ", " + col1 + ") bookedsuccessfully!");
        } else {
            System.out.println("Seat (" + row1 + ", " + col1 + ") cannot be cooked.");
        }
        int row2 = 3, col2 = 5;
        if (isValidSeat(theater, row2, col2) && theater[row2][col2] == 0) {
            theater[row2][col2] = 1;
            System.out.println("Seat (" + row2 + ", " + col2 + ") bookedsuccessfully!");
        } else {
            System.out.println("Seat (" + row2 + ", " + col2 + ") cannot be cooked");
        }
        System.out.println("\nTheater Seat statuses (0=Available, 1=Booked):");
        for (int i = 0; i < theater.length; i++) {
            System.out.print("Row " + i + ": ");
        }
    }
}
