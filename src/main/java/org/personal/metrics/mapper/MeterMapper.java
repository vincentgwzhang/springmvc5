package org.personal.metrics.mapper;

import com.codahale.metrics.Meter;

public class MeterMapper {

    public static String map(String name, Meter meter) {
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(name);
        stringBuffer.append(" ");
        stringBuffer.append("count=").append(meter.getCount()).append("i,");
        stringBuffer.append("fifteen-minute=").append(meter.getFifteenMinuteRate()).append(",");
        stringBuffer.append("five-minute=").append(meter.getFiveMinuteRate()).append(",");
        stringBuffer.append("mean-minute=").append(meter.getMeanRate()).append(",");
        stringBuffer.append("one-minute=").append(meter.getOneMinuteRate());

        return stringBuffer.toString();
    }
}
