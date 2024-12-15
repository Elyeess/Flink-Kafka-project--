package org.example;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;

public class FlinkSlidingWindow {

    public static SingleOutputStreamOperator<String> applySlidingWindow(DataStream<String> stream) {
        return stream
                .keyBy(message -> message.split(",")[0]) // Extraire la clé (nom de la crypto)
                .window(SlidingEventTimeWindows.of(Time.minutes(1), Time.seconds(30)))
                .aggregate(new AveragePriceAggregator());
    }
}
