package concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPool {
	private static AtomicInteger countDownLatch = new AtomicInteger(0);
	private static ScheduledExecutorService scheduledExecutorService;
	private long delay;
	private TimeUnit timeUnit;

	public ThreadPool(int corePoolSize, long delay, TimeUnit timeUnit) {
		scheduledExecutorService = Executors.newScheduledThreadPool(corePoolSize);
		this.delay = delay;
		this.timeUnit = timeUnit;
	}

	public void execute(Runnable command) {
		countDownLatch.incrementAndGet();
		scheduledExecutorService.schedule(command, delay, timeUnit);
	}

	public void shutdown() {
		while (countDownLatch.get() > 0) {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		scheduledExecutorService.shutdownNow();
	}

	public static void countDown() {
		countDownLatch.decrementAndGet();
	}

}
