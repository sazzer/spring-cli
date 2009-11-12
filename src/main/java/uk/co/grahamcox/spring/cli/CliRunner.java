package uk.co.grahamcox.spring.cli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.StaticApplicationContext;
/**
 * The actual runner that does the work. This is seperated out from MainClass for unit testing purposes
 * @author graham
 *
 */
public class CliRunner {
	/** The logger to use */
	private final Log LOG = LogFactory.getLog(getClass());
	/** The default name of the runner to use */
	private static final String RUNNER_DEFAULT_NAME = "runner";

	/** The properties to use */
	private Properties properties = new Properties();
	/** The arguments to use */
	private String[] args = new String[0];
	/** The initial set of objects to define */
	private Map<String, Object> initialSet = new HashMap<String, Object>();
	
	public void setProperties(final Properties properties) {
		this.properties = properties;
	}
	
	public void setArguments(final String[] args) {
		this.args = args;
	}
	
	public void setInitialValue(final String name, final Object value) {
		initialSet.put(name, value);
	}
	
	/**
	 * Actually run the app
	 */
	public void run() {
		List<String> configFiles = new ArrayList<String>();
		Enumeration<Object> keys = properties.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement().toString();
			if (key.startsWith("context.") || "context".equals(key)) {
				String configFile = properties.getProperty(key);
				LOG.info("Adding config file: " + configFile);
				configFiles.add(configFile);
			}
		}
		StaticApplicationContext parentContext = new StaticApplicationContext();
		BeanDefinition argsBeanDefinition = 
			BeanDefinitionBuilder.rootBeanDefinition(Arrays.class, "asList")
								 .addConstructorArgValue(args)
								 .getBeanDefinition();
		parentContext.registerBeanDefinition("arguments", 
				argsBeanDefinition);
		parentContext.refresh();
		ApplicationContext context = new ClassPathXmlApplicationContext(configFiles.toArray(new String[0]),
				true,
				parentContext);
		
		String runnerName = properties.getProperty("runner", RUNNER_DEFAULT_NAME);
		LOG.info("Loading runner with name: " + runnerName);
		Runnable runner = null;
		if (context.containsBean(runnerName)) {
			runner = (Runnable)context.getBean(runnerName, Runnable.class);
		}
		if (runner == null) {
			LOG.error("Runner not found!");
		}
		else {
			LOG.info("Found runner: " + runner.getClass().getName());
			runner.run();
		}
	}
}
