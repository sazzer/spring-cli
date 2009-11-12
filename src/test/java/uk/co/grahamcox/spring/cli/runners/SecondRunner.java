package uk.co.grahamcox.spring.cli.runners;

public class SecondRunner implements Runnable {
	public static int runCount;
	
	@Override
	public void run() {
		runCount++;
	}

}
