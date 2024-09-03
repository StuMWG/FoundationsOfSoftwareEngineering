package core;

public class Payroll {
	private String name;
	private int idNumber;
	private double payRate;
	private double hoursWorked;

	// constructor
	public void Payroll(String name, int id) {
		this.name = name;
		this.idNumber = id;
	}
	
	// sets name
	public void setName(String name) {
		this.name = name;
	}
	
	// sets id number
	public void setIdNumber(int idNum) {
		this.idNumber = idNum;
	}
	
	// sets pay rate
	public void setPayRate(double payRate) {
		this.payRate = payRate;
	}
	
	//sets hours worked
	public void setHoursWorked(double hoursWorked) {
		this.hoursWorked = hoursWorked;
	}
	
	// gets name
	public String getName() {
		return name;
	}
	
	// gets id number
	public int getIdNumber() {
		return idNumber;
	}
	
	// gets pay rate
	public double getPayRate() {
		return payRate;
	}
	
	// gets hours worked
	public double getHoursWorked() {
		return hoursWorked;
	}
	
	// calculates and returns gross pay
	public double getGrossPay() {
		return hoursWorked * payRate;
	}
}

