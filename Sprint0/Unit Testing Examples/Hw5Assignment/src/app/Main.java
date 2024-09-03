package app;

import javax.swing.JOptionPane;

import core.Area;
import core.Book;
import core.Car;
import core.Employee;
import core.Payroll;

public class Main {
	public static void main(String args[]) {
		/*
		// test code for exercise 1
		Book myBook = new Book();
		
		String title = JOptionPane.showInputDialog("Enter title: ");
		String author = JOptionPane.showInputDialog("Enter author: ");
		String publish = JOptionPane.showInputDialog("Enter publisher: ");
		String copiesSold = JOptionPane.showInputDialog("Enter copies sold: ");
		
		myBook.setTitle(title);
		myBook.setAuthor(author);
		myBook.setPublish(publish);
		myBook.setCopiesSold(Integer.parseInt(copiesSold));
		
		JOptionPane.showMessageDialog(null, "Title: " + myBook.getTitle() + "\nAuthor: " + myBook.getAuthor() + "\nPublisher: " + myBook.getPublish() + "\nCopies sold: " + myBook.getCopiesSold());
		*/
		
		/*
		//test code for exercise 2
		Employee newEmployee = new Employee();
		
		String employeeId = JOptionPane.showInputDialog("Enter employee ID: ");
		String employeeName = JOptionPane.showInputDialog("Enter employee name: ");
		String salary = JOptionPane.showInputDialog("Enter salary: ");
		
		newEmployee.setEmployeeId(employeeId);
		newEmployee.setEmployeeName(employeeName);
		newEmployee.setSalary(Float.parseFloat(salary));
		
		JOptionPane.showMessageDialog(null, newEmployee.toString());
		*/
		
		/*
		//test code for exercise 3
		Car myCar = new Car();
		String carModelYear = JOptionPane.showInputDialog("Enter the car model year: ");
		String make = JOptionPane.showInputDialog("Enter the make of the car: ");
		myCar.Car(Integer.parseInt(carModelYear), make);
		myCar.accelerate();
		JOptionPane.showMessageDialog(null, "The Speed is: " + myCar.getSpeed());
		myCar.brake();
		JOptionPane.showMessageDialog(null, "The speed is: " + myCar.getSpeed());
		*/
		
		/*
		//test code for exercise 4
		Area area = new Area();
		
		String radius = JOptionPane.showInputDialog("Enter radius: ");
		String length = JOptionPane.showInputDialog("Enter length: ");
		String width = JOptionPane.showInputDialog("Enter width: ");
		String height = JOptionPane.showInputDialog("Enter height:");
		
		JOptionPane.showMessageDialog(null, "Circle Area: " + area.Area(Double.parseDouble(radius)));
		JOptionPane.showMessageDialog(null, "Rectangle Area: " + area.Area(Double.parseDouble(length), Double.parseDouble(width)));
		JOptionPane.showMessageDialog(null, "Cylinder Area: " + area.Area(Float.parseFloat(radius), Float.parseFloat(height)));
		*/
		
		/*
		//test code for exercise 7
		Payroll employee = new Payroll();
		String name = JOptionPane.showInputDialog("Enter employee name: ");
		String employeeId = JOptionPane.showInputDialog("Enter employee id: ");
		String employeePayRate = JOptionPane.showInputDialog("Enter pay rate: ");
		String employeeHours = JOptionPane.showInputDialog("Hours worked: ");
		
		employee.Payroll(name, Integer.parseInt(employeeId));
		employee.setPayRate(Double.parseDouble(employeePayRate));
		employee.setHoursWorked(Double.parseDouble(employeeHours));
		
		JOptionPane.showMessageDialog(null, "ID: " + employee.getIdNumber() + "\nName: " + employee.getName() + "\nPay Rate: " + employee.getPayRate() + "\nHours: " + employee.getHoursWorked() + "\nGross pay: " + employee.getGrossPay());
		*/
		
		System.exit(0);
	}
}
