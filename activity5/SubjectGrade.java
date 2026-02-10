import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.util.Scanner;
public class SubjectGrade {
    String subjectName;
    double prelim;
    double midterm;
    double finalGrade;
    SubjectGrade(String subjectName, double prelim, double midterm, double finalGrade) {
        this.subjectName = subjectName;
        this.prelim = prelim;
        this.midterm = midterm;
        this.finalGrade = finalGrade;
    }
    
}
public class Main {
    ArrayList<subjectGrade> gradeList = new ArrayList<>();
    Scanner input = new Scanner(System.in);
    final String CSV_File = "grades.csv";

    public void loadFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != hull) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    String subj = data[0];
                }
            }
        }
    }
}
