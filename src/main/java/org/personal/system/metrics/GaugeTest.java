package org.personal.system.metrics;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.slf4j.LoggerFactory;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.CsvReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;

public class GaugeTest {

    public static List<String> q = new LinkedList<>();

    /**
     * com.codahale.metrics.Gauge     :Refect exactly a number
     * com.codahale.metrics.Counter   :计数器，增加，或者减少，都算在里面,是个类。
     * com.codahale.metrics.Histogram :A metric which calculates the distribution of a value.
     * com.codahale.metrics.Meter     :measures mean throughput and one-, five-, and fifteen-minute
     * com.codahale.metrics.Timer     :A timer metric which aggregates timing durations and provides duration statistics, plus throughput statistics
     *
     * com.codahale.metrics.MetricRegistry
     * com.codahale.metrics.MetricSet
     *
     * 4 reports:
     * com.codahale.metrics.ConsoleReporter
     * com.codahale.metrics.CsvReporter
     * com.codahale.metrics.jmx.JmxReporter
     * com.codahale.metrics.Slf4jReporter
     */
    public static void main(String[] args) throws InterruptedException {
        MetricRegistry registry = new MetricRegistry();

        ConsoleReporter reporter = ConsoleReporter.forRegistry(registry).build();
        reporter.start(1, TimeUnit.SECONDS);

        final CsvReporter csvReporter = CsvReporter.forRegistry(registry).formatFor(Locale.US).convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).build(new File("/home/vzhang/Downloads/"));
        csvReporter.start(1, TimeUnit.SECONDS);

        final Slf4jReporter slf4jReporter = Slf4jReporter.forRegistry(registry).outputTo(LoggerFactory.getLogger("com.example.metrics")).convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).build();
        slf4jReporter.start(1, TimeUnit.MINUTES);

        registry.register(MetricRegistry.name(GaugeTest.class, "queue", "size"), (Gauge<Integer>) () -> q.size());

        while(true){
            Thread.sleep(1000);
            q.add("Job-xxx");
        }
    }
}
