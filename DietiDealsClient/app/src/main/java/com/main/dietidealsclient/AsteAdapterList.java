package com.main.dietidealsclient;



import android.annotation.SuppressLint;

import android.graphics.Color;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.dietidealsclient.Model.Asta;
import com.main.dietidealsclient.Model.AstaClassica;
import com.main.dietidealsclient.Model.AstaInversa;
import com.main.dietidealsclient.Model.AstaSilenziosa;
import com.main.dietidealsclient.Model.Offerta;
import com.main.dietidealsclient.Utility.LoggedUser;

import java.time.Instant;
import java.util.List;

public class AsteAdapterList extends RecyclerView.Adapter<AsteAdapterList.ViewHolder> {
    private RecyclerAsteInterface recyclerAsteInterface;

    public List<Asta> getData() {
        return data;
    }

    private List<Asta> data;

    public AsteAdapterList(List<Asta> data, RecyclerAsteInterface recyclerAsteInterface) {
        this.data = data;
        this.recyclerAsteInterface = recyclerAsteInterface;
    }

    @NonNull
    @Override
    public AsteAdapterList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_asta, parent, false);
        view.getLayoutParams().width = parent.getWidth();
        return new ViewHolder(view, recyclerAsteInterface);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(AsteAdapterList.ViewHolder holder, int position) {
        Asta asta = data.get(position);
        Offerta bestOffer = asta.getBestOffer();
        Log.e("list", "onBindViewHolder" + position);
        holder.nome.setText(asta.getNomeProdotto());
        holder.tipo.setText(asta.getTypeAsString());


        if(asta instanceof AstaClassica classica) {
            holder.det1.setText(classica.getMinPrice().toString()+"€");
            if(bestOffer != null) {
                holder.det1.setText(bestOffer.getValore().toString() + "€");
                Log.d("MyDebug", "LOGGED USER pre" );
                Log.d("MyDebug", "LOGGED USER" + LoggedUser.getInstance().getLoggedUser().getEmail());
                //TODO bestOffer.getOwnerEmail() É NULL?
//                if (bestOffer.getOwnerEmail().equals(LoggedUser.getInstance().getLoggedUser().getEmail())){
//                    holder.det1.setTextColor(Color.red(1));
//                } else {
//                    holder.det1.setTextColor(Color.red(1));
//                }
            }
        }

        if (asta.getScaduta() && asta instanceof AstaInversa){
            holder.det1.setText("Da accettare");
        }

//        if (asta.getScaduta() && asta instanceof AstaInversa){
//            holder.det1.setText("Da accettare");
//        } else {
//            Offerta bestOff = asta.getBestOffer();
//            if (bestOff != null){
//                //TODO vari tipi di account e test colori
//                holder.det1.setText(String.valueOf(bestOff.getValore()));
//                if(bestOff.getOwnerEmail().equals(LoggedUser.getInstance().getLoggedUser().getEmail())){
//                    holder.det1.setTextColor(Color.red(1));
//                }else {
//                    holder.det1.setTextColor(Color.green(1));
//                }
//            }
//        }

        //CALCOLO DURATA
        long secs = asta.getScadenza().toInstant().minusSeconds(Instant.now().getEpochSecond()).getEpochSecond();
        long hours = secs/3600;
        holder.det2.setText(Math.floorDiv(hours, 24)+"d "+hours%24+"h");

        holder.image.setOnClickListener(view -> {
            Log.d("MyDebug", "Ho cliccato sull immagine");
            holder.notify();
        });

        //TODO Gestione immagine
        holder.image.setImageResource(R.drawable.add_image);
//        holder.image.setImageDrawable(asta.getImg());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setClickListener(RecyclerAsteInterface context) {
        this.recyclerAsteInterface = context;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nome;
        private TextView tipo;
        private  TextView det1, det2;
        private ImageView image;

        public ViewHolder(View itemView, RecyclerAsteInterface recyclerAsteInterface) {
            super(itemView);
            nome = itemView.findViewById(R.id.asta_nome);
            tipo = itemView.findViewById(R.id.asta_tipo);
            det1 = itemView.findViewById(R.id.asta_det1);
            det2 = itemView.findViewById(R.id.asta_det2);
            image = itemView.findViewById(R.id.asta_image);

            itemView.setOnClickListener(view -> {
                if(recyclerAsteInterface != null) {
                    int pos = getAbsoluteAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION)
                        recyclerAsteInterface.onItemClick(pos);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerAsteInterface.onItemClick(getAdapterPosition()); // passing click to interface
                }
            });
        }
    }
}
