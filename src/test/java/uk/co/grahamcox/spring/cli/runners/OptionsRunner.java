package uk.co.grahamcox.spring.cli.runners;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.springframework.beans.factory.annotation.Autowired;

public class OptionsRunner implements Runnable {
	public static Options options;
	
	@Autowired
	private Options opts;
	
	@Override
	public void run() {
		options = opts;
		HelpFormatter fmt = new HelpFormatter();
		fmt.printHelp("OptionsRunner", options);
	}
}
