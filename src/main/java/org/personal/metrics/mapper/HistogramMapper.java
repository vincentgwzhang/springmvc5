package org.personal.metrics.mapper;

import com.codahale.metrics.Histogram;
import com.codahale.metrics.Snapshot;

public class HistogramMapper {

    public static String map(String name, Histogram histogram) {
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(name);
        stringBuffer.append(" ");

        Snapshot snapshot = histogram.getSnapshot();
        stringBuffer.append("50-percentile=").append(snapshot.getMedian()).append(",");
        stringBuffer.append("75-percentile=").append(snapshot.get75thPercentile()).append(",");
        stringBuffer.append("95-percentile=").append(snapshot.get95thPercentile()).append(",");
        stringBuffer.append("99-percentile=").append(snapshot.get99thPercentile()).append(",");
        stringBuffer.append("999-percentile=").append(snapshot.get999thPercentile()).append(",");
        stringBuffer.append("count=").append(histogram.getCount()).append("i,");
        stringBuffer.append("max=").append(snapshot.getMax()).append("i,");
        stringBuffer.append("mean=").append(snapshot.getMean()).append(",");
        stringBuffer.append("min=").append(snapshot.getMin()).append("i,");
        stringBuffer.append("std-dev=").append(snapshot.getStdDev()).append("");

        return stringBuffer.toString();
    }

}
