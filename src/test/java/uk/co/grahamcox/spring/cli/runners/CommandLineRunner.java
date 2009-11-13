package uk.co.grahamcox.spring.cli.runners;

import org.apache.commons.cli.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;

public class CommandLineRunner implements Runnable {
	public static CommandLine commandLine;
	
	@Autowired
	private CommandLine cl;
	
	@Override
	public void run() {
		commandLine = cl;
	}
}
