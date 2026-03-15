package task1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GradeTracker tracker = new GradeTracker();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        System.out.println("_______________________________________________");
        System.out.println("\nSTUDENT GRADE TRACKER SYSTEM");
        System.out.println("_______________________________________________");
        
        while (running) {
            displayMenu();
            System.out.print("Enter your choice (1-7): ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    tracker.addStudent();
                    break;
                case "2":
                    tracker.displayAllStudents();
                    break;
                case "3":
                    tracker.updateGrade();
                    break;
                case "4":
                    tracker.displayStatistics();
                    break;
                case "5":
                    tracker.searchStudent();
                    break;
                case "6":
                    tracker.removeStudent();
                    break;
                case "7":
                    System.out.println("\nThank you for using Student Grade Tracker!");
                    System.out.println("Goodbye! 👋");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice! Please enter a number between 1 and 7.");
            }
            
            if (running && !choice.equals("7")) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    private static void displayMenu() {
        System.out.println("_______________________________________________");
    	System.out.println("\n      **** MAIN MENU ****      ");
        System.out.println("1. Add New Student");
        System.out.println("2. View All Students");
        System.out.println("3. Update Student Grade");
        System.out.println("4. View Statistics");
        System.out.println("5. Search Student");
        System.out.println("6. Remove Student");
        System.out.println("7. Exit");
        System.out.println("_______________________________________________");
    }
}