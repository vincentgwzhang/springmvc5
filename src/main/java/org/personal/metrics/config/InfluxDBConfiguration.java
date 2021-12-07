package org.personal.metrics.config;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfluxDBConfiguration {

    @Bean
    public WriteApiBlocking writeApi() {
        InfluxDBClient influxDBClient = InfluxDBClientFactory.create();
        return influxDBClient.getWriteApiBlocking();
    }
}
