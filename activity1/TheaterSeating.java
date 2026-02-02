package activity1;

public class TheaterSeating {
    public static void main(String[] args) {
        int[] theaterRow = new int[10];

        int seatToBook = 3;
        if (seatToBook >= 0 && seatToBook < theaterRow.length && theaterRow[seatToBook] == 0) {
            theaterRow[seatToBook] = 1;
            System.out.println("Seat at index " + seatToBook + " successfully booked");
        } else {
            System.out.println("Seat at index " + seatToBook + " cannot be booked (invalid or already taken).");
        }
        System.out.println("\nCurrent Seat statuses (0=Available, 1=Booked):");
        for (int i = 0; i < theaterRow.length; i++) {
            System.out.println("Seat " + i + ": " + theaterRow[i]);
        }
    }
}
