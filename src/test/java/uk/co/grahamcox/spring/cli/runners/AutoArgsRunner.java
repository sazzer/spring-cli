package uk.co.grahamcox.spring.cli.runners;

import org.springframework.beans.factory.annotation.Autowired;

import uk.co.grahamcox.spring.cli.Arguments;

public class AutoArgsRunner implements Runnable {
	public static String[] args = new String[0];
	
	@Autowired
	private Arguments arguments;

	@Override
	public void run() {
		args = arguments.getArguments();
	}

}
