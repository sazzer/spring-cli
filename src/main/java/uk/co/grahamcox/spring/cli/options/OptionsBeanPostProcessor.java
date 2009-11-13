package uk.co.grahamcox.spring.cli.options;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
/**
 * Bean post processor for populating the {@link Options} object with all the {@link Option} objects in the context
 * @author graham
 *
 */
public class OptionsBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {
	/** The logger to use */
	private final Log LOG = LogFactory.getLog(getClass());

	/** The Application Context to use */
	private ApplicationContext applicationContext;
	
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
	 * Process the Options objects that are created to add any Option objects in the context to them
	 * @param bean The bean to process
	 * @param name The name of the bean
	 * @return The processed bean
	 */
	@Override
	public Object postProcessAfterInitialization(final Object bean, final String name)
			throws BeansException {
		if (bean instanceof Options) {
			LOG.info("Processing an Options bean: " + name);
			Options options = (Options)bean;
			for (String option : applicationContext.getBeanNamesForType(Option.class)) {
				Option opt = (Option)applicationContext.getBean(option);
				LOG.info("Adding option " + opt + " to options");
				options.addOption(opt);
			}
		}
		return bean;
	}

	/**
	 * Do nothing
	 */
	@Override
	public Object postProcessBeforeInitialization(final Object bean, final String name)
			throws BeansException {
		return bean;
	}

}
