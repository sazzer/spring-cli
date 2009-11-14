package uk.co.grahamcox.spring.cli.options;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to place on a setter method to wire a command line option into
 * @author graham
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WireOption {
	/** The name of the command line option */
	String value();
}
