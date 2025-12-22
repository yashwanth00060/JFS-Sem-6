package com.skillnext;

import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Scanner scanner = new Scanner(System.in);
        StudentDAO dao = new StudentDAO();

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1) Add student");
            System.out.println("2) Show all students");
            System.out.println("3) Update student");
            System.out.println("4) Delete student");
            System.out.println("0) Exit");
            System.out.print("Enter choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, try again.");
                continue;
            }

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Semester (int): ");
                        int sem = Integer.parseInt(scanner.nextLine().trim());
                        System.out.print("Department: ");
                        String dept = scanner.nextLine();
                        Student newStudent = new Student(name, sem, dept);
                        dao.AddStudent(newStudent);
                        System.out.println("Student added successfully.");
                        break;
                    case 2:
                        List<Student> students = dao.ShowAllStudents();
                        if (students.isEmpty()) {
                            System.out.println("No students found.");
                        } else {
                            students.forEach(System.out::println);
                        }
                        break;
                    case 3:
                        System.out.print("ID of student to update: ");
                        int upId = Integer.parseInt(scanner.nextLine().trim());
                        System.out.print("New name: ");
                        String upName = scanner.nextLine();
                        System.out.print("New semester (int): ");
                        int upSem = Integer.parseInt(scanner.nextLine().trim());
                        System.out.print("New department: ");
                        String upDept = scanner.nextLine();
                        Student upStudent = new Student(upName, upSem, upDept);
                        upStudent.setId(upId);
                        dao.UpdateStudent(upStudent);
                        System.out.println("Student updated successfully.");
                        break;
                    case 4:
                        System.out.print("ID of student to delete: ");
                        int delId = Integer.parseInt(scanner.nextLine().trim());
                        Student delStudent = new Student();
                        delStudent.setId(delId);
                        dao.DeleteStudent(delStudent);
                        System.out.println("Student deleted successfully.");
                        break;
                    case 0:
                        System.out.println("Exiting.");
                        return;
                    default:
                        System.out.println("Unknown choice, try again.");
                }
            } catch (Exception e) {
                System.err.println("Operation failed: " + e.getMessage());
            }
        }
    }
}
