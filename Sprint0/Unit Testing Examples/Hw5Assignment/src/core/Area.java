package core;

public class Area {
	// overloaded function for a circles area
	public static double Area (double r) {
		return 3.14159 * r * r;
	}
	
	// overloaded function for a rectangles area
	public static double Area (double l, double w) {
		return l * w;
	}
	
	// overloaded function for a cylinders area
	public static double Area (float r, float h) {
		return 3.14159*r*r*h;
	}

}
