package org.example;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Properties;

public class FlinkCryptoConsumer {
    public static FlinkKafkaConsumer<String> createConsumer(String topic, String kafkaBootstrapServers) {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", kafkaBootstrapServers);
        properties.setProperty("group.id", "crypto-consumer-group");

        return new FlinkKafkaConsumer<>(topic, new SimpleStringSchema(), properties);
    }
}
