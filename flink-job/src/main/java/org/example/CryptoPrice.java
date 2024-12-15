package org.example;

public class CryptoPrice {
    private String symbol;
    private double priceUsd;
    private double volumeUsd24Hr;

    public CryptoPrice(String symbol, double priceUsd, double volumeUsd24Hr) {
        this.symbol = symbol;
        this.priceUsd = priceUsd;
        this.volumeUsd24Hr = volumeUsd24Hr;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPriceUsd() {
        return priceUsd;
    }

    public double getVolumeUsd24Hr() {
        return volumeUsd24Hr;
    }

    @Override
    public String toString() {
        return "CryptoPrice{" +
                "symbol='" + symbol + '\'' +
                ", priceUsd=" + priceUsd +
                ", volumeUsd24Hr=" + volumeUsd24Hr +
                '}';
    }
}
