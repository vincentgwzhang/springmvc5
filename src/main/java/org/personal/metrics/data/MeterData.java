package org.personal.metrics.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MeterData extends InfluxDBData {

    private long count;

    private double oneMinuteRate;

    private double fiveMinuteRate;

    private double fifteenMinuteRate;

    private double meanRate;

    @Builder
    public MeterData(String name, long count, double oneMinuteRate, double fiveMinuteRate, double fifteenMinuteRate, double meanRate) {
        super(name);
        this.count = count;
        this.oneMinuteRate = oneMinuteRate;
        this.fiveMinuteRate = fiveMinuteRate;
        this.fifteenMinuteRate = fifteenMinuteRate;
        this.meanRate = meanRate;
    }

}
