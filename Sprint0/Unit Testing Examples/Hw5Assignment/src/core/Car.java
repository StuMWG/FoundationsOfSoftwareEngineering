package core;

public class Car {
	private int yearModel;
	private String make;
	private int speed;
	
	// default constructor and setting speed to zero
	public Car() {
		super();
		speed = 0;
	}
	
	// constructor
	public void Car(int y, String m) {
		this.yearModel = y;
		this.make = m;
	}
	
	// sets the model year of the car
	public void setYearModel(int y) {
		this.yearModel = y;
	}
	
	// sets the make of the car
	public void setMake(String m) {
		this.make = m;
	}
	
	// sets the speed of the car
	public void setSpeed(int s) {
		this.speed = s;
	}
	
	// gets the model year of the car
	public int getYearModel() {
		return yearModel;
	}
	
	// gets the make of the car
	public String getMake() {
		return make;
	}
	
	// gets the speed of the car
	public int getSpeed() {
		return speed;
	}
	
	// increases the speed of the car by 5
	public void accelerate() {
		this.speed = this.speed + 5;
	}
	
	// decreases the speed of the car by 5
	public void brake() {
		this.speed = this.speed - 5;
	}
}
