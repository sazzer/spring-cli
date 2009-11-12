package uk.co.grahamcox.spring.cli;

import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * The main class that is run
 * @author graham
 *
 */
public class MainClass {
	/** The name of the properties file that specifies config to use */
	private static final String PROPERTIES_FILE = "classpath:/spring-cli.properties";
	/**
	 * The main routine for the app
	 * @param args the arguments
	 * @throws Exception if any errors occur
	 */
	public static void main(final String[] args) throws Exception {
		Resource properties = new ClassPathResource(PROPERTIES_FILE);
		Properties props = new Properties();
		props.load(properties.getInputStream());
		CliRunner runner = new CliRunner();
		runner.setArguments(args);
		runner.setProperties(props);
		runner.run();
	}
}
