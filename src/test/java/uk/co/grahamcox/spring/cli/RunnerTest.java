package uk.co.grahamcox.spring.cli;

import java.util.Properties;

import org.junit.Test;

public class RunnerTest {
	@Test
	public void testDefaultRunner() {
		Properties props = new Properties();
		props.setProperty("context.1", "classpath:/runnerTest.xml");
		CliRunner runner = new CliRunner();
		runner.setProperties(props);
		runner.run();
	}
	@Test
	public void testNonDefaultRunner() {
		
	}
}
