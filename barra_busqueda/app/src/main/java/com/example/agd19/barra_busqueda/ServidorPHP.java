package com.example.agd19.barra_busqueda;

/**
 * Created by agd19 on 11/01/2018.
 */
import android.os.StrictMode;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Esta clase realiza una conexión con un servidor PHP
 */
public class ServidorPHP
{
    private String URLSERVIDOR = "https://api.coinmarketcap.com/v1/ticker/";
    private final String URLGLOBALES = "https://api.coinmarketcap.com/v1/global/";
    private String URLMONEDA = "https://api.coinmarketcap.com/v1/ticker/";
    private final String URLFAV = "http://personalarea.000webhostapp.com/obtenerFavoritos.php";
    private final String URLINSERTARFAV = "http://personalarea.000webhostapp.com/insertarFavorito.php";
    private final String URLFECHAUNIX = "http://personalarea.000webhostapp.com/fechaUnix.php";
    private final String URLELIMINARFAV = "http://personalarea.000webhostapp.com/eliminarFavorito.php";
    private final String URLCHART = "https://poloniex.com/public?command=returnChartData&currencyPair=USDT_BTC&start=1524113662&end=9999999999&period=14400";

    /**
     * Constructor
     */
    public ServidorPHP()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public String obtenerFechaUnix()
    {
        String fecha = "";
        JSONParser parser = new JSONParser();
        JSONObject datos;
        try {
            datos = parser.getJSONObjectFromUrl(URLFECHAUNIX, null);
            if(datos!= null)
            {
              fecha = datos.getString("fecha");
            }
        } catch (JSONException | IOException e) {
            System.out.println("ERROR OBTENIENDO FECHA");
            e.printStackTrace();
        }
        for (int i = 0; i<fecha.length(); i++)
        {

        }

        return fecha;
    }


    /**
     * metodo para insertar un usuario
     * @param moneda de usuario
     * @throws ServidorPHPException
     */
    public void InsertarFav(String moneda) throws ServidorPHPException {
        JSONParser parser = new JSONParser();

        HashMap<String, String> parametros = new HashMap<>();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        // Meto los parámetros
        parametros.put("token", refreshedToken.toString());
        parametros.put("moneda", moneda);


        try {
            parser.getJSONObjectFromUrl(URLINSERTARFAV, parametros);
        } catch (JSONException | IOException e) {
            System.out.println("ERROR INSERTANDO FAVORITO");
            e.printStackTrace();
        }
    }

    /**
     * eliminamos el favorito si esta guardado
     */
    public void EliminarFav(String moneda)
    {
        JSONParser parser = new JSONParser();
        HashMap<String, String> parametros = new HashMap<>();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        // Meto los parámetros
        parametros.put("token", refreshedToken.toString());
        parametros.put("moneda", moneda);

        try {
            parser.getJSONObjectFromUrl(URLELIMINARFAV, parametros);
        } catch (JSONException | IOException e) {
            System.out.println("ERROR ELIMINANDO USUARIO");
            e.printStackTrace();
        }
    }


    public ArrayList<favorito> ObtenerFav () throws ServidorPHPException
    {
        ArrayList<favorito> favoritos = new ArrayList<>();
        HashMap<String, String> parametros = new HashMap<>();
        JSONParser parser = new JSONParser();
        JSONArray datos;
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        System.out.println("token app "+refreshedToken);
        parametros.put("token", refreshedToken);

        try
        {
            // Obtengo los datos de la moneda del servidor
            // Como no tiene parámetros la llamada al servidor
            // el segundo parámetro de la llamada es null
            datos = parser.getJSONArrayFromUrl(URLFAV, parametros);
            if( datos != null )
            {
                for(int i = 0; i < datos.length(); i++)
                {
                    // Obtengo los datos de los favoritos
                    String tok = datos.getJSONObject(i).getString("token");
                    String coin = datos.getJSONObject(i).getString("moneda");
                    favorito fav = new favorito(tok, coin);
                    favoritos.add(fav);
                }
            }
        }
        catch (IOException | JSONException e)
        {
            throw new ServidorPHPException(e.toString());
        }

        return favoritos;
    }

    /**
     * metodo para obtener una moneda
     * @return devuelve una moneda
     * @throws ServidorPHPException
     */
    public moneda obtenermoneda(String identificador, String valor) throws ServidorPHPException
    {
        moneda mone = new moneda();
        JSONParser parser = new JSONParser();
        JSONObject datos;
        // Declaro el array de los parámetros
        HashMap<String, String> parametros = new HashMap<>();
        System.out.println("identificador de la moneda -> "+identificador);
        String URLESPECIFICA = URLMONEDA+identificador+"/";
        //System.out.println("url especifica -> "+URLESPECIFICA);
        try {
            // Obtengo los datos de la moneda del servidor
            // Como no tiene parámetros la llamada al servidor
            // el segundo parámetro de la llamada es null

            if (valor.equals("USD")) {
                datos = parser.getJSONObjectFromUrl(URLESPECIFICA, null);
                System.out.println("url especifica -> "+URLESPECIFICA);

                if (datos != null) {

                    // Obtengo los datos de moneda
                    String id = datos.getString("id");
                    String nombre = datos.getString("name");
                    String symbol = datos.getString("symbol");
                    String rank = datos.getString("rank");
                    String price_usd = datos.getString("price_usd");
                    String price_btc = datos.getString("price_btc");
                    String available_supply = datos.getString("available_supply");
                    String total_supply = datos.getString("total_supply");
                    String max_supply = datos.getString("max_supply");
                    String volume_usd_24h = datos.getString("24h_volume_usd");
                    String market_cap_usd = datos.getString("market_cap_usd");
                    String percent_change_1h = datos.getString("percent_change_1h");
                    String percent_change_24h = datos.getString("percent_change_24h");
                    String percent_change_7d = datos.getString("percent_change_7d");
                    String last_updated = datos.getString("last_updated");
                    System.out.println(id);
                    System.out.println(nombre);
                    System.out.println(symbol);
                    System.out.println(rank);
                    System.out.println(price_usd);
                    System.out.println(price_btc);
                    System.out.println(percent_change_7d);

                    mone = new moneda(id, nombre, symbol, rank, price_usd, price_btc, available_supply, total_supply, max_supply, volume_usd_24h, market_cap_usd, percent_change_1h, percent_change_24h, percent_change_7d, last_updated, false, "", "", "");

                }
            } else if (valor.equals("EUR")) {
                URLESPECIFICA += "?convert=EUR";
                datos = parser.getJSONObjectFromUrl(URLESPECIFICA, null);
                System.out.println("url especifica -> "+URLESPECIFICA);
                if (datos != null) {
                    for (int i = 0; i < datos.length(); i++) {
                        // Obtengo los datos de cada moneda
                        String id = datos.getString("id");
                        String nombre = datos.getString("name");
                        String symbol = datos.getString("symbol");
                        String rank = datos.getString("rank");
                        String price_usd = datos.getString("price_usd");
                        String price_btc = datos.getString("price_btc");
                        String available_supply = datos.getString("available_supply");
                        String total_supply = datos.getString("total_supply");
                        String max_supply = datos.getString("max_supply");
                        String volume_usd_24h = datos.getString("24h_volume_usd");
                        String market_cap_usd = datos.getString("market_cap_usd");
                        String percent_change_1h = datos.getString("percent_change_1h");
                        String percent_change_24h = datos.getString("percent_change_24h");
                        String percent_change_7d = datos.getString("percent_change_7d");
                        String last_updated = datos.getString("last_updated");
                        String value = datos.getString("price_eur");
                        String volume = datos.getString("24h_volume_eur");
                        String marketcap = datos.getString("market_cap_eur");

                        mone = new moneda(id, nombre, symbol, rank, price_usd, price_btc, available_supply, total_supply, max_supply, volume_usd_24h, market_cap_usd, percent_change_1h, percent_change_24h, percent_change_7d, last_updated, false, value, volume, marketcap);
                    }
                }
            }else if (valor.equals("ZAR")) {
                URLESPECIFICA += "?convert=ZAR";
                System.out.println("url especifica -> "+URLESPECIFICA);
                datos = parser.getJSONObjectFromUrl(URLESPECIFICA, null);
                if (datos != null) {
                    for (int i = 0; i < datos.length(); i++) {
                        // Obtengo los datos de cada moneda
                        String id = datos.getString("id");
                        String nombre = datos.getString("name");
                        String symbol = datos.getString("symbol");
                        String rank = datos.getString("rank");
                        String price_usd = datos.getString("price_usd");
                        String price_btc = datos.getString("price_btc");
                        String available_supply = datos.getString("available_supply");
                        String total_supply = datos.getString("total_supply");
                        String max_supply = datos.getString("max_supply");
                        String volume_usd_24h = datos.getString("24h_volume_usd");
                        String market_cap_usd = datos.getString("market_cap_usd");
                        String percent_change_1h = datos.getString("percent_change_1h");
                        String percent_change_24h = datos.getString("percent_change_24h");
                        String percent_change_7d = datos.getString("percent_change_7d");
                        String last_updated = datos.getString("last_updated");
                        String value = datos.getString("price_zar");
                        String volume = datos.getString("24h_volume_zar");
                        String marketcap = datos.getString("market_cap_zar");

                        mone = new moneda(id, nombre, symbol, rank, price_usd, price_btc, available_supply, total_supply, max_supply, volume_usd_24h, market_cap_usd, percent_change_1h, percent_change_24h, percent_change_7d, last_updated, false, value, volume, marketcap);
                    }
                }
            }
        }
        catch (IOException | JSONException e)
        {
            throw new ServidorPHPException(e.toString());
        }

        return mone;
    }


    /**
     * metodo para obtener todas las monedas
     * @return devuelve un array con todas las monedas
     * @throws ServidorPHPException
     */
    public ArrayList<moneda> obtenerMonedas(String valor) throws ServidorPHPException
    {
        ArrayList<moneda> monedas = new ArrayList<>();
        JSONParser parser = new JSONParser();
        //JSONParser parser1 = new JSONParser();
        JSONArray datos;
        //JSONArray datos1;

        try
        {
            // Obtengo los datos de la moneda del servidor
            // Como no tiene parámetros la llamada al servidor
            // el segundo parámetro de la llamada es null
            if(valor.equals("USD")) {
                URLSERVIDOR+="?start=0&limit=1900";
                datos = parser.getJSONArrayFromUrl(URLSERVIDOR, null);
                if (datos != null) {
                    for (int i = 0; i < datos.length(); i++) {
                        // Obtengo los datos de cada moneda
                        String id = datos.getJSONObject(i).getString("id");
                        String nombre = datos.getJSONObject(i).getString("name");
                        String symbol = datos.getJSONObject(i).getString("symbol");
                        String rank = datos.getJSONObject(i).getString("rank");
                        String price_usd = datos.getJSONObject(i).getString("price_usd");
                        String price_btc = datos.getJSONObject(i).getString("price_btc");
                        String available_supply = datos.getJSONObject(i).getString("available_supply");
                        String total_supply = datos.getJSONObject(i).getString("total_supply");
                        String max_supply = datos.getJSONObject(i).getString("max_supply");
                        String volume_usd_24h = datos.getJSONObject(i).getString("24h_volume_usd");
                        String market_cap_usd = datos.getJSONObject(i).getString("market_cap_usd");
                        String percent_change_1h = datos.getJSONObject(i).getString("percent_change_1h");
                        String percent_change_24h = datos.getJSONObject(i).getString("percent_change_24h");
                        String percent_change_7d = datos.getJSONObject(i).getString("percent_change_7d");
                        String last_updated = datos.getJSONObject(i).getString("last_updated");
                        moneda mo = new moneda(id, nombre, symbol, rank, price_usd, price_btc, available_supply, total_supply, max_supply, volume_usd_24h, market_cap_usd, percent_change_1h, percent_change_24h, percent_change_7d, last_updated, false, "", "", "");
                        monedas.add(mo);
                    }
                }
            }else if(valor.equals("EUR")) {
                URLSERVIDOR+="?convert=EUR&start=0&limit=1900";
                datos = parser.getJSONArrayFromUrl(URLSERVIDOR, null);
                if (datos != null) {
                    for (int i = 0; i < datos.length(); i++) {
                        // Obtengo los datos de cada moneda
                        String id = datos.getJSONObject(i).getString("id");
                        String nombre = datos.getJSONObject(i).getString("name");
                        String symbol = datos.getJSONObject(i).getString("symbol");
                        String rank = datos.getJSONObject(i).getString("rank");
                        String price_usd = datos.getJSONObject(i).getString("price_usd");
                        String price_btc = datos.getJSONObject(i).getString("price_btc");
                        String available_supply = datos.getJSONObject(i).getString("available_supply");
                        String total_supply = datos.getJSONObject(i).getString("total_supply");
                        String max_supply = datos.getJSONObject(i).getString("max_supply");
                        String volume_usd_24h = datos.getJSONObject(i).getString("24h_volume_usd");
                        String market_cap_usd = datos.getJSONObject(i).getString("market_cap_usd");
                        String percent_change_1h = datos.getJSONObject(i).getString("percent_change_1h");
                        String percent_change_24h = datos.getJSONObject(i).getString("percent_change_24h");
                        String percent_change_7d = datos.getJSONObject(i).getString("percent_change_7d");
                        String last_updated = datos.getJSONObject(i).getString("last_updated");
                        String value = datos.getJSONObject(i).getString("price_eur");
                        String volume = datos.getJSONObject(i).getString("24h_volume_eur");
                        String marketcap = datos.getJSONObject(i).getString("market_cap_eur");
                        moneda mo = new moneda(id, nombre, symbol, rank, price_usd, price_btc, available_supply, total_supply, max_supply, volume_usd_24h, market_cap_usd, percent_change_1h, percent_change_24h, percent_change_7d, last_updated, false, value, volume, marketcap);
                        monedas.add(mo);
                    }
                }
            }else if(valor.equals("ZAR")) {
                URLSERVIDOR+="?convert=ZAR&start=0&limit=1900";
                datos = parser.getJSONArrayFromUrl(URLSERVIDOR, null);
                if (datos != null) {
                    for (int i = 0; i < datos.length(); i++) {
                        // Obtengo los datos de cada moneda
                        String id = datos.getJSONObject(i).getString("id");
                        String nombre = datos.getJSONObject(i).getString("name");
                        String symbol = datos.getJSONObject(i).getString("symbol");
                        String rank = datos.getJSONObject(i).getString("rank");
                        String price_usd = datos.getJSONObject(i).getString("price_usd");
                        String price_btc = datos.getJSONObject(i).getString("price_btc");
                        String available_supply = datos.getJSONObject(i).getString("available_supply");
                        String total_supply = datos.getJSONObject(i).getString("total_supply");
                        String max_supply = datos.getJSONObject(i).getString("max_supply");
                        String volume_usd_24h = datos.getJSONObject(i).getString("24h_volume_usd");
                        String market_cap_usd = datos.getJSONObject(i).getString("market_cap_usd");
                        String percent_change_1h = datos.getJSONObject(i).getString("percent_change_1h");
                        String percent_change_24h = datos.getJSONObject(i).getString("percent_change_24h");
                        String percent_change_7d = datos.getJSONObject(i).getString("percent_change_7d");
                        String last_updated = datos.getJSONObject(i).getString("last_updated");
                        String value = datos.getJSONObject(i).getString("price_zar");
                        String volume = datos.getJSONObject(i).getString("24h_volume_zar");
                        String marketcap = datos.getJSONObject(i).getString("market_cap_zar");
                        moneda mo = new moneda(id, nombre, symbol, rank, price_usd, price_btc, available_supply, total_supply, max_supply, volume_usd_24h, market_cap_usd, percent_change_1h, percent_change_24h, percent_change_7d, last_updated, false, value, volume, marketcap);
                        monedas.add(mo);
                    }
                }
            }


        }
        catch (IOException | JSONException e)
        {
            throw new ServidorPHPException(e.toString());
        }

        return monedas;
    }

    /**
     * metodo para obtener todas las monedas
     * @return devuelve un array con todas las monedas
     * @throws ServidorPHPException
     */
    public ArrayList<moneda> obtenerMonedas2() throws ServidorPHPException
    {
        ArrayList<moneda> monedas = new ArrayList<>();
        JSONParser parser = new JSONParser();
        JSONArray datos;

        try
        {
            // Obtengo los datos de la moneda del servidor
            // Como no tiene parámetros la llamada al servidor
            // el segundo parámetro de la llamada es null
                URLSERVIDOR+="?start=0&limit=1900";
                datos = parser.getJSONArrayFromUrl(URLSERVIDOR, null);
                if (datos != null) {
                    for (int i = 0; i < datos.length(); i++) {
                        // Obtengo los datos de cada moneda
                        String id = datos.getJSONObject(i).getString("id");
                        String nombre = datos.getJSONObject(i).getString("name");
                        String symbol = datos.getJSONObject(i).getString("symbol");
                        String rank = datos.getJSONObject(i).getString("rank");
                        String price_usd = datos.getJSONObject(i).getString("price_usd");
                        String price_btc = datos.getJSONObject(i).getString("price_btc");
                        String available_supply = datos.getJSONObject(i).getString("available_supply");
                        String total_supply = datos.getJSONObject(i).getString("total_supply");
                        String max_supply = datos.getJSONObject(i).getString("max_supply");
                        String volume_usd_24h = datos.getJSONObject(i).getString("24h_volume_usd");
                        String market_cap_usd = datos.getJSONObject(i).getString("market_cap_usd");
                        String percent_change_1h = datos.getJSONObject(i).getString("percent_change_1h");
                        String percent_change_24h = datos.getJSONObject(i).getString("percent_change_24h");
                        String percent_change_7d = datos.getJSONObject(i).getString("percent_change_7d");
                        String last_updated = datos.getJSONObject(i).getString("last_updated");
                        moneda mo = new moneda(id, nombre, symbol, rank, price_usd, price_btc, available_supply, total_supply, max_supply, volume_usd_24h, market_cap_usd, percent_change_1h, percent_change_24h, percent_change_7d, last_updated, false, "", "", "");
                        monedas.add(mo);
                    }
                }

        }
        catch (IOException | JSONException e)
        {
            throw new ServidorPHPException(e.toString());
        }

        return monedas;
    }




    public ArrayList<dataChart> obtenerChart(String URLNEW) throws ServidorPHPException
    {
        ArrayList<dataChart> chart = new ArrayList<>();
        JSONParser parser = new JSONParser();
        JSONArray datos;


        try
        {

            datos = parser.getJSONArrayFromUrl(URLNEW, null);
            if( datos != null )
            {
                for(int i = 0; i < datos.length(); i++)
                {
                    // Obtengo los datos de cada moneda
                    String date = datos.getJSONObject(i).getString("date");
                    String high = datos.getJSONObject(i).getString("high");
                    String low = datos.getJSONObject(i).getString("low");
                    String open = datos.getJSONObject(i).getString("open");
                    String close = datos.getJSONObject(i).getString("close");
                    String volume = datos.getJSONObject(i).getString("volume");
                    String quoteVolume = datos.getJSONObject(i).getString("quoteVolume");
                    String weightedAverage = datos.getJSONObject(i).getString("weightedAverage");
                    dataChart ch = new dataChart(date, high, low, open, close, volume, quoteVolume, weightedAverage);
                    chart.add(ch);
                }
            }


        }
        catch (IOException | JSONException e)
        {
            throw new ServidorPHPException(e.toString());
        }

        return chart;
    }

    /*public void obtener_datos_globales() throws ServidorPHPException, JSONException {
        //globalData dg = new globalData();
        JSONParser parser = new JSONParser();
        JSONObject datos = new JSONObject();
        try
        {
            System.out.println("INTENTAMOS ACCEDER");
            datos = parser.getJSONObjectFromUrl(URLGLOBALES, null);

            System.out.println("accedimos????");

            if(datos != null) {
                    System.out.println("HEMOS accedido a los datos!");
                    System.out.println(datos.getLong("total_market_cap_usd"));
                    System.out.println(datos.getLong("total_24h_volume_usd"));
                    System.out.println(datos.getLong("bitcoin_percentage_of_market_cap"));

            }

        }
        catch (IOException | JSONException e)
        {
            //throw new ServidorPHPException(e.toString());
        }

        //return dg;
    }*/

}
