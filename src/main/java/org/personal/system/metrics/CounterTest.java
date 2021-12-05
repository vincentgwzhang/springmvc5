package org.personal.system.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CounterTest {

    public static Counter pendingJobs;

    public static Random random = new Random();

    public static void addJob() {
        pendingJobs.inc();
    }

    public static void takeJob() {
        pendingJobs.dec();
    }

    public static void main(String[] args) throws InterruptedException {
        MetricRegistry registry = new MetricRegistry();
        ConsoleReporter reporter = ConsoleReporter.forRegistry(registry).build();
        reporter.start(3, TimeUnit.SECONDS);

        pendingJobs = registry.counter(MetricRegistry.name(Queue.class,"pending-jobs","size"));

        for (int i = 0; i < 10; i++)
            addJob();
        for (int i = 0; i < 5; i++)
            takeJob();
        Thread.sleep(60 * 60 * 1000);
    }
}
