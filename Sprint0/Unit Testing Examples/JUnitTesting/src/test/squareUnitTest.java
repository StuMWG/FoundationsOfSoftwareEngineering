package test;

import static org.junit.Assert.*;

import org.junit.Test;

public class squareUnitTest {
	
	@Test
	public void test() {
		int[] array = {10,20,30,40};
		UnitTesting test = new UnitTesting();
		int output = test.average(array);
		
		//testing the output
		assertEquals(25,output);
	}

}
