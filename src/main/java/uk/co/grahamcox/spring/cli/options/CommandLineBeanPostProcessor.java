package uk.co.grahamcox.spring.cli.options;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
 * Bean post processor for creating the CommandLine object properly and for automatically wiring command line options into created beans
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
	 * This now does two things.
	 * If the {@link #arguments} and {@link #commandLineParser} objects are bound, and the bean is a {@link CommandLine} object
	 * then try to actually instantiate the command line from the {@link Options} object in the context, using the bound arguments and parser
	 * If this isn't the case and the bean has at least one method with the {@link WireOption} annotation then attempt to wire into that
	 * setter the value from the named command line option 
	 */
	@Override
	public Object postProcessBeforeInitialization(final Object bean, final String name)
			throws BeansException {
		Object result = bean;
        if (bean == null) {
            result = null;
        }
		else if (arguments != null &&
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
		else {
			CommandLine cl = null;
			for (Method met : bean.getClass().getMethods()) {
				WireOption wo = met.getAnnotation(WireOption.class);
				if (wo != null) {
					String[] clNames = applicationContext.getBeanNamesForType(CommandLine.class);
					cl = (CommandLine)applicationContext.getBean(clNames[0]);
					if (cl != null) {
						String option = wo.value();
						Class<?>[] paramTypes = met.getParameterTypes();
						Object[] params = new Object[paramTypes.length];
						for (int i = 0; i < paramTypes.length; ++i) {
							if (paramTypes[i].isAssignableFrom(boolean.class)) {
								params[i] = cl.hasOption(option);
							}
							else if (paramTypes[i].isAssignableFrom(String.class)) {
								params[i] = cl.getOptionValue(option);
							}
							else {
								throw new BeanCreationException("Unable to assign option value to setter. Parameters must be Boolean or String types");
							}
						}
						try {
							met.invoke(bean, params);
						} catch (IllegalArgumentException e) {
							throw new BeanCreationException("Illegal argument supplied to Option setter", e);
						} catch (IllegalAccessException e) {
							throw new BeanCreationException("Illegal access to Option setter", e);
						} catch (InvocationTargetException e) {
							throw new BeanCreationException("Error invoking Option setter", e);
						}
					}
				}
			}
		}
		return result;
	}

}
