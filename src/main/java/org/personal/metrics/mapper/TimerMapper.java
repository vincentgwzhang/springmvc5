package org.personal.metrics.mapper;

import com.codahale.metrics.Snapshot;
import com.codahale.metrics.Timer;

public class TimerMapper {

    public static String map(String name, Timer timer) {
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(name);
        stringBuffer.append(" ");

        Snapshot snapshot = timer.getSnapshot();
        stringBuffer.append("50-percentile=").append(snapshot.getMedian()).append(",");
        stringBuffer.append("75-percentile=").append(snapshot.get75thPercentile()).append(",");
        stringBuffer.append("95-percentile=").append(snapshot.get95thPercentile()).append(",");
        stringBuffer.append("99-percentile=").append(snapshot.get99thPercentile()).append(",");
        stringBuffer.append("999-percentile=").append(snapshot.get999thPercentile()).append(",");
        stringBuffer.append("max=").append(snapshot.getMax()).append(",");
        stringBuffer.append("mean=").append(snapshot.getMean()).append(",");
        stringBuffer.append("min=").append(snapshot.getMin()).append(",");
        stringBuffer.append("std-dev=").append(snapshot.getStdDev()).append(",");

        stringBuffer.append("count=").append(timer.getCount()).append("i,");
        stringBuffer.append("fifteen-minute=").append(timer.getFifteenMinuteRate()).append(",");
        stringBuffer.append("five-minute=").append(timer.getFiveMinuteRate()).append(",");
        stringBuffer.append("mean-minute=").append(timer.getMeanRate()).append(",");
        stringBuffer.append("one-minute=").append(timer.getOneMinuteRate());

        return stringBuffer.toString();
    }

}
