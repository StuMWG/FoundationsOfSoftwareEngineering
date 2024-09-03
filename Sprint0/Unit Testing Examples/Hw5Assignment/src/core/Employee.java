package core;

public class Employee {
	private String employeeId;
	private String employeeName;
	private float salary;
	
	// default constructor
	public Employee() {
		super();
	}
	
	// constructor
	public void Employee(String employeeId, String employeeName, float salary) {
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.salary = salary;
	}
	
	// gets the employee id
	public String getEmployeeId() {
		return employeeId;
	}
	
	// sets the employee id with provided parameter
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	
	// gets the employee name
	public String getEmployeeName() {
		return employeeName;
	}
	
	// sets the employee name with the provided parameter
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	// gets the employee salary
	public float getSalary() {
		return salary;
	}
	
	// sets the employee salary with the provided parameter
	public void setSalary(float salary) {
		this.salary = salary;
	}
	
	// returns a string showing all relevant information to the employee
	public String toString() {
		String newString;
		newString = "Employee ID: " + this.employeeId + ", Employee Name: " + this.employeeName + ", Salary: " + this.salary;
		return newString;
	}
}
