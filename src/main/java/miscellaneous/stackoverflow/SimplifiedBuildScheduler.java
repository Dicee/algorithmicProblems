package miscellaneous.stackoverflow;

import java.util.Random;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

// http://stackoverflow.com/questions/42053157/synchronous-task-producer-consumer-using-threadpoolexecutor/42451191#42451191
public class SimplifiedBuildScheduler {
    private static final int MAX_POOL_SIZE = 10;

    private static final Random random = new Random();
    private static final AtomicLong nextTaskId = new AtomicLong(0);

    public static void main(String[] args) throws InterruptedException {
        SynchronousQueue<Runnable> queue = new SynchronousQueue<>();

        // this is a soft requirement in my system, not a real-time guarantee. See the complete semantics in my question.
        long maxBuildTimeInMillis = 50;
        // this timeout must be small compared to maxBuildTimeInMillis in order to accurately match the maximum build time
        long taskSubmissionTimeoutInMillis = 1;

        ThreadPoolExecutor pool = new ThreadPoolExecutor(MAX_POOL_SIZE, MAX_POOL_SIZE, 0, SECONDS, queue);
        pool.prestartAllCoreThreads();

        Runnable nextTask = makeTask(maxBuildTimeInMillis);

        long millisAtStart = System.currentTimeMillis();
        while (maxBuildTimeInMillis > System.currentTimeMillis() - millisAtStart) {
            boolean submitted = queue.offer(nextTask, taskSubmissionTimeoutInMillis, MILLISECONDS);
            if (submitted) {
                nextTask = makeTask(maxBuildTimeInMillis);
            } else {
                System.out.println("Task " + nextTaskId.get() + " was not submitted. " + "It will be rescheduled unless " +
                        "the max build time has expired");
            }
        }

        System.out.println("Max build time has expired. Stop submitting new tasks and running existing tasks to completion");

        pool.shutdown();
        pool.awaitTermination(9999999, SECONDS);
    }

    private static Runnable makeTask(long maxBuildTimeInMillis) {
        long sleepTimeInMillis = randomSleepTime(maxBuildTimeInMillis);
        long taskId = nextTaskId.getAndIncrement();
        return () -> {
            try {
                System.out.println("Task " + taskId + " sleeping for " + sleepTimeInMillis + " ms");
                Thread.sleep(sleepTimeInMillis);
                System.out.println("Task " + taskId + " completed !");
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    private static int randomSleepTime(long maxBuildTimeInMillis) {
        // voluntarily make it possible that a task finishes after the max build time is expired
        return 1 + random.nextInt(2 * Math.toIntExact(maxBuildTimeInMillis));
    }
}