package uk.co.grahamcox.spring.cli;

/**
 * Wrapper around the arguments to the application
 * @author graham
 *
 */
public class Arguments {
	/** The actual arguments */
	private final String[] args;
	
	/**
	 * Create the wrapper
	 * @param args the arguments
	 */
	public Arguments(final String[] args) {
		this.args = args;
	}
	
	/**
	 * Get the arguments themselves
	 * @return the arguments
	 */
	public String[] getArguments() {
		return args;
	}
	
	/**
	 * Get the number of arguments
	 * @return the number of arguments
	 */
	public int getNumberOfArguments() {
		return args.length;
	}
	
	/**
	 * Get a specific argument
	 * @param index the index of the argument to get
	 * @return the argument
	 */
	public String getArgument(final int index) {
		assert (index >= 0);
		return args[index];
	}
}
