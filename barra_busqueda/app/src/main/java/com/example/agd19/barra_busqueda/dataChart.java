package com.example.agd19.barra_busqueda;

/**
 * Created by agd19 on 20/04/2018.
 */

public class dataChart {
    private String date, high, low, open, close, volume, quoteVolume, weightedAverage;

    public dataChart()
    {
        date = "";
        high = "";
        low = "";
        open = "";
        close = "";
        volume = "";
        quoteVolume = "";
        weightedAverage = "";
    }

    public dataChart(String date, String high, String low, String open , String close, String volume, String quoteVolume, String weightedAverage)
    {
        this.date = date;
        this.high = high;
        this.low = low;
        this.open = open;
        this.close = close;
        this.volume = volume;
        this.quoteVolume = quoteVolume;
        this.weightedAverage = weightedAverage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getQuoteVolume() {
        return quoteVolume;
    }

    public void setQuoteVolume(String quoteVolume) {
        this.quoteVolume = quoteVolume;
    }

    public String getWeightedAverage() {
        return weightedAverage;
    }

    public void setWeightedAverage(String weightedAverage) {
        this.weightedAverage = weightedAverage;
    }
}
