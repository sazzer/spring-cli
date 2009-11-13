package uk.co.grahamcox.spring.cli.runners;

import uk.co.grahamcox.spring.cli.Arguments;

public class ArgsRunner implements Runnable {
	public static String[] args = new String[0];
	
	private Arguments arguments;
	public void setArguments(final Arguments args) {
		this.arguments = args;
	}
	@Override
	public void run() {
		args = arguments.getArguments();
	}

}
