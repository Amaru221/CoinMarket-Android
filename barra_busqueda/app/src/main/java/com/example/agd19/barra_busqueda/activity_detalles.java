package com.example.agd19.barra_busqueda;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.net.ssl.SSLSocketFactory;

public class activity_detalles extends AppCompatActivity{

    WebSocketClient webSocketClient;
    final String GDAX_WS_URL = "wss://ws-feed.gdax.com";
    final String TAG = "GDAX";
    private String URLNEW;
    private TextView tvnombre;
    private TextView tvalor;
    private ServidorPHP server;
    private moneda moneda;
    private String identificador;
    private String simbolo;
    private String valor;
    private TextView tvsymbol1;
    private TextView tvsymbol2;
    private TextView tvpriceusd1;
    private TextView tvpriceusd2;
    private TextView tvpricebtc1;
    private TextView tvpricebtc2;
    private TextView tvvolumen1;
    private TextView tvvolumen2;
    private TextView tvmarketcap1;
    private TextView tvmarketcap2;
    private TextView tvavailable1;
    private TextView tvavailable2;
    private TextView tvtotal1;
    private TextView tvtotal2;
    private TextView tvmax1;
    private TextView tvmax2;
    private TextView tvchange1h1;
    private TextView tvchange1h2;
    private TextView tvchange24h1;
    private TextView tvchange24h2;
    private TextView tvchange7d1;
    private TextView tvchange7d2;
    private LineChart mChart;
    private String[] tiempo;
    private String[] periodo1;
    private ArrayList<dataChart> chart;
    private String valor_tiempo, valor_periodo;
    char c1;
    char c24;
    char c7;
    private int fecha;
    private String periodo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                finish();
            }
        });
        valor_periodo = "";
        valor_tiempo = "";

        if(getIntent().getExtras() != null)
        {
            Bundle bundle = getIntent().getExtras();
            if(bundle!= null)
            {
                Object value1 = bundle.get("tiempo");
                Object value2 = bundle.get("periodo");
                if(value1 != null)
                {
                    valor_tiempo = value1.toString();
                    System.out.println("tiempo Recogido -----------------> "+valor_tiempo);
                }if(value2 != null)
                {
                    valor_periodo = value2.toString();
                    System.out.println("periodo Recogido -----------------> "+valor_periodo);
                }
            }
        }

        periodo1 = getResources().getStringArray(R.array.periodo);
        tiempo = getResources().getStringArray(R.array.tiempo);
        //*******************************************
        //probando Line Chart
        //********************************************
        mChart = (LineChart) findViewById(R.id.lineChart);
        //mChart.setOnchartGestureListener(this);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);
        //mChart.animateXY(300, 300);
        mChart.animateY( 300 , Easing. EasingOption.EaseInQuad);
        mChart.getAxisLeft().setTextColor(Color.WHITE);
        mChart.getAxisRight().setEnabled(false);
        mChart.getXAxis().setTextColor(Color.WHITE);
        mChart.setScaleEnabled(true);

        //***************************************************************

        URLNEW = "https://poloniex.com/public?command=returnChartData&currencyPair=USDT_";
        identificador = " ";
        simbolo = "";
        valor = "";
        c1 = ' ';
        c24 = ' ';
        c7 = ' ';
        moneda = new moneda();
        tvnombre = findViewById(R.id.tvnombre);
        tvalor = findViewById(R.id.tvalor);
        tvsymbol1 = findViewById(R.id.tvsymbol1);
        tvsymbol2 = findViewById(R.id.tvsymbol2);
        tvpriceusd1 = findViewById(R.id.tvprice1);
        tvpriceusd2 = findViewById(R.id.tvprice2);
        tvpricebtc1 = findViewById(R.id.tvpricebtc1);
        tvpricebtc2 = findViewById(R.id.tvpricebtc2);
        tvvolumen1 = findViewById(R.id.tvvolumen1);
        tvvolumen2 = findViewById(R.id.tvvolumen2);
        tvmarketcap1 = findViewById(R.id.tvmarketcap1);
        tvmarketcap2 = findViewById(R.id.tvmarketcap2);
        tvavailable1 = findViewById(R.id.tvavailable1);
        tvavailable2 = findViewById(R.id.tvavailable2);
        tvtotal1 = findViewById(R.id.tvtotal1);
        tvtotal2 = findViewById(R.id.tvtotal2);
        tvmax1 = findViewById(R.id.tvmax1);
        tvmax2 = findViewById(R.id.tvmax2);
        tvchange1h1 = findViewById(R.id.tvchange1h1);
        tvchange1h2 = findViewById(R.id.tvchange1h2);
        tvchange24h1 = findViewById(R.id.tvchange24h1);
        tvchange24h2 = findViewById(R.id.tvchange24h2);
        tvchange7d1 = findViewById(R.id.tvchange7d1);
        tvchange7d2 = findViewById(R.id.tvchange7d2);

        identificador = getIntent().getStringExtra("id");
        simbolo = getIntent().getStringExtra("symbol");
        valor = getIntent().getStringExtra("valor");

        System.out.println("Identificador websocket -> "+simbolo.toUpperCase());

        server = new ServidorPHP();
        try {
            moneda = server.obtenermoneda(identificador, valor);
            fecha = Integer.valueOf(server.obtenerFechaUnix());
            c1 = moneda.getPercent_change_1h().charAt(0);
            c24 = moneda.getPercent_change_24h().charAt(0);
            c7 = moneda.getPercent_change_7d().charAt(0);
            tvnombre.setText(moneda.getNombre());
            if(valor.equals("USD")) {
                tvalor.setText("$ " + moneda.getPrice_usd());
                tvpriceusd2.setText(moneda.getPrice_usd());
                tvmarketcap2.setText(moneda.getMarket_cap_usd());
                tvvolumen2.setText(moneda.getVolume_usd_24h());
            }else if (valor.equals("EUR")){
                tvalor.setText("€ " + moneda.getValue());
                tvpriceusd1.setText("Price (EUR): ");
                tvpriceusd2.setText(moneda.getValue());
                tvmarketcap2.setText(moneda.getMarketcap());
                tvvolumen2.setText(moneda.getVolume());
            }else if (valor.equals("ZAR")){
                tvalor.setText("R " + moneda.getValue());
                tvpriceusd1.setText("Price (ZAR): ");
                tvpriceusd2.setText(moneda.getValue());
                tvmarketcap2.setText(moneda.getMarketcap());
                tvvolumen2.setText(moneda.getVolume());
            }
            tvsymbol2.setText(moneda.getSymbol());
            //*********************************************

            tvpricebtc2.setText(moneda.getPrice_btc());


            tvavailable2.setText(moneda.getAvailable_supply());
            tvtotal2.setText(moneda.getTotal_supply());
            tvmax2.setText(moneda.getMax_supply());
            tvchange1h2.setText(moneda.getPercent_change_1h());
            tvchange24h2.setText(moneda.getPercent_change_24h());
            tvchange7d2.setText(moneda.getPercent_change_7d());


            periodo = "";

            if(valor_tiempo.equals("1 día"))
            {
                fecha-=86400;

            }else if(valor_tiempo.equals("7 días"))
            {
                fecha-=604000;

            }else if(valor_tiempo.equals("30 días"))
            {
                fecha-=2678400;
            }else{
                fecha-=86400;
            }

            if(valor_periodo.equals("5 min"))
            {
                periodo = "300";
            }else if (valor_periodo.equals("15 min"))
            {
                periodo = "900";
            }else if(valor_periodo.equals("30 min"))
            {
                periodo = "1800";
            }else if(valor_periodo.equals("2 horas"))
            {
                periodo = "7200";
            }else if(valor_periodo.equals("4 horas"))
            {
                periodo = "14400";
            }else if(valor_periodo.equals("1 día"))
            {
                periodo = "86400";
            }else{
                periodo = "7200";
            }

            ////////////////////////////////////////////////////////////
            URLNEW+=simbolo.toUpperCase();

            System.out.println("DATOS ----------------- simbolo "+simbolo+"//fecha//"+fecha+"//valor tiempo//"+valor_tiempo+"//periodo//"+periodo+"-/valor periodo-/"+valor_periodo);


            String start = "&start="+fecha+"&end=9999999999&period="+periodo;
            URLNEW+=start;
            System.out.println("Mostrar url -> "+URLNEW);
            chart = new ArrayList<>();

           if(simbolo.toUpperCase().equals("ETH")||simbolo.toUpperCase().equals("LTC")||simbolo.toUpperCase().equals("BTC")||simbolo.toUpperCase().equals("BCH")||simbolo.toUpperCase().equals("XMR")||simbolo.toUpperCase().equals("BCH")||simbolo.toUpperCase().equals("DASH")||simbolo.toUpperCase().equals("ZEC")||simbolo.toUpperCase().equals("XRP")||simbolo.toUpperCase().equals("STR")||simbolo.toUpperCase().equals("ETC")) {

                try {
                    chart = server.obtenerChart(URLNEW);

                } catch (ServidorPHPException e) {
                    e.printStackTrace();
                }
                ////////////////////////////////////////////////////////////

                ArrayList<Entry> yvalues = new ArrayList<>();

                for (int i = 0; i < chart.size(); i++) {
                    yvalues.add(new Entry(i, Float.valueOf(chart.get(i).getClose())));
                }
                LineDataSet set1 = new LineDataSet(yvalues, "Data set 1");

                set1.setFillAlpha(110);
                set1.setColor(Color.RED);
                set1.setLineWidth(2f);
                set1.setValueTextColor(Color.GREEN);
                set1.setValueTextSize(7f);
                //ponemos el la leyenda el simbolo de la moneda
                set1.setLabel(moneda.getSymbol());
                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                dataSets.add(set1);

                LineData data = new LineData(dataSets);
                mChart.setData(data);
            }

            editporcentajes();



            iniciarWebSocket(simbolo.toUpperCase());


        }catch(ServidorPHPException e)
        {
            System.out.println("Error obteniendo la moneda -> " + e.toString());
        }


    }

    public boolean onCreateOptionsMenu (Menu menu)
    {
        getMenuInflater().inflate(R.menu.item_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tiempo:
                System.out.println("HAS PULSADO EL CAMBIO DE TIEMPO");
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getResources().getString(R.string.dialog4));
                builder.setItems(R.array.tiempo, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent=new Intent(activity_detalles.this,activity_detalles.class);
                        intent.putExtra("tiempo", tiempo[i]);
                        intent.putExtra("periodo", valor_periodo);
                        intent.putExtra("symbol", simbolo);
                        intent.putExtra("valor", valor);
                        intent.putExtra("id", identificador);
                        startActivity(intent);
                        Toast.makeText(activity_detalles.this,String.format("Has elegido %s", tiempo[i]), Toast.LENGTH_SHORT).show();
                    }
                });
                Dialog dialog = builder.create();
                dialog.show();
                return true;

                case R.id.periodo:
                System.out.println("HAS PULSADO EL CAMBIO DE PERIODO");
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setTitle(getResources().getString(R.string.dialog5));
                builder1.setItems(R.array.periodo, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent=new Intent(activity_detalles.this,activity_detalles.class);
                        intent.putExtra("periodo", periodo1[i]);
                        intent.putExtra("tiempo", valor_tiempo);
                        intent.putExtra("symbol", simbolo);
                        intent.putExtra("valor", valor);
                        intent.putExtra("id", identificador);
                        startActivity(intent);
                        Toast.makeText(activity_detalles.this,String.format("Has elegido %s", periodo1[i]), Toast.LENGTH_SHORT).show();
                    }
                });
                Dialog dialog1 = builder1.create();
                dialog1.show();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }




    public void editporcentajes()
    {
        if('-' == c1 )
        {
            tvchange1h2.setText(moneda.getPercent_change_1h()+"%");
            tvchange1h2.setTextColor(Color.RED);
        }else{
            tvchange1h2.setText("+"+moneda.getPercent_change_1h()+"%");
            tvchange1h2.setTextColor(Color.GREEN);
        }

        if('-' == c24 )
        {
            tvchange24h2.setText(moneda.getPercent_change_24h()+"%");
            tvchange24h2.setTextColor(Color.RED);
        }else{
            tvchange24h2.setText("+"+moneda.getPercent_change_24h()+"%");
            tvchange24h2.setTextColor(Color.GREEN);
        }

        if('-' == c7 )
        {
            tvchange7d2.setText(moneda.getPercent_change_7d()+"%");
            tvchange7d2.setTextColor(Color.RED);
        }else{
            tvchange7d2.setText("+"+moneda.getPercent_change_7d()+"%");
            tvchange7d2.setTextColor(Color.GREEN);
        }
    }

    public void initWebSocket(){

        URI gdaxURI = null;
        try {
            gdaxURI = new URI(GDAX_WS_URL);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        webSocketClient = new WebSocketClient(gdaxURI) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                Log.d(TAG, "·onOpen");
                subcribe();
            }

            @Override
            public void onMessage(String message) {
                Log.d(TAG, "onMessage"+message);
                JSONObject jsonObject = null;

                try {
                    jsonObject = new JSONObject(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(jsonObject!= null){
                    try {
                        final String price = jsonObject.getString("price");
                        System.out.println("preciooooo"+price);

                        if(price != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ((TextView) findViewById(R.id.tvalor)).setText("$" + price);
                                }
                            });
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.d(TAG, "·onClose:");
            }

            @Override
            public void onError(Exception ex) {

            }
        };

        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();

        try {
            webSocketClient.setSocket(factory.createSocket());
        } catch (IOException e) {
            e.printStackTrace();
        }

        webSocketClient.connect();

    }

    public void subcribe(){

        webSocketClient.send("{\n" +
                "    \"type\": \"subscribe\",\n" +
                "    \"channels\": [{ \"name\": \"ticker\", \"product_ids\": [\""+simbolo.toUpperCase()+"-USD\"] }]\n" +
                "}");

    }

    public void iniciarWebSocket(String simbol)
    {
        if(simbol.equals("ETH")||simbol.equals("BTC")||simbol.equals("LTC"))
        {
            initWebSocket();
        }
    }

}
