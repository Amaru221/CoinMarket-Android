package com.example.agd19.barra_busqueda;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agd19 on 09/03/2018.
 */
public class adaptador_lista_favoritos extends RecyclerView.Adapter<adaptador_lista_favoritos.HolderTitular> //implements AdapterView.OnItemClickListener
 {

     /**
     * Clase interna equivalente al holder de los elementos
     */
            public class HolderTitular extends RecyclerView.ViewHolder //implements View.OnClickListener
          {

              private TextView tvnombrei;
              private TextView tvid;
              private TextView tvalorusd;
              private TextView tvnvalor;
              private TextView tvmarketcap;
              private TextView tvnmarketcap;
              private TextView tvpercent24h;
              private TextView tvsymbol;
              private TextView tvrank;
              private String identificador;
              private String simbolo;
              private ImageButton botonDelete;



              HolderTitular(View ItemView)
              {
                  super(ItemView);
                  tvid = (TextView) itemView.findViewById(R.id.tvid);
                  tvnombrei = (TextView) itemView.findViewById(R.id.tvnombrei);
                  tvalorusd = (TextView) itemView.findViewById(R.id.tvalor);
                  tvmarketcap = (TextView) itemView.findViewById(R.id.tvmarketcap);
                  tvpercent24h = (TextView) itemView.findViewById(R.id.tvpercent24h);
                  botonDelete = (ImageButton) itemView.findViewById(R.id.ibdelete);
                  tvrank = (TextView) itemView.findViewById(R.id.tvrank);
                  identificador = " ";
                  simbolo = " ";
                  itemView.setOnClickListener(new View.OnClickListener() {

                      @Override
                      public void onClick(View v) {
                          Intent intent=new Intent(contexto,activity_detalles.class);
                          intent.putExtra("id", tvid.getText());
                          intent.putExtra("symbol",simbolo);
                          contexto.startActivity(intent);

                      }
                  });

              }

          }


          //Atributos de la clase adaptador_lista_monedas
            private ArrayList<moneda> favoritos;
            private Context contexto;
            private MainActivity principal;
            private ServidorPHP server;
            private String valor;
            private String porcent;

            public adaptador_lista_favoritos(Context contexto, ArrayList<moneda> pa, MainActivity p, String valor, String percent)
            {
                this.contexto = contexto;
                this.favoritos = pa;
                this.principal = p;
                this.valor = valor;
                this.porcent = percent;
            }

            /**
             * agrega los datos que queremos mostrar
             * @param monedas en este caso las favoritos
             */
            public void add(ArrayList<moneda> monedas)
            {
                monedas.clear();
                monedas.addAll(monedas);
            }

            /**
             * actualiza los datos del reciclerview
             */
            public void refrescar()
            {
                notifyDataSetChanged();
            }

             public void addAll(ArrayList<moneda> monedas){
                 this.favoritos.clear();
                 this.favoritos =monedas;
                 refrescar();
             }

            @Override
            public HolderTitular onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_fav, parent, false);
                HolderTitular pvh = new HolderTitular(v);

                return pvh;
            }

            @Override
            public void onBindViewHolder(HolderTitular holder, final int position) {

                char c24 = favoritos.get(position).getPercent_change_24h().charAt(0);
                char c1 = favoritos.get(position).getPercent_change_1h().charAt(0);
                char c7 = favoritos.get(position).getPercent_change_7d().charAt(0);
                 holder.tvid.setText(favoritos.get(position).getId());
                 holder.simbolo = favoritos.get(position).getSymbol();
                holder.tvnombrei.setText(favoritos.get(position).getNombre()+"("+ favoritos.get(position).getSymbol()+")");
                if(valor.equals("USD")) {
                    holder.tvalorusd.setText(" $ " + favoritos.get(position).getPrice_usd());
                    holder.tvmarketcap.setText(" $ " + favoritos.get(position).getMarket_cap_usd());
                }else if(valor.equals("EUR")){
                    holder.tvalorusd.setText(" € " + favoritos.get(position).getValue());
                    holder.tvmarketcap.setText(" € " + favoritos.get(position).getMarketcap());
                }else if(valor.equals("ZAR")){
                    holder.tvalorusd.setText(" R " + favoritos.get(position).getValue());
                    holder.tvmarketcap.setText(" R " + favoritos.get(position).getMarketcap());
                }
                holder.tvrank.setText(favoritos.get(position).getRank());
                holder.botonDelete.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            server = new ServidorPHP();
                            server.EliminarFav(favoritos.get(position).getSymbol());
                        }
                    });

                if(porcent.equals("24h")) {
                    if ('-' == c24) {
                        holder.tvpercent24h.setText(favoritos.get(position).getPercent_change_24h() + "%");
                        holder.tvpercent24h.setTextColor(Color.RED);
                    } else {
                        holder.tvpercent24h.setText("+" + favoritos.get(position).getPercent_change_24h() + "%");
                        holder.tvpercent24h.setTextColor(Color.GREEN);
                    }
                }else if(porcent.equals("1h")) {
                    if ('-' == c1) {
                        holder.tvpercent24h.setText(favoritos.get(position).getPercent_change_1h() + "%");
                        holder.tvpercent24h.setTextColor(Color.RED);
                    } else {
                        holder.tvpercent24h.setText("+" + favoritos.get(position).getPercent_change_1h() + "%");
                        holder.tvpercent24h.setTextColor(Color.GREEN);
                    }
                }else if(porcent.equals("7d")) {
                    if ('-' == c7) {
                        holder.tvpercent24h.setText(favoritos.get(position).getPercent_change_7d() + "%");
                        holder.tvpercent24h.setTextColor(Color.RED);
                    } else {
                        holder.tvpercent24h.setText("+" + favoritos.get(position).getPercent_change_7d() + "%");
                        holder.tvpercent24h.setTextColor(Color.GREEN);
                    }
                }

            }

            @Override
            public int getItemCount() {

                     return favoritos.size();

            }

            public void setfilter(List<moneda> listmoneda)
            {
                favoritos = new ArrayList<>();
                favoritos.addAll(listmoneda);
                notifyDataSetChanged();
            }

            @Override
            public void onAttachedToRecyclerView(RecyclerView recyclerView)
            {
                super.onAttachedToRecyclerView(recyclerView);
            }


}
