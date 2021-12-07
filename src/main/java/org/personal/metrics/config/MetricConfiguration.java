package org.personal.metrics.config;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.RatioGauge;
import com.codahale.metrics.Timer;
import com.codahale.metrics.jmx.JmxReporter;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import org.personal.metrics.reporter.InfluxDBReporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Meter 纯粹是统计总点击数，平均每秒点击数，1 分钟平均每秒点击数，5 分钟平均每秒点击数，15 分钟平均每秒点击数
 * Histogram 是平常统计学里面的统计最大数，最小数，总点击数
 * Counter 的本质其实就是 AutomicLong, 最纯粹的增加减少
 * Timer   的本质其实就是 Meter 和 Histogram 的结合，包括了 Meter 和 Histogram 的所有统计, 就是计算耗时的
 * Gauge   的本质就是每次显示 getValue(), 而 RatioGauge 传统来说就是显示两个数之间的比例，例如 Kafka 里面还剩下的 message
 *         没有消耗完比总共消费完的 message 之间的比例，但是通常也可以做 customize, 因为 T getValue(), 而 RatioGauge 只是
 *         double getValue(), 所以只要集成 Gauge, 就可以 "任意Object getValue"
 */
@Configuration
public class MetricConfiguration {

    @Bean
    public MetricRegistry metricRegistry() {
        return new MetricRegistry();
    }

    @Bean
    public Meter meter1(MetricRegistry metrics) {
        return metrics.meter("MetricStatisticsController::callFunctionA:clicked");
    }

    @Bean
    public Meter meter2(MetricRegistry metrics) {
        return metrics.meter("MetricStatisticsController::callFunctionB:clicked");
    }

    @Bean
    public Histogram histogram(MetricRegistry metrics) {
        return metrics.histogram("statistics");
    }

    @Bean
    public Counter counter(MetricRegistry metrics) {
        return metrics.counter("requestCount");
    }

    @Bean
    public Timer timer(MetricRegistry metrics) {
        return metrics.timer("executeTime");
    }

    @Bean
    public CompareClick compareClick(MetricRegistry metrics) {
        CompareClick compareClick = new CompareClick();
        metrics.register("compareClick", compareClick);
        return compareClick;
    }

    //@Bean
    public ConsoleReporter consoleReporter(MetricRegistry metricRegistry) {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metricRegistry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(10, TimeUnit.SECONDS);
        return reporter;
    }

    @Bean
    public WriteApiBlocking writeApi() {
        InfluxDBClient influxDBClient = InfluxDBClientFactory.create();
        return influxDBClient.getWriteApiBlocking();
    }

    @Bean
    public InfluxDBReporter influxDBReporter(MetricRegistry metricRegistry, WriteApiBlocking writeApi) {
        InfluxDBReporter reporter = InfluxDBReporter.forRegistry(metricRegistry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .writerAPI(writeApi)
                .build();
        reporter.start(10, TimeUnit.SECONDS);
        return reporter;
    }

    @Bean
    public JmxReporter jmxReporter(MetricRegistry metrics) {
        final JmxReporter reporter = JmxReporter.forRegistry(metrics).build();
        reporter.start();
        return reporter;
    }

}
