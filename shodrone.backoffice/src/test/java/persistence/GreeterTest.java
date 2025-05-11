package persistence;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 A comment
*/
public class GreeterTest {

	@Test
	public void greeterSaysHello() {
		assertThat("Hello world!", containsString("Hello world!"));
	}

}
