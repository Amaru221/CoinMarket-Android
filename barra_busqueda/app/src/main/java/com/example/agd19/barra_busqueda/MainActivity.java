package com.example.agd19.barra_busqueda;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private RecyclerView recView;
    private adaptador_lista_monedas adaptador;
    private adaptador_lista_favoritos adaptadorfav;
    private ArrayList<moneda> monedas;
    private ArrayList<moneda> monedasFavoritas;
    private ArrayList<favorito> fav;
    private ServidorPHP server;
    private SearchView searchView;
    private TabHost th;
    private SwipeRefreshLayout refreshLayout;
    private SwipeRefreshLayout refreshLayoutfav;
    private RecyclerView recViewFav;
    private String valor;
    private String valor1;
    private String valor2;
    private String[] conversiones;
    private String[] porcent;
    private String[] ordenar;
    private String refreshedToken;
    private globalData dg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        conversiones = getResources().getStringArray(R.array.conversiones);
        porcent = getResources().getStringArray(R.array.porcent);
        ordenar = getResources().getStringArray(R.array.ordenar);
        monedas = new ArrayList<>();
        dg = new globalData();
        //valor por defecto
        valor = "USD";
        valor1 = "24h";
        valor2 = "Market Cap";
        refreshedToken = "";
        if(getIntent().getExtras() != null)
        {
            Bundle bundle = getIntent().getExtras();
            if(bundle!= null)
            {
                Object value1 = bundle.get("valor");
                Object value2 = bundle.get("porcent");
                Object value3 = bundle.get("ordenar");
                if(value1 != null)
                {
                    valor = value1.toString();
                    System.out.println("Valor Recogido de moneda -----------------> "+valor);
                }
                if(value2 != null)
                {
                    valor1 = value2.toString();
                    System.out.println("Valor Recogido de porcentaje-----------------> "+valor1);
                }
                if(value3 != null)
                {
                    valor2 = value3.toString();
                    System.out.println("Valor Recogido para orden-----------------> "+valor2);
                }
            }
        }



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //System.out.println("DISPOSITIVO ->" +refreshedToken.toString());

        //*********************Obtenemos fecha actual*************************************
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();

        String fecha = dateFormat.format(date);
        System.out.println("Fecha actual ->"+fecha);

        int unix = 1524009600;
        int formula = (unix/86400)+255569+(-5/24);
        System.out.println("UNIX ->"+formula);
        //*****************************************************************************************

        //Obtengo los recursos de la actividad
        recView = (RecyclerView) findViewById(R.id.recView);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        refreshLayoutfav = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh2);
        th = (TabHost) findViewById(R.id.TabHost);
        recViewFav = (RecyclerView) findViewById(R.id.recViewFav);
        //*****************************************

        // Hay que crear en la carpeta values un fichero dimens.xml y crear ahí list_space
        recView.addItemDecoration(new SpaceItemDecoration(this,
                R.dimen.list_space, true, true));
        recViewFav.addItemDecoration(new SpaceItemDecoration(this, R.dimen.list_space, true, true));

        // Con esto el tamaño del recyclerwiew no cambiará
        recView.setHasFixedSize(true);
        recViewFav.setHasFixedSize(true);
        // Creo un layoutmanager para el recyclerview
        LinearLayoutManager llm = new LinearLayoutManager(this);
        LinearLayoutManager mmm = new LinearLayoutManager(this);
        recView.setLayoutManager(llm);
        recViewFav.setLayoutManager(mmm);
        server = new ServidorPHP();
        monedasFavoritas = new ArrayList<>();
        fav = new ArrayList<>();
        /////////////////////////////
        String fechita = server.obtenerFechaUnix();
        System.out.println("Obtener fecha -----> "+fechita);
        /////////////////////////////

        //obtenemos las monedas del servidor
        try {
            monedas = server.obtenerMonedas(valor);

            fav = server.ObtenerFav();

            System.out.println("obtenemos las monedas del json");
        } catch (ServidorPHPException e) {
            System.out.println("Error obteniendo las monedas -> " + e.toString());
        }


        //Configuramos el TabHost
        //tabspec 1
        th.setup();
        TabHost.TabSpec ts1 = th.newTabSpec("Tab1");
        ts1.setIndicator("All COINS");
        ts1.setContent(R.id.tab1);
        th.addTab(ts1);
        //tabspec 2
        th.setup();
        TabHost.TabSpec ts2 = th.newTabSpec("Tab2");
        ts2.setIndicator("FAVORITES");
        ts2.setContent(R.id.tab2);
        th.addTab(ts2);
        //ponemos por defecto el color del tabhost 1
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


        for (int i = 0; i<monedas.size(); i++)
        {
            for (int f = 0; f<fav.size(); f++)
            {
                if(monedas.get(i).getSymbol().equals(fav.get(f).getCripto()))
                {
                    monedas.get(i).setLike(true);
                    moneda a = monedas.get(i);
                    a.setLike(true);
                    monedasFavoritas.add(a);
                }
            }
        }

        if(valor2.equals("Market Cap")) {
            adaptador = new adaptador_lista_monedas(this, monedas, this, valor, valor1);
            adaptador.refrescar();
            recView.setAdapter(adaptador);
        }else if(valor2.equals("Nombre"))
        {
            ArrayList<moneda> limpias = new ArrayList();
            ArrayList<moneda> arrayordenado = new ArrayList();
            for (int i = 0; i < monedas.size(); i++ )
            {
                if(monedas.get(i).getNombre().trim().toString().equals("null"))
                {
                    System.out.println("este valor es nulo");
                }else {
                    limpias.add(monedas.get(i));
                }
            }
            String[] pornombre = new String[limpias.size()];

            for (int i = 0; i<limpias.size(); i++)
            {
                pornombre[i] = limpias.get(i).getNombre();
            }
            Arrays.sort(pornombre);

            for(int f = 0; f<limpias.size(); f++ )
            {
                String nombre = pornombre[f];
                for (int i = 0; i < limpias.size(); i++)
                {
                    if (nombre.equals(limpias.get(i).getNombre())) {
                        arrayordenado.add(limpias.get(i));
                    }

                }
            }

            adaptador = new adaptador_lista_monedas(this, arrayordenado, this, valor, valor1);
            adaptador.refrescar();
            recView.setAdapter(adaptador);

        }else if(valor2.equals("Precio"))
        {
            ArrayList<moneda> limpias = new ArrayList();
            ArrayList<moneda> arrayordenado = new ArrayList();
            for (int i = 0; i < monedas.size(); i++ )
            {
                if(monedas.get(i).getPrice_usd().trim().toString().equals("null"))
                {
                    System.out.println("este valor es nulo");
                }else {
                    limpias.add(monedas.get(i));
                }
            }
            Double[] porprecio = new Double[limpias.size()];

            for (int i = 0; i<limpias.size(); i++)
            {
                porprecio[i] = Double.parseDouble(limpias.get(i).getPrice_usd());
            }
            Arrays.sort(porprecio);


            Double [] porprecio1 = new Double[limpias.size()];
            int numero = 0;
            for (int i = porprecio.length-1 ; i>=0; i--)
            {
                porprecio1[numero] = porprecio[i];
                numero++;
            }

            for(int f = 0; f<limpias.size(); f++ )
            {
                Double precio =porprecio[f];
                for (int i = 0; i < limpias.size(); i++)
                {
                    if (precio == Double.parseDouble(limpias.get(i).getPrice_usd())) {
                        arrayordenado.add(limpias.get(i));
                    }

                }
            }


            adaptador = new adaptador_lista_monedas(this, arrayordenado, this, valor, valor1);
            adaptador.refrescar();
            recView.setAdapter(adaptador);
        }

        adaptadorfav = new adaptador_lista_favoritos(this, monedasFavoritas, this, valor, valor1);
        adaptadorfav.refrescar();
        recViewFav.setAdapter(adaptadorfav);


        // Iniciar la tarea asíncrona al revelar el indicador
        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        new HackingBackgroundTask().execute();
                    }
                }
        );


        // Iniciar la tarea asíncrona al revelar el indicador
        refreshLayoutfav.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        new HackingBackgroundTask2().execute();
                    }
                }
        );


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            Intent intent=new Intent(this,MainActivity.class);
            finish();
            startActivity(intent);

        } else if (id == R.id.heroes) {
            Intent intent=new Intent(this,heroes_zeroes.class);
            startActivity(intent);

        } else if (id == R.id.data) {
            Intent intent=new Intent(this,Conversor.class);
            startActivity(intent);

        } else if (id == R.id.info) {
            Intent intent=new Intent(this,info_app.class);
            startActivity(intent);

        }
        else if (id == R.id.alert) {
            Intent intent=new Intent(this,activity_alertas.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * opciones de menu para añadir el buscador y darle funcionalidad
     * @param menu del toolbar
     * @return verdadero o falso dependiendo de la acciónn en la toolbar
     */
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        final MenuItem menuItem = menu.findItem(R.id.search);

        searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!searchView.isIconified())
                {
                    searchView.setIconified(true);
                }
                menuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final List<moneda> filteredmodelist = filter(monedas, newText);
                adaptador.setfilter(filteredmodelist);
                return true;
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.valor:
                System.out.println("HAS PULSADO EL CAMBIO DE MONEDA");
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getResources().getString(R.string.dialog1));
                builder.setItems(R.array.conversiones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ProgressDialog mDialog = new ProgressDialog(MainActivity.this);
                        mDialog.setMessage(getResources().getString(R.string.cargadialog));
                        mDialog.show();
                        Intent intent=new Intent(MainActivity.this,MainActivity.class);
                        intent.putExtra("valor", conversiones[i]);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this,String.format("Has elegido %s", conversiones[i]), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                Dialog dialog = builder.create();
                dialog.show();
                return true;

            case R.id.porcentaje:
                System.out.println("HAS PULSADO EL CAMBIO DE PORCENTAJE");
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setTitle(getResources().getString(R.string.dialog2));
                builder1.setItems(R.array.porcent, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ProgressDialog mDialog = new ProgressDialog(MainActivity.this);
                        mDialog.setMessage(getResources().getString(R.string.cargadialog));
                        mDialog.show();
                        Intent intent=new Intent(MainActivity.this,MainActivity.class);
                        intent.putExtra("porcent", porcent[i]);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this,String.format("Has elegido %s", porcent[i]), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                Dialog dialog1 = builder1.create();
                dialog1.show();
                return true;
            case R.id.ordenar:
                System.out.println("HAS PULSADO EL CAMBIO DE ORDEN");
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setTitle(getResources().getString(R.string.dialog3));
                builder2.setItems(R.array.ordenar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ProgressDialog mDialog = new ProgressDialog(MainActivity.this);
                        mDialog.setMessage(getResources().getString(R.string.cargadialog));
                        mDialog.show();
                        Intent intent=new Intent(MainActivity.this,MainActivity.class);
                        intent.putExtra("ordenar", ordenar[i]);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this,String.format("Has elegido %s", ordenar[i]), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                Dialog dialog2 = builder2.create();
                dialog2.show();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    /**
     * metodo para filtrar la lista optimizada
     * @param p1 lista de monedas
     * @param query consulta en la toolbar
     * @return lista resultante de la consulta
     */
    private List<moneda> filter(List<moneda> p1, String query)
    {
        query = query.toLowerCase();
        final List<moneda> filteredModeList = new ArrayList<>();
        for (moneda model:p1)
        {
            final String text = model.getNombre().toLowerCase();
            if(text.startsWith(query))
            {
                filteredModeList.add(model);
            }
        }
        return filteredModeList;
    }



    /**
     * clase para actualizar la lista al refrescar
     */
    private class HackingBackgroundTask extends AsyncTask<Void, Void, List<moneda>> {

        static final int DURACION = 500; // 1 segundo de carga

        @Override
        protected List doInBackground(Void... params) {

            try {
                monedas = server.obtenerMonedas(valor);
                fav = server.ObtenerFav();

                for(int i = 0; i<monedas.size(); i++)
                {
                    for (int f = 0; f<fav.size(); f++)
                    {
                        if(monedas.get(i).getSymbol().equals(fav.get(f).getCripto()))
                        {
                            monedas.get(i).setLike(true);
                        }
                    }
                }

                System.out.println("obtenemos las monedas del json");
            } catch (ServidorPHPException e) {
                System.out.println("Error obteniendo las monedas -> " + e.toString());
            }

            // Simulación de la carga de items
            try {
                Thread.sleep(DURACION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Retornar en nuevos elementos para el adaptador
            return monedas;
        }

        @Override
        protected void onPostExecute(List result) {
            super.onPostExecute(result);

            // Añadir elementos nuevos
            adaptador.addAll((ArrayList<moneda>) result);

            // Parar la animación del indicador
            refreshLayout.setRefreshing(false);
        }

    }/**
     * clase para actualizar la lista de favoritos al refrescar
     */
    private class HackingBackgroundTask2 extends AsyncTask<Void, Void, List<moneda>> {

        static final int DURACION = 1000; // 1 segundo de carga

        @Override
        protected List doInBackground(Void... params) {

            try {
                monedasFavoritas = new ArrayList<>();
                monedas = server.obtenerMonedas(valor);
                fav = server.ObtenerFav();
                System.out.println("obtenemos las monedas del json");
            } catch (ServidorPHPException e) {
                System.out.println("Error obteniendo las monedas -> " + e.toString());
            }

            // Simulación de la carga de items
            try {
                Thread.sleep(DURACION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; i<monedas.size(); i++)
            {
                for (int f = 0; f<fav.size(); f++)
                {
                    if(monedas.get(i).getSymbol().equals(fav.get(f).getCripto()))
                    {
                        moneda a = monedas.get(i);
                        monedasFavoritas.add(a);
                    }
                }
            }

            for (int i = 0; i<monedasFavoritas.size(); i++)
            {
                System.out.println("favoritas ->>>>>>>>"+monedasFavoritas.get(i).getSymbol());
            }

            // Retornar en nuevos elementos para el adaptador
            return monedasFavoritas;
        }

        @Override
        protected void onPostExecute(List result) {
            super.onPostExecute(result);

            // Añadir elementos nuevos
            adaptadorfav.addAll((ArrayList<moneda>) result);

            // Parar la animación del indicador
            refreshLayoutfav.setRefreshing(false);

        }


    }


}
