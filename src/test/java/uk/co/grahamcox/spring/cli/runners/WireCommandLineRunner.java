package uk.co.grahamcox.spring.cli.runners;

import uk.co.grahamcox.spring.cli.options.WireOption;

public class WireCommandLineRunner implements Runnable {
	public static Boolean hasFile;
	public static Boolean hasHelp;
	public static String file;
	
	@WireOption("h")
	public void setHasHelp(final boolean h) {
		hasHelp = h;
	}
	
	@WireOption("f")
	public void setHasFile(final boolean h) {
		hasFile = h;
	}
	
	@WireOption("f")
	public void setFile(final String h) {
		file = h;
	}
	@Override
	public void run() {
	}
}
