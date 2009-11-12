package uk.co.grahamcox.spring.cli;

import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.Test;

import uk.co.grahamcox.spring.cli.runners.ArgsRunner;
import uk.co.grahamcox.spring.cli.runners.DefaultRunner;
import uk.co.grahamcox.spring.cli.runners.SecondRunner;

public class RunnerTest {
	@Test
	public void testDefaultRunner() {
		Properties props = new Properties();
		props.setProperty("context.1", "classpath:/runnerTest.xml");
		CliRunner runner = new CliRunner();
		runner.addProperties(props);
		DefaultRunner.runCount = 0;
		SecondRunner.runCount = 0;
		assertEquals(0, DefaultRunner.runCount);
		assertEquals(0, SecondRunner.runCount);
		runner.run();
		assertEquals(1, DefaultRunner.runCount);
		assertEquals(0, SecondRunner.runCount);
	}
	@Test
	public void testNonDefaultRunner() {
		Properties props = new Properties();
		props.setProperty("context.1", "classpath:/runnerTest.xml");
		props.setProperty("runner", "runnerTwo");
		CliRunner runner = new CliRunner();
		runner.addProperties(props);
		DefaultRunner.runCount = 0;
		SecondRunner.runCount = 0;
		assertEquals(0, DefaultRunner.runCount);
		assertEquals(0, SecondRunner.runCount);
		runner.run();
		assertEquals(0, DefaultRunner.runCount);
		assertEquals(1, SecondRunner.runCount);
	}
	@Test
	public void testArgsRunner() {
		Properties props = new Properties();
		props.setProperty("context.1", "classpath:/runnerTest.xml");
		props.setProperty("runner", "argsRunner");
		CliRunner runner = new CliRunner();
		runner.addProperties(props);
		runner.setArguments(new String[]{"a", "b", "c"});
		runner.run();
		assertArrayEquals(new String[]{"a", "b", "c"}, ArgsRunner.args);
	}
	@Test
	public void testThrowingRunner() {
		Properties props = new Properties();
		props.setProperty("context.1", "classpath:/runnerTest.xml");
		props.setProperty("runner", "throwingRunner");
		CliRunner runner = new CliRunner();
		runner.addProperties(props);
		try {
			runner.run();
			fail("Expected RuntimeException");
		} catch (final RuntimeException e) {
			// Expected
		}
	}
}
