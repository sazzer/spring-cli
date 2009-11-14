package uk.co.grahamcox.spring.cli;

import java.util.Properties;

import org.junit.Test;
import static org.junit.Assert.*;

import uk.co.grahamcox.spring.cli.runners.CommandLineRunner;
import uk.co.grahamcox.spring.cli.runners.OptionsRunner;
import uk.co.grahamcox.spring.cli.runners.WireCommandLineRunner;

public class OptionsTest {
	@Test
	public void testDefaultRunner() {
		Properties props = new Properties();
		props.setProperty("context.1", "classpath:/optionsTest.xml");
		CliRunner runner = new CliRunner();
		runner.addProperties(props);
		runner.run();
	}
	@Test
	public void testOptionsRunner() {
		Properties props = new Properties();
		props.setProperty("context.1", "classpath:/optionsTest.xml");
		props.setProperty("runner", "optionsRunner");
		CliRunner runner = new CliRunner();
		runner.addProperties(props);
		runner.run();
		assertTrue(OptionsRunner.options.hasOption("h"));
		assertFalse(OptionsRunner.options.hasOption("x"));
	}
	@Test
	public void testCommandLineRunner() {
		Properties props = new Properties();
		props.setProperty("context.1", "classpath:/optionsTest.xml");
		props.setProperty("runner", "commandLineRunner");
		CliRunner runner = new CliRunner();
		runner.addProperties(props);
		runner.setArguments(new String[]{"--file", "hello"});
		runner.run();
		assertFalse(CommandLineRunner.commandLine.hasOption("h"));
		assertEquals("hello", CommandLineRunner.commandLine.getOptionValue("f"));
	}
	@Test
	public void testWireCommandLineRunner() {
		Properties props = new Properties();
		props.setProperty("context.1", "classpath:/optionsTest.xml");
		props.setProperty("runner", "wireCommandLineRunner");
		CliRunner runner = new CliRunner();
		runner.addProperties(props);
		runner.setArguments(new String[]{"--file", "hello"});
		WireCommandLineRunner.file = null;
		WireCommandLineRunner.hasFile = null;
		WireCommandLineRunner.hasHelp = null;
		runner.run();
		assertEquals("hello", WireCommandLineRunner.file);
		assertEquals(true, WireCommandLineRunner.hasFile);
		assertEquals(false, WireCommandLineRunner.hasHelp);
	}
}
