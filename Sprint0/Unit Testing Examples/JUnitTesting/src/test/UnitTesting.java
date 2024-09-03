package test;

public class UnitTesting {
	public static void main(String[] args) {
		int[] array = {5, 10, 15, 20};
		
		System.out.print(average(array));	
	}
	public static int average(int[] array) {
		int total = 0;
		int count=0;
		for (int element : array) {
			total += element;
			count++;
		}
		return total/count;
	}
	
	
}
