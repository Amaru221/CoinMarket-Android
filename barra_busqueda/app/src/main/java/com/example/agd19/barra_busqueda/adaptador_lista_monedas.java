package com.example.agd19.barra_busqueda;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agd19 on 09/03/2018.
 */
public class adaptador_lista_monedas extends RecyclerView.Adapter<adaptador_lista_monedas.HolderTitular> //implements AdapterView.OnItemClickListener
 {

     /**
     * Clase interna equivalente al holder de los elementos
     */
            public class HolderTitular extends RecyclerView.ViewHolder //implements View.OnClickListener
          {

              private TextView tvnombrei;
              private ImageView ivicon;
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
              private LikeButton botonStar;


              HolderTitular(View ItemView)
              {
                  super(ItemView);
                  tvid = (TextView) itemView.findViewById(R.id.tvid);
                  tvnombrei = (TextView) itemView.findViewById(R.id.tvnombrei);
                  tvalorusd = (TextView) itemView.findViewById(R.id.tvalor);
                  tvmarketcap = (TextView) itemView.findViewById(R.id.tvmarketcap);
                  tvpercent24h = (TextView) itemView.findViewById(R.id.tvpercent24h);
                  botonStar = (LikeButton) itemView.findViewById(R.id.like_button);
                  tvrank = (TextView) itemView.findViewById(R.id.tvrank);
                  ivicon = (ImageView) itemView.findViewById(R.id.ivicon);
                  simbolo = " ";
                  identificador = " ";
                  itemView.setOnClickListener(new View.OnClickListener() {

                      @Override
                      public void onClick(View v) {
                          Intent intent=new Intent(contexto,activity_detalles.class);
                          intent.putExtra("id", tvid.getText());
                          intent.putExtra("symbol", simbolo);
                          intent.putExtra("valor", valor);
                          contexto.startActivity(intent);

                      }
                  });
              }

          }

          //Atributos de la clase adaptador_lista_monedas
            private ArrayList<moneda> monedas;
            private Context contexto;
            private MainActivity principal;
            private ServidorPHP server;
            private String valor;
            private String porcent;

            public adaptador_lista_monedas(Context contexto, ArrayList<moneda> pa, MainActivity p, String valor, String porcent)
            {
                this.contexto = contexto;
                this.monedas = pa;
                this.principal = p;
                this.valor = valor;
                this.porcent = porcent;

            }

            /**
             * agrega los datos que queremos mostrar
             * @param monedas en este caso las monedas
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
                 this.monedas.clear();
                 this.monedas=monedas;
                 refrescar();
             }

            @Override
            public HolderTitular onCreateViewHolder(ViewGroup parent, int viewType) {
                     View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_monedas, parent, false);
                     HolderTitular pvh = new HolderTitular(v);

                return pvh;
            }

            @Override
            public void onBindViewHolder(final HolderTitular holder, final int position) {

                 char c24 = monedas.get(position).getPercent_change_24h().charAt(0);
                 char c1 = monedas.get(position).getPercent_change_1h().charAt(0);
                 char c7 = monedas.get(position).getPercent_change_7d().charAt(0);

                 holder.tvid.setText(monedas.get(position).getId());
                 holder.simbolo = monedas.get(position).getSymbol();
                holder.tvnombrei.setText(monedas.get(position).getNombre()+"("+monedas.get(position).getSymbol()+")");
                if(valor.equals("USD")) {
                    holder.tvalorusd.setText(" $ " + monedas.get(position).getPrice_usd());
                    holder.tvmarketcap.setText(" $ " + monedas.get(position).getMarket_cap_usd());
                }else if(valor.equals("EUR")){
                    holder.tvalorusd.setText(" € " + monedas.get(position).getValue());
                    holder.tvmarketcap.setText(" € " + monedas.get(position).getMarketcap());
                }else if(valor.equals("ZAR")){
                    holder.tvalorusd.setText(" R " + monedas.get(position).getValue());
                    holder.tvmarketcap.setText(" R " + monedas.get(position).getMarketcap());
                }
                if(holder.simbolo.toUpperCase().equals("ETH")){
                    holder.ivicon.setImageResource(R.drawable.ethereum);
                }
                if(holder.simbolo.toUpperCase().equals("BTC")){
                    holder.ivicon.setImageResource(R.drawable.coin);
                }
                if(holder.simbolo.toUpperCase().equals("XRP")){
                    holder.ivicon.setImageResource(R.drawable.ripple);
                }
                if(holder.simbolo.toUpperCase().equals("XMR")){
                    holder.ivicon.setImageResource(R.drawable.monero);
                }
                if(holder.simbolo.toUpperCase().equals("LTC")){
                    holder.ivicon.setImageResource(R.drawable.litecoin);
                }
                if(holder.simbolo.toUpperCase().equals("XLM")){
                    holder.ivicon.setImageResource(R.drawable.stellar_coin);
                }

                if(holder.simbolo.toUpperCase().equals("USDT")){
                    holder.ivicon.setImageResource(R.drawable.tether);
                }

                holder.tvrank.setText(monedas.get(position).getRank());

                if(monedas.get(position).getLike() == true)
                {
                    holder.botonStar.setLiked(true);
                }else
                {
                    holder.botonStar.setLiked(false);
                }

                holder.botonStar.setOnLikeListener(new OnLikeListener() {
                    @Override
                    public void liked(LikeButton likeButton) {

                        try {
                            server = new ServidorPHP();
                            server.InsertarFav(monedas.get(position).getSymbol());
                            //adaptador_fav.refrescar();
                        } catch (ServidorPHPException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void unLiked(LikeButton likeButton) {
                        server = new ServidorPHP();
                        server.EliminarFav(monedas.get(position).getSymbol());
                        //adaptador_fav.refrescar();

                    }
                });
                if(porcent.equals("24h")) {
                    if ('-' == c24) {
                        holder.tvpercent24h.setText(monedas.get(position).getPercent_change_24h() + "%");
                        holder.tvpercent24h.setTextColor(Color.RED);
                    } else {
                        holder.tvpercent24h.setText("+" + monedas.get(position).getPercent_change_24h() + "%");
                        holder.tvpercent24h.setTextColor(Color.GREEN);
                    }
                }else if(porcent.equals("1h")) {
                    if ('-' == c1) {
                        holder.tvpercent24h.setText(monedas.get(position).getPercent_change_1h() + "%");
                        holder.tvpercent24h.setTextColor(Color.RED);
                    } else {
                        holder.tvpercent24h.setText("+" + monedas.get(position).getPercent_change_1h() + "%");
                        holder.tvpercent24h.setTextColor(Color.GREEN);
                    }
                }else if(porcent.equals("7d")) {
                    if ('-' == c7) {
                        holder.tvpercent24h.setText(monedas.get(position).getPercent_change_7d() + "%");
                        holder.tvpercent24h.setTextColor(Color.RED);
                    } else {
                        holder.tvpercent24h.setText("+" + monedas.get(position).getPercent_change_7d() + "%");
                        holder.tvpercent24h.setTextColor(Color.GREEN);
                    }
                }

            }

            @Override
            public int getItemCount() {

                     return monedas.size();

            }

            public void setfilter(List<moneda> listmoneda)
            {
                monedas = new ArrayList<>();
                monedas.addAll(listmoneda);
                notifyDataSetChanged();
            }

            @Override
            public void onAttachedToRecyclerView(RecyclerView recyclerView)
            {
                super.onAttachedToRecyclerView(recyclerView);
            }


}
