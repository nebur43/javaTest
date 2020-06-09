package pruebas.java_concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VolatileExample {

//	private static volatile int number;
//	private static volatile boolean ready;
	private static int number;
	private static boolean ready;

	private static class Reader implements Runnable{

		@Override
		public void run() {
			while (!ready) {
//				Thread.yield();
			}
			int n = 0;
			n = number;
			System.out.println(n);
		}
	}

	public static void main(String[] args) {
		ExecutorService service= Executors.newFixedThreadPool(500);
		for (int i=0;i<500;i++) {
			service.submit(new Reader());
		}
		
//		new Reader().start();
		number = 42;
		ready = true;
		service.shutdown();
	}

}
