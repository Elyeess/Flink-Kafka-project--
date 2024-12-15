package org.example;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class Main {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<String> kafkaStream = env.addSource(FlinkCryptoConsumer.createConsumer(
                "crypto_prices",
                "localhost:9092"
        ));

        DataStream<String> resultStream = FlinkSlidingWindow.applySlidingWindow(kafkaStream);

        resultStream.print();

        env.execute("Flink Crypto Prices Processor");
    }
}
