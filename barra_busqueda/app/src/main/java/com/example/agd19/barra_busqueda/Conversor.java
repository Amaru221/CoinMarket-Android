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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class Conversor extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    private EditText etcripto;
    private TextView etmoneda;
    private Button blimpiar;
    private Button bcalcular;
    private Spinner spcripto;
    private String[] listamonedas;
    private String[] listavalor;
    private Spinner spmoneda;
    private ServidorPHP server;
    private moneda mone;
    private ArrayList<moneda> monedas;
    private ArrayList<String> newlist;
    private ArrayList<String> newlist1;
    private ArrayAdapter<String> comboAdapter;
    private ArrayAdapter<String> comboAdapter1;
    private String nombremoneda;
    private String valor;
    private String simbolo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversor);
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
        etmoneda = findViewById(R.id.etmoneda);
        etcripto = findViewById(R.id.etcripto);
        blimpiar = findViewById(R.id.blimpiar);
        bcalcular = findViewById(R.id.bcalcular);
        spcripto = findViewById(R.id.spcripto);
        spmoneda = findViewById(R.id.spmoneda);

        spmoneda.setOnItemSelectedListener(this);
        spcripto.setOnItemSelectedListener(this);

        blimpiar.setOnClickListener(this);
        bcalcular.setOnClickListener(this);

        mone = new moneda();
        monedas = new ArrayList<>();
        server = new ServidorPHP();
        listavalor = new String[] {"USD", "EUR", "ZAR"};
        simbolo = "bitcoin";
        valor = "USD";
        nombremoneda = " ";
        newlist = new ArrayList<>();
        newlist1 = new ArrayList<>();

        try {
            monedas = server.obtenerMonedas("USD");
            listamonedas = new String [monedas.size()];


            for (int i = 0; i<monedas.size(); i++)
            {
                listamonedas[i] = monedas.get(i).getNombre();
            }

            Collections.addAll(newlist, listamonedas);
            Collections.addAll(newlist1, listavalor);
            //Implemento el adapter con el contexto, layout, listamonedas
            comboAdapter = new ArrayAdapter<String>(this,R.layout.nuevo_spinner_item, newlist);
            comboAdapter1 = new ArrayAdapter<String>(this,R.layout.nuevo_spinner_item, newlist1);
            comboAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            comboAdapter1.setDropDownViewResource(R.layout.spinner_dropdown_item);

            //Cargo el spinner con los datos
            spcripto.setAdapter(comboAdapter);
            spmoneda.setAdapter(comboAdapter1);




        } catch (ServidorPHPException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.blimpiar:
                etmoneda.setText("0");
                etcripto.setText("0");

                break;

            case R.id.bcalcular:
                if(etcripto.getText().toString().equals("")) {
                    Toast.makeText(Conversor.this,String.format("Introduce un valor válido de conversión"), Toast.LENGTH_SHORT).show();

                }else
                {
                    try {
                        System.out.println("simbolito " + simbolo);
                        System.out.println("valorcito " + valor);
                        mone = server.obtenermoneda(simbolo, valor);
                        Double n1 = 0.0;
                        Double n2 = 0.0;
                        String mostrar = "";
                        String numero = etcripto.getText().toString().trim();
                        Double n3 = 0.0;
                        n3 = Double.parseDouble(numero);
                        System.out.println(" n3 " + n3);
                        if (valor.equals("USD")) {
                            n1 = Double.parseDouble(mone.getPrice_usd());
                            System.out.println(" n1 " + n1);
                        } else {
                            n1 = Double.parseDouble(mone.getValue());
                            System.out.println(" n1 " + n1);
                        }
                        n2 = n1 * n3;
                        String number = String.valueOf(n2);
                        if(number.length()>20)
                        {
                            for (int i = 0; i<number.length(); i++)
                            {
                                char c = number.charAt(i);
                                mostrar+=c;
                            }
                        }else{
                            mostrar = number;
                        }

                        System.out.println(" numero final " + mostrar);
                        etmoneda.setText(mostrar);


                    } catch (ServidorPHPException e) {
                        e.printStackTrace();
                    }
                }

                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        switch (adapterView.getId()){
            case R.id.spcripto:
                nombremoneda = listamonedas[i];
                simbolo = monedas.get(i).getNombre();
                System.out.println("simbolo al pulsar "+simbolo);

                break;

            case R.id.spmoneda:
                valor = listavalor[i];

                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
