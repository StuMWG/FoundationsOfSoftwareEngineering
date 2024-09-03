import static org.junit.Assert.*;
import org.junit.Test;

import core.Area;

public class TestArea {

	@Test
	public void test() {
		Area unit = new Area();
		
		double output = Area.Area(1);
		
		assertEquals(3.14159, output, 0.001);
	}

}
