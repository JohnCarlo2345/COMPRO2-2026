import java.util.Scanner;
public class Main {
    double[][] grades = new double[50][3];
    String[] subjectNames = new String[50];
    int counter = 0;
    Scanner input = new Scanner(System.in);
    public void displayMenu() {
        System.out.println("\n=== Student Subject Portfolio ===");
        System.out.println("1. Enter Grade (Add new subject and grades)");
        System.out.println("2. Exit (Save data to CSV)");
        System.out.println("Select an option: ");
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
        System.out.println("Subject added successfully");
    }
}
