package uk.co.grahamcox.spring.cli.runners;

import java.util.List;

public class ArgsRunner implements Runnable {
	public static String[] args = new String[0];
	
	private List<String> arguments;
	public void setArguments(final List<String> args) {
		this.arguments = args;
	}
	@Override
	public void run() {
		args = arguments.toArray(new String[0]);
	}

}
