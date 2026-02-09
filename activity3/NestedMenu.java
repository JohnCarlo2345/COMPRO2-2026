import java.util.Scanner;
public class NestedMenu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double[] subjectGrades = new double[3];
        String[] subjects = {"COMPRO2", "DSA", "OOP"};
        int mainChoice, subChoice;
        do {
            System.out.println("MAIN MENU: ");
            System.out.println("[1] Enter Grades");
            System.out.println("[2] Display Grades");
            System.out.println("[3] Exit");
            System.out.print("Enter choice: ");
            mainChoice = scanner.nextInt();
            switch (mainChoice) {
                case 1:
                    do {
                        System.out.println("\nEnter grade for:");
                        System.out.println("[1] " + subjects[0]);
                        System.out.println("[2] " + subjects[1]);
                        System.out.println("[3] " + subjects[2]);
                        System.out.println("[0] Go Back");
                        System.out.print("Enter choice: ");
                        subChoice = scanner.nextInt();
                        if (subChoice >= 1 && subChoice <= 3) {
                            System.out.print("Enter grades for " + subjects[subChoice - 1] + ": ");
                            subjectGrades[subChoice - 1] = scanner.nextDouble();
                            System.out.println("Grades, saved...\n");
                        } else if (subChoice != 0) {
                            System.out.println("Invalid sub-choice! try again.\n");
                        }
                    }
            }
        }
    }
}
