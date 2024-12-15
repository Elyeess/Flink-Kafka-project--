package org.example;

import org.apache.flink.api.common.functions.AggregateFunction;

public class AveragePriceAggregator implements AggregateFunction<String, AverageAccumulator, String> {

    @Override
    public AverageAccumulator createAccumulator() {
        return new AverageAccumulator();
    }

    @Override
    public AverageAccumulator add(String value, AverageAccumulator accumulator) {
        double price = Double.parseDouble(value.split(",")[1]); // Extraire "price"
        accumulator.sum += price;
        accumulator.count++;
        return accumulator;
    }

    @Override
    public String getResult(AverageAccumulator accumulator) {
        return "Prix moyen : " + (accumulator.sum / accumulator.count);
    }

    @Override
    public AverageAccumulator merge(AverageAccumulator a, AverageAccumulator b) {
        a.sum += b.sum;
        a.count += b.count;
        return a;
    }
}

class AverageAccumulator {
    double sum = 0;
    int count = 0;
}
