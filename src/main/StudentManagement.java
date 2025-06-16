package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import model.Student;
import service.StudentService;
import service.StudentServiceImpl;

public class StudentManagement {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        //here we get username and password from admin.csv
        String[] info = loadAdminCredentials("src/Data/admin.csv");
        if (info == null) {
            System.out.println("Admin credentials file missing or invalid.");
        }

        //w ecreate d sa=tatci method and info[0] conatin username and info[1] conatian passowrd
        while (!adminLogin(sc, info[0], info[1])) {
            System.out.print("Do you want to try again? (yes/no): ");
            String response = sc.nextLine().trim().toLowerCase();
            if (!response.equals("yes")) {
                System.out.println("Exiting system.");
                return;
            }
        }

        StudentService service = new StudentServiceImpl();
        while (true) {
            System.out.println("\n===== Student Management Menu =====");
            System.out.println("1. Add Student");
            System.out.println("2. Delete Student");
            System.out.println("3. View Students");
            System.out.println("4. Search Student");
            System.out.println("5. Save to CSV");
            System.out.println("6. Load from CSV");
            System.out.println("7. Exit");
            System.out.print("Choose option: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                	//for add student
                    System.out.print("Enter ID: ");
                    int id = Integer.parseInt(sc.nextLine());
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Email: ");
                    String email = sc.nextLine();
                    Student s = new Student(id, name, email);
                    if (service.addStudent(s)) {
                        System.out.println("Student added successfully.");
                    } else {
                        System.out.println("Student already exists.");
                    }
                    break;

                case 2:
                	//delete the student by id
                    System.out.print("Enter Student ID to delete: ");
                    int deleteId = Integer.parseInt(sc.nextLine());
                    if (service.deleteStudent(deleteId)) {
                        System.out.println("Student deleted.");
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 3:
                    List<Student> all = service.getAllStudents();
                    if (all.isEmpty()) {
                        System.out.println("No students found.");
                    } else {
                        for (Student st : all) {
                            System.out.println(st);
                        }
                    }
                    break;

                case 4:
                    System.out.print("Enter name to search: ");
                    String searchName = sc.nextLine();
                    List<Student> results = service.searchStudentsByName(searchName);
                    if (results.isEmpty()) {
                        System.out.println("No student found with that name.");
                    } else {
                        for (Student st : results) {
                            System.out.println(st);
                        }
                    }
                    break;

                case 5:
                	//here we pass the string path file
                    service.saveToCSV("src/Data/student.csv");
                    break;

                case 6:
                    service.loadFromCSV("src/Data/student.csv");
                    break;

                case 7:
                    System.out.println("Exiting...");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    //this is used to read data from admin csv
    private static String[] loadAdminCredentials(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            if (line != null) {
            	String[] parts = line.split("\\t");
                if (parts.length == 2) {
                    return new String[]{parts[0].trim(), parts[1].trim()};
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading admin file: " + e.getMessage());
        }
        return null;
    }
    /// here this method to chekc username and password
    private static boolean adminLogin(Scanner sc, String adminUsername, String adminPassword) {
        System.out.println("=== Admin Login ===");
        System.out.print("Username: ");
        String user = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();

        if (user.equals(adminUsername) && pass.equals(adminPassword)) {
            System.out.println("Login successful!");
            return true;
        } else {
            System.out.println("Invalid username or password.");
            return false;
        }
    }
}
