import java.io.*;
import java.util.*;

public class GradeQuery {

    public static void main(String[] args) {
        String studentsFile = "src/Students.txt";
        String coursesFile = "src/Courses.txt";
        String enrollmentsFile = "src/Enrollments.txt";

        Map<String, String> courseIdToName = readCourses(coursesFile);
        Map<String, String> studentIdToName = readStudents(studentsFile);
        List<String> qualifyingStudents = readEnrollments(enrollmentsFile, courseIdToName);

        System.out.println("Students who took 'Databases' in Winter 2024 with at least B+:");
        for (String studentId : qualifyingStudents) {
            System.out.println(studentIdToName.get(studentId) + " (ID: " + studentId + ")");
        }
    }

    private static Map<String, String> readCourses(String coursesFile) {
        Map<String, String> courseIdToName = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(coursesFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if ("Databases".equals(parts[1])) {
                    courseIdToName.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return courseIdToName;
    }

    private static Map<String, String> readStudents(String studentsFile) {
        Map<String, String> studentIdToName = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(studentsFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                studentIdToName.put(parts[0], parts[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return studentIdToName;
    }

    private static List<String> readEnrollments(String enrollmentsFile, Map<String, String> courseIdToName) {
        List<String> qualifyingStudents = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(enrollmentsFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if ("Winter".equals(parts[2]) && "2024".equals(parts[3]) && courseIdToName.containsKey(parts[1]) && isGradeBPlusOrBetter(parts[4])) {
                    qualifyingStudents.add(parts[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return qualifyingStudents;
    }

    private static boolean isGradeBPlusOrBetter(String grade) {
        List<String> qualifyingGrades = Arrays.asList("A+", "A", "A-", "B+");
        return qualifyingGrades.contains(grade);
    }
}
