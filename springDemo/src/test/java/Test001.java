import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class Test001 {

	@Autowired
	private Environment environment;

	@Test
	public void test001() {
		System.out.println(environment.getProperty("application"));
	}
}
