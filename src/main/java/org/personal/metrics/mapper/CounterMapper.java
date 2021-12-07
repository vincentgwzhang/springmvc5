package org.personal.metrics.mapper;

import com.codahale.metrics.Counter;

public class CounterMapper {

    public static String map(String name, Counter counter) {
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(name);
        stringBuffer.append(" ");
        stringBuffer.append("count=").append(counter.getCount()).append("i");

        return stringBuffer.toString();
    }

}
