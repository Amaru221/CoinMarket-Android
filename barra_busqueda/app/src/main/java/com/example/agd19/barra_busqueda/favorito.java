package com.example.agd19.barra_busqueda;

/**
 * Created by agd19 on 09/04/2018.
 */

public class favorito {

    private String token;
    private String cripto;

    public favorito()
    {
        token = "";
        cripto = "";
    }

    public favorito (String token, String cripto)
    {
        this.token = token;
        this.cripto = cripto;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCripto() {
        return cripto;
    }

    public void setCripto(String cripto) {
        this.cripto = cripto;
    }
}
