package uk.co.grahamcox.spring.cli.runners;

public class ThrowingRunner implements Runnable {

	@Override
	public void run() {
		throw new RuntimeException("oops");
	}

}
