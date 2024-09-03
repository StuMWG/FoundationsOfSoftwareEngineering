package test;

public class UnitTesting {
	public int average(int[] array) {
		int total = 0;
		int count=0;
		for (int element : array) {
			total += element;
			count++;
		}
		return total/count;
	}
}
