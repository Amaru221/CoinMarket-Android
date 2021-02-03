package com.example.agd19.barra_busqueda;

import java.io.Serializable;

/**
 * Created by agd19 on 08/03/2018.
 */

public class moneda implements Serializable {

    private String id, nombre, symbol, rank, price_usd, price_btc, volume_usd_24h, market_cap_usd, available_supply, total_supply, max_supply, percent_change_1h, percent_change_24h, percent_change_7d, last_updated, value, volume, marketcap;
    private Boolean like;

    public moneda()
    {
        id = "";
        nombre = "";
        symbol = "";
        rank = "";
        price_usd = "";
        price_btc = "";
        volume_usd_24h = "";
        market_cap_usd = "";
        available_supply = "";
        total_supply = "";
        max_supply = "";
        percent_change_1h = "";
        percent_change_24h = "";
        percent_change_7d = "";
        last_updated = "";
        like = false;
        value = "";
        volume = "";
        marketcap = "";
    }

    public moneda (String id, String nombre, String symbol, String rank, String price_usd, String price_btc, String volume_usd_24h, String market_cap_usd, String available_supply, String total_supply, String max_supply, String percent_change_1h, String percent_change_24h, String percent_change_7d, String last_updated, Boolean like, String value, String volume, String marketcap)
    {
        this.id = id;
        this.nombre = nombre;
        this.symbol = symbol;
        this.rank = rank;
        this.price_usd = price_usd;
        this.price_btc = price_btc;
        this.volume_usd_24h = volume_usd_24h;
        this.market_cap_usd = market_cap_usd;
        this.available_supply = available_supply;
        this.total_supply = total_supply;
        this.max_supply = max_supply;
        this.percent_change_1h = percent_change_1h;
        this.percent_change_24h = percent_change_24h;
        this.percent_change_7d = percent_change_7d;
        this.last_updated = last_updated;
        this.like = like;
        this.value = value;
        this.volume = volume;
        this.marketcap = marketcap;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getMarketcap() {
        return marketcap;
    }

    public void setMarketcap(String marketcap) {
        this.marketcap = marketcap;
    }

    public Boolean getLike() {
        return like;
    }

    public void setLike(Boolean like) {
        this.like = like;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getPrice_usd() {
        return price_usd;
    }

    public void setPrice_usd(String price_usd) {
        this.price_usd = price_usd;
    }

    public String getPrice_btc() {
        return price_btc;
    }

    public void setPrice_btc(String price_btc) {
        this.price_btc = price_btc;
    }

    public String getVolume_usd_24h() {
        return volume_usd_24h;
    }

    public void setVolume_usd_24h(String volume_usd_24h) {
        this.volume_usd_24h = volume_usd_24h;
    }

    public String getMarket_cap_usd() {
        return market_cap_usd;
    }

    public void setMarket_cap_usd(String market_cap_usd) {
        this.market_cap_usd = market_cap_usd;
    }

    public String getAvailable_supply() {
        return available_supply;
    }

    public void setAvailable_supply(String available_supply) {
        this.available_supply = available_supply;
    }

    public String getTotal_supply() {
        return total_supply;
    }

    public void setTotal_supply(String total_supply) {
        this.total_supply = total_supply;
    }

    public String getMax_supply() {
        return max_supply;
    }

    public void setMax_supply(String max_supply) {
        this.max_supply = max_supply;
    }

    public String getPercent_change_1h() {
        return percent_change_1h;
    }

    public void setPercent_change_1h(String percent_change_1h) {
        this.percent_change_1h = percent_change_1h;
    }

    public String getPercent_change_24h() {
        return percent_change_24h;
    }

    public void setPercent_change_24h(String percent_change_24h) {
        this.percent_change_24h = percent_change_24h;
    }

    public String getPercent_change_7d() {
        return percent_change_7d;
    }

    public void setPercent_change_7d(String percent_change_7d) {
        this.percent_change_7d = percent_change_7d;
    }

    public String getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(String last_updated) {
        this.last_updated = last_updated;
    }
}
