import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Map<String, Integer> enrollments = new HashMap<>();
        List<String> courseOrder = new ArrayList<>();
        List<String> checkResults = new ArrayList<>();

        int rejectedOperations = 0;

        try {
            File file = new File("enrollment.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(" ");
                String action = parts[0];
                String courseCode = parts[1];

                if (action.equals("CHECK")) {
                    if (enrollments.containsKey(courseCode)) {
                        checkResults.add(courseCode + ": " + enrollments.get(courseCode) + " students");
                    } else {
                        checkResults.add(courseCode + ": Not found");
                    }
                } else {
                    int count = Integer.parseInt(parts[2]);

                    if (count <= 0) {
                        rejectedOperations++;
                        continue;
                    }

                    if (action.equals("REGISTER")) {
                        if (!enrollments.containsKey(courseCode)) {
                            enrollments.put(courseCode, count);
                            courseOrder.add(courseCode);
                        } else {
                            enrollments.put(courseCode, enrollments.get(courseCode) + count);
                        }
                    } else if (action.equals("WITHDRAW")) {
                        if (enrollments.containsKey(courseCode) && enrollments.get(courseCode) >= count) {
                            enrollments.put(courseCode, enrollments.get(courseCode) - count);
                        } else {
                            rejectedOperations++;
                        }
                    }
                }
            }
            scanner.close();

            System.out.println("===== Enrollment Checks");
            for (String result : checkResults) {
                System.out.println(result);
            }

            System.out.println("===== Final Enrollment =====");
            for (String course : courseOrder) {
                System.out.println(course + ": " + enrollments.get(course) + " students");
            }

            System.out.println("Rejected operations: " + rejectedOperations);

        } catch (FileNotFoundException e) {
            System.out.println("File enrollment.txt tidak ditemukan.");
        }
    }
}