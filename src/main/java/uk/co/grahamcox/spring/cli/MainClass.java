package uk.co.grahamcox.spring.cli;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * The main class that is run
 * @author graham
 *
 */
public class MainClass {
	/** The logger to use */
	private static final Log LOG = LogFactory.getLog(MainClass.class);

	/** The system property that overrides the default properties file */
	private static final String PROPERTIES_KEY = "properties";
	
	/** The name of the properties file that specifies config to use */
	private static final String PROPERTIES_FILE = "/spring-cli.properties";
	/**
	 * The main routine for the app
	 * @param args the arguments
	 * @throws Exception if any errors occur
	 */
	public static void main(final String[] args) throws Exception {
		Resource properties = new ClassPathResource(System.getProperty(PROPERTIES_KEY, PROPERTIES_FILE));
		LOG.info("Loading properties from file: " + properties.getFilename());
		Properties props = new Properties();
		props.load(properties.getInputStream());
		CliRunner runner = new CliRunner();
		runner.setArguments(args);
		runner.addProperties(props);
		runner.run();
	}
}
