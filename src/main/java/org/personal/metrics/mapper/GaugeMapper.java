package org.personal.metrics.mapper;

import com.codahale.metrics.Gauge;
import org.apache.commons.lang3.StringUtils;

public class GaugeMapper {

    public static String map(String name, Gauge gauge) {
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(name);
        stringBuffer.append(" ");

        Double value = (Double)gauge.getValue();

        if (!Double.isNaN(value)) {
            stringBuffer.append("value=").append(gauge.getValue());
        } else {
            return StringUtils.EMPTY;
        }

        return stringBuffer.toString();
    }
}
