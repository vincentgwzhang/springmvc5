package org.personal.metrics.reporter;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ScheduledReporter;
import com.codahale.metrics.Timer;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.personal.metrics.mapper.CounterMapper;
import org.personal.metrics.mapper.GaugeMapper;
import org.personal.metrics.mapper.HistogramMapper;
import org.personal.metrics.mapper.MeterMapper;
import org.personal.metrics.mapper.TimerMapper;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.concurrent.TimeUnit;

@Slf4j
public class InfluxDBReporter extends ScheduledReporter {

    private static final String METER_TAB = "[Meter]";
    private static final String GUAGE_TAB = "[Gauge]";
    private static final String COUNTER_TAB = "[Counter]";
    private static final String HISTOGRAM_TAB = "[Histogram]";

    private final WriteApiBlocking writeApi;

    public static Builder forRegistry(MetricRegistry registry) {
        return new Builder(registry);
    }

    public static class Builder {
        private final MetricRegistry registry;
        private TimeUnit rateUnit;
        private TimeUnit durationUnit;
        private WriteApiBlocking writeApi;

        private Builder(MetricRegistry registry) {
            this.registry = registry;
            this.rateUnit = TimeUnit.SECONDS;
            this.durationUnit = TimeUnit.MILLISECONDS;
            this.writeApi = null;
        }

        public Builder convertRatesTo(TimeUnit rateUnit) {
            this.rateUnit = rateUnit;
            return this;
        }

        public Builder convertDurationsTo(TimeUnit durationUnit) {
            this.durationUnit = durationUnit;
            return this;
        }

        public Builder writerAPI(WriteApiBlocking writeApi) {
            this.writeApi = writeApi;
            return this;
        }

        public InfluxDBReporter build() {
            return new InfluxDBReporter(registry, writeApi, rateUnit, durationUnit);
        }
    }

    private InfluxDBReporter(MetricRegistry registry, WriteApiBlocking writeApi, TimeUnit rateUnit, TimeUnit durationUnit) {
        super(registry, "influxdb-reporter", MetricFilter.ALL, rateUnit, durationUnit, null, true, Collections.emptySet());
        this.writeApi = writeApi;
    }

    @Override
    public void report(SortedMap<String, Gauge> gauges, SortedMap<String, Counter> counters, SortedMap<String, Histogram> histograms, SortedMap<String, Meter> meters, SortedMap<String, Timer> timers) {
        sendMetersData(meters);
        sendGaugesData(gauges);
        sendCountersData(counters);
        sendHistogramsData(histograms);
        sendTimersData(timers);
    }

    private void sendTimersData(SortedMap<String, Timer> timers) {
        if (!CollectionUtils.isEmpty(timers)) {
            List<String> records = timers.entrySet().stream().map(entry -> TimerMapper.map(entry.getKey(), entry.getValue())).toList();
            sendMetricsData(records, HISTOGRAM_TAB);
        }
    }

    private void sendHistogramsData(SortedMap<String, Histogram> histograms) {
        if (!CollectionUtils.isEmpty(histograms)) {
            List<String> records = histograms.entrySet().stream().map(entry -> HistogramMapper.map(entry.getKey(), entry.getValue())).toList();
            sendMetricsData(records, HISTOGRAM_TAB);
        }
    }

    private void sendCountersData(SortedMap<String, Counter> counters) {
        if (!CollectionUtils.isEmpty(counters)) {
            List<String> records = counters.entrySet().stream().map(entry -> CounterMapper.map(entry.getKey(), entry.getValue())).toList();
            sendMetricsData(records, COUNTER_TAB);
        }
    }

    private void sendGaugesData(SortedMap<String, Gauge> gauges) {
        if (!CollectionUtils.isEmpty(gauges)) {
            List<String> records = gauges.entrySet().stream().map(entry -> GaugeMapper.map(entry.getKey(), entry.getValue())).toList();
            sendMetricsData(records, GUAGE_TAB);
        }
    }

    private void sendMetersData(SortedMap<String, Meter> meters) {
        if (!CollectionUtils.isEmpty(meters)) {
            List<String> records = meters.entrySet().stream().map(entry -> MeterMapper.map(entry.getKey(), entry.getValue())).toList();
            sendMetricsData(records, METER_TAB);
        }
    }

    private void sendMetricsData(List<String> records, String tabs) {
        if (!CollectionUtils.isEmpty(records)) {
            records.stream().filter(StringUtils::isNotEmpty).forEach(record -> {
                try {
                    writeApi.writeRecord(WritePrecision.NS, record);
                } catch (Exception e) {
                    log.warn("[InfluxDB][WriteAPI]{} write meter error, error message = {}", tabs, e.getMessage(), e);
                }
            });
        }
    }
}
