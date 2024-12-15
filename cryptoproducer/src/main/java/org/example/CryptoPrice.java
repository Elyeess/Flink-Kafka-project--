package org.example;

public class CryptoPrice {
    private String name;
    private double price;
    private double volume;
    private long timestamp;

    public CryptoPrice(String name, double price, double volume, long timestamp) {
        this.name = name;
        this.price = price;
        this.volume = volume;
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getVolume() {
        return volume;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("CryptoPrice{name='%s', price=%f, volume=%f, timestamp=%d}",
                name, price, volume, timestamp);
    }
}
