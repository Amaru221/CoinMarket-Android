package com.example.agd19.barra_busqueda;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;

public class activity_alertas extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Spinner spalerta;
    private ServidorPHP server;
    private String[] listamonedas;
    private moneda mone;
    private String valor;
    private String simbolo;
    private ArrayList<String> newlist;
    private ArrayAdapter<String> comboAdapter;
    private ArrayList<moneda> monedas;
    private EditText etalerta;
    private Button b1;
    private Alerta alert;
    private Double numero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alertas);
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
        spalerta = findViewById(R.id.spalerta);

        spalerta.setOnItemSelectedListener(this);
        etalerta = findViewById(R.id.etalerta);
        b1 = findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    moneda mo = new moneda();
                    mo = server.obtenermoneda(simbolo, "USD");
                    numero = Double.parseDouble(mo.getPrice_usd());


                } catch (ServidorPHPException e) {
                    e.printStackTrace();
                }
                alert = new Alerta(etalerta,activity_alertas.this, numero);
                new Thread(alert).start();
            }
        });

        mone = new moneda();
        monedas = new ArrayList<>();
        server = new ServidorPHP();

        simbolo = "bitcoin";
        valor = "USD";
        newlist = new ArrayList<>();

        try {

            monedas = server.obtenerMonedas("USD");
            listamonedas = new String [monedas.size()];


            for (int i = 0; i<monedas.size(); i++)
            {
                listamonedas[i] = monedas.get(i).getNombre();
            }

            Collections.addAll(newlist, listamonedas);

            comboAdapter = new ArrayAdapter<String>(this,R.layout.nuevo_spinner_item, newlist);
            comboAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            spalerta.setAdapter(comboAdapter);

        } catch (ServidorPHPException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        switch (adapterView.getId()){
            case R.id.spalerta:
                //nombremoneda = listamonedas[i];
                simbolo = monedas.get(i).getNombre();
                System.out.println("simbolo al pulsar "+simbolo);

                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
