package uk.co.grahamcox.spring.cli.options;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import uk.co.grahamcox.spring.cli.Arguments;

/**
 * Bean post processor for creating the CommandLine object properly
 * @author graham
 *
 */
public class CommandLineBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {
	/** The Application Context to use */
	private ApplicationContext applicationContext;
	
	/** The command line parser to use */
	@Autowired(required=false)
	private CommandLineParser commandLineParser;
	
	/** The arguments to parse */
	@Autowired(required=true)
	private Arguments arguments;
	
	/**
	 * Set the Application Context
	 * @param context the context
	 */
	@Override
	public void setApplicationContext(final ApplicationContext context)
			throws BeansException {
		this.applicationContext = context;
	}
	/**
	 * Do nothing
	 */
	@Override
	public Object postProcessAfterInitialization(final Object bean, final String name)
			throws BeansException {
		return bean;
	}

	/**
	 * Parse the arguments with the parser, if possible
	 */
	@Override
	public Object postProcessBeforeInitialization(final Object bean, final String name)
			throws BeansException {
		Object result = bean;
		if (arguments != null &&
			commandLineParser != null &&
			result instanceof CommandLine) {
			String[] optionsNames = applicationContext.getBeanNamesForType(Options.class);
			Options options = (Options)applicationContext.getBean(optionsNames[0]);

			try {
				result = commandLineParser.parse(options, arguments.getArguments());
			} catch (ParseException e) {
				throw new BeanCreationException("Error parsing arguments", e);
			}
		}
		return result;
	}

}
