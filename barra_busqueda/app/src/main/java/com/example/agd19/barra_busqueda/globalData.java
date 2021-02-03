package com.example.agd19.barra_busqueda;

import java.io.Serializable;

/**
 * Created by agd19 on 04/05/2018.
 */

public class globalData implements Serializable {
    private String mercados_activos, porcentaje_btc, total_mercado, total_volumen;
    private int monedas_activas;

    public globalData()
    {
        monedas_activas =0;
        mercados_activos = "";
        porcentaje_btc ="";
        total_mercado = "";
        total_volumen = "";
    }

    public globalData(int monedas_activas, String mercados_activos, String porcentaje_btc, String total_mercado, String total_volumen)
    {
        this.monedas_activas = monedas_activas;
        this.mercados_activos = mercados_activos;
        this.porcentaje_btc = porcentaje_btc;
        this.total_volumen = total_volumen;
        this.total_mercado = total_mercado;
    }

    public int getMonedas_activas() {
        return monedas_activas;
    }

    public void setMonedas_activas(int monedas_activas) {
        this.monedas_activas = monedas_activas;
    }

    public String getMercados_activos() {
        return mercados_activos;
    }

    public void setMercados_activos(String mercados_activos) {
        this.mercados_activos = mercados_activos;
    }

    public String getPorcentaje_btc() {
        return porcentaje_btc;
    }

    public void setPorcentaje_btc(String porcentaje_btc) {
        this.porcentaje_btc = porcentaje_btc;
    }

    public String getTotal_mercado() {
        return total_mercado;
    }

    public void setTotal_mercado(String total_mercado) {
        this.total_mercado = total_mercado;
    }

    public String getTotal_volumen() {
        return total_volumen;
    }

    public void setTotal_volumen(String total_volumen) {
        this.total_volumen = total_volumen;
    }
}
