package task1;

public class Student 
{
	private String id;
	private String name;
	private double grade;
	
	public Student(String id, String name, double grade) {
		 
		this.id = id;
		this.name = name;
		this.grade = grade;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return String.format("ID: %s | Name: %-15s | Grade: %.2f", id, name, grade);
	}
	
	
	
	
}
