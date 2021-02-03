package com.example.agd19.barra_busqueda;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class heroes_zeroes extends AppCompatActivity {
    private ArrayList<moneda> heroes;
    private ArrayList<moneda> zeroes;
    private ArrayList<moneda> monedas;
    private moneda mone;
    private moneda moneda;
    private moneda moneda1;
    private ServidorPHP server;
    private RecyclerView recViewh;
    private RecyclerView recViewz;
    private TabHost th;
    private adaptador_lista_hz adaptador;
    private adaptador_lista_hz adaptador1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heroes_zeroes);
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

        recViewh = findViewById(R.id.recViewh);
        recViewz = findViewById(R.id.recViewz);
        server = new ServidorPHP();
        th = findViewById(R.id.TabHost1);

        th.setup();
        TabHost.TabSpec ts1 = th.newTabSpec("Tabh");
        ts1.setIndicator("HEROES");
        ts1.setContent(R.id.tabh);
        th.addTab(ts1);
        //tabspec 2
        th.setup();
        TabHost.TabSpec ts2 = th.newTabSpec("Tabz");
        ts2.setIndicator("ZEROES");
        ts2.setContent(R.id.tabz);
        th.addTab(ts2);

        th.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#00000000")); // unselected
        TextView tv0= (TextView) th.getTabWidget().getChildAt(0).findViewById(android.R.id.title); //Unselected Tabs
        tv0.setTextColor(Color.parseColor("#ffffff"));
        //ponemos por defecto el color del tabhost 2
        th.getTabWidget().getChildAt(1).setBackgroundColor(Color.parseColor("#1C1C1C")); // unselected
        TextView tv1 = (TextView) th.getTabWidget().getChildAt(1).findViewById(android.R.id.title); //Unselected Tabs
        tv1.setTextColor(Color.parseColor("#ffffff"));

        //método para cambiar el estilo del tabhost segun sea seleccionado
        th.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {

                for (int i = 0; i < th.getTabWidget().getChildCount(); i++) {
                    th.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#1C1C1C")); // unselected
                    TextView tv = (TextView) th.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
                    tv.setTextColor(Color.parseColor("#ffffff"));
                }

                th.getTabWidget().getChildAt(th.getCurrentTab()).setBackgroundColor(Color.parseColor("#00000000")); // selected
                TextView tv = (TextView) th.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
                tv.setTextColor(Color.parseColor("#ffffff"));


            }
        });

        recViewh.addItemDecoration(new SpaceItemDecoration(this,
                R.dimen.list_space, true, true));
        recViewz.addItemDecoration(new SpaceItemDecoration(this, R.dimen.list_space, true, true));

        // Con esto el tamaño del recyclerwiew no cambiará
        recViewh.setHasFixedSize(true);
        recViewz.setHasFixedSize(true);
        // Creo un layoutmanager para el recyclerview
        LinearLayoutManager llm = new LinearLayoutManager(this);
        LinearLayoutManager mmm = new LinearLayoutManager(this);
        recViewh.setLayoutManager(llm);
        recViewz.setLayoutManager(mmm);

        boolean existe = false;
        monedas = new ArrayList<>();
        try {
            monedas = server.obtenerMonedas2();
                heroes = new ArrayList<>();
                zeroes = new ArrayList<>();
                ArrayList<moneda> limpias = new ArrayList();

                mone = monedas.get(0);

                for (int i = 0; i < monedas.size(); i++ )
                {
                    if(monedas.get(i).getPercent_change_24h().trim().toString().equals("null"))
                    {
                        System.out.println("este valor es nulo");
                    }else {
                        limpias.add(monedas.get(i));
                    }
                }

            for (int i = 0; i < limpias.size(); i++ )
            {
                System.out.println("valor "+i+" "+limpias.get(i).getPercent_change_24h());
            }

            Double [] minimos = new Double[limpias.size()];
            Double [] maximos = new Double[limpias.size()];

                for (int i = 0;i<limpias.size(); i++)
                {
                    Double n1 = 0.0;
                    boolean valido = false;
                    if(limpias.get(i).getPercent_change_24h().trim().toString().equals("null"))
                    {
                        System.out.println("este valor es nulo");
                    }else {
                        n1 = Double.parseDouble(limpias.get(i).getPercent_change_24h().trim());
                        valido = true;
                    }
                    int longitud = minimos.length;
                    if(valido == true) {
                        minimos[i] = n1;
                    }
                }

                for (int i = 0; i<minimos.length; i++)
                {
                    System.out.println("valor "+minimos[i]);
                }

                Arrays.sort(minimos);

                for (int i = 0; i<minimos.length; i++)
                {
                    System.out.println("valor ordenado "+minimos[i]);
                }

                for (int i = 0; i<29; i++)
                {
                    for (int f = 0; f<limpias.size(); f++)
                    {
                        String doble = String.valueOf(minimos[i]);
                        if(doble.equals(limpias.get(f).getPercent_change_24h()))
                        {
                            System.out.println("esta moneda coincide "+limpias.get(f).getSymbol());
                            mone = limpias.get(f);
                            zeroes.add(mone);
                        }
                    }
                }
                int numero = 0;
                for (int i = minimos.length-1 ; i>=0; i--)
                {

                    //String doble = String.valueOf(minimos[i]);
                    maximos[numero] = minimos[i];
                    numero++;
                }

            for (int i = 0; i<29; i++)
            {
                for (int f = 0; f<limpias.size(); f++)
                {
                    String doble = String.valueOf(maximos[i]);
                    if(doble.equals(limpias.get(f).getPercent_change_24h()))
                    {
                        System.out.println("esta moneda coincide "+limpias.get(f).getSymbol());
                        mone = limpias.get(f);
                        heroes.add(mone);
                    }
                }
            }

        } catch (ServidorPHPException e) {
            e.printStackTrace();
        }




        adaptador = new adaptador_lista_hz(this, heroes, this);
        adaptador.refrescar();
        recViewh.setAdapter(adaptador);

        adaptador1 = new adaptador_lista_hz(this, zeroes, this);
        adaptador1.refrescar();
        recViewz.setAdapter(adaptador1);

    }


}
