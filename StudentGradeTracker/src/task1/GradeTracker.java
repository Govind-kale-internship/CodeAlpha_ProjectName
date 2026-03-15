package task1;

import java.util.ArrayList;
import java.util.Scanner;

public class GradeTracker 
{
	private ArrayList<Student> students;
	private Scanner sc;
	
	public GradeTracker()
	{
		students = new ArrayList<>();
		sc = new Scanner(System.in);
	}
	
	public void addStudent()
	{
		System.out.println("||          ** Add New Student **         ||");
		System.out.println("Enter Student Id : ");
		String id = sc.nextLine();
		
		if(findStudentById(id) != null)
		{
			System.out.println("Student with this ID already exists!");
			return;
		}
		
		System.out.println("Enter Student Name : ");
		String name = sc.nextLine();
		
		double grade = 0;
		boolean validGrade = false;
		while(!validGrade)
		{
			try
			{
				System.out.println("Enter Grade (0 - 100) : ");
				grade = Double.parseDouble(sc.nextLine());
				
				if(grade >= 0 && grade <= 100)
				{
					validGrade = true;
				}
				else
				{
					System.out.println("Grade must be between 0 and 100!");
				}
			}
			catch(NumberFormatException e)
			{
				System.out.println("Invalid input! Please enter a number.");
			}		
		}
		
		students.add(new Student(id, name, grade));
		System.out.println("Student added successfully");
	}
	
	private Student findStudentById(String id)
	{
		for(Student student : students)
		{
			if(student.getId().equals(id))
			{
				return student;
			}
		}
		return null;
		
	}
	
	public void updateGrade()
	{
		if(students.isEmpty())
		{
			System.out.println("No students in the system! please add student.");
			return;
		}
		
		System.out.println("Update Student Grade");
		System.out.println("Enter Student ID : ");
		String id = sc.nextLine();
		
		Student student = findStudentById(id);
		
		if(student == null)
		{
			System.out.println("Student not found!");
			return;
		}
		
		System.out.println("Current grade for " + student.getName() + ": " + student.getGrade());
		
		double newGrade = 0;
		boolean validGrade = false;
		while(!validGrade)
		{
			try
			{
				System.out.println("Enter new Grade (0 - 100) : ");
				newGrade = Double.parseDouble(sc.nextLine());
				
				if(newGrade >= 0 && newGrade <= 100)
				{
					validGrade = true;
				}
				else
				{
					System.out.println("Grade must be between 0 and 100! : ");
				}
			}
			catch(NumberFormatException e)
			{
				System.out.println("Invalid input! Please enter a number.");
			}
		}
		
		student.setGrade(newGrade);
		System.out.println("Grade updated successfully");
	}
	
	public void displayStatistics()
	{
		if(students.isEmpty())
		{
			System.out.println("No students in the system to calculate statistics!");
			return;
		}
		
		double sum = 0;
		double highest = Double.MIN_VALUE;
		double lowest = Double.MAX_VALUE;
		
		Student highestStudent = null;
		Student lowestStudent = null;
		
		for(Student student : students)
		{
			double grade = student.getGrade();
			sum += grade;
			
			if(grade > highest)
			{
				highest = grade;
				highestStudent = student;
			}
			
			if(grade < lowest)
			{
				lowest = grade;
				lowestStudent = student;
			}
		}
		
		double average = sum / students.size();
		
		System.out.println("    **** Grade Statistics ****  ");
		System.out.printf("Average Grade: %.2f\n", average);
        System.out.printf("Highest Grade: %.2f (Student: %s)\n", highest, highestStudent.getName());
        System.out.printf("Lowest Grade: %.2f (Student: %s)\n", lowest, lowestStudent.getName());
        
        System.out.println("___________________________________________________________________");
		
	}
	
	public void displayAllStudents()
	{
		if(students.isEmpty())
		{
			System.out.println("No students in the system!");
			return;
		}
		System.out.println("\n   **** All Students ****  ");
		
		for(int i = 0; i < students.size(); i++)
		{
			System.out.println((i + 1) + ". " + students.get(i));
		}
		
		System.out.println("Total Students: " + students.size());
		
		System.out.println("_________________________________________________");
		
	}
	
	public void searchStudent()
	{
		 System.out.println("\nEnter Student ID to search: ");
		 String id = sc.nextLine();
		 
		 Student student = findStudentById(id);
		 
		 if(student != null)
		 {
			 System.out.println("\nStudent Found : ");
			 System.out.println(student);
		 
			 double grade = student.getGrade();
			 System.out.println("Status : ");
			 
			 if(grade >= 90) System.out.println("Excellent");
			 else if(grade >= 80) System.out.println("Very Good");
			 else if(grade >= 70) System.out.println("Good");
			 else if(grade >= 60) System.out.println("Satisfactory");
			 else if(grade >= 50) System.out.println("Passing");
			 else System.out.println("Needs Improvement");
		 }
		 else
		 {
			 System.out.println("Student not found!");
		 }
		
	}
	
	public void removeStudent()
	{
		if(students.isEmpty())
		{
			System.out.println("No students in the system!");
			return;
		}
		
		System.out.println("\nEnter Student ID to remove : ");
		String id = sc.nextLine();
		
		Student student = findStudentById(id);
		if(student != null)
		{
			students.remove(student);
			System.out.println("Student removed successfully!");
		}
		else
		{
			System.out.println("Student not found!");
		}
		
	}

}
