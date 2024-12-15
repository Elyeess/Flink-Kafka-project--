package org.example;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.json.JSONObject;

import java.util.Properties;

public class FlinkSlidingWindow {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties kafkaProps = new Properties();
        kafkaProps.setProperty("bootstrap.servers", "localhost:9092");
        kafkaProps.setProperty("group.id", "flink-group");

        FlinkKafkaConsumer<String> kafkaConsumer = new FlinkKafkaConsumer<>("crypto-prices", new SimpleStringSchema(), kafkaProps);

        DataStream<String> cryptoStream = env.addSource(kafkaConsumer);

        cryptoStream.map(value -> {
                    JSONObject json = new JSONObject(value);
                    return new CryptoPrice(
                            json.getString("symbol"),
                            json.getDouble("priceUsd"),
                            json.getDouble("volumeUsd24Hr"));
                })
                .keyBy(CryptoPrice::getSymbol)
                .window(TumblingEventTimeWindows.of(Time.seconds(10)))
                .aggregate(new MoyPrice())
                .print();

        env.execute("Flink Sliding Window");
    }
}
