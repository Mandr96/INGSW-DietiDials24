package com.main.dietidealsclient;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.dietidealsclient.Model.Asta;

import java.util.List;

public class AsteAdapterList extends RecyclerView.Adapter<AsteAdapterList.ViewHolder> {


    private List<Asta> data;

    public AsteAdapterList(List<Asta> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public AsteAdapterList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("list", "onCreateViewHolder " + "viewType " + viewType);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_asta, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AsteAdapterList.ViewHolder holder, int position) {
        Asta asta = data.get(position);
        Log.e("list", "onBindViewHolder" + position);
        holder.nome.setText(asta.getNomeProdotto());
        holder.descrizione.setText(asta.getDescrizione());
        holder.det1.setText(asta.getCategoria());
        holder.det2.setText(asta.getScadenza().toString());
        //TODO Gestione immagine
        holder.image.setImageResource(R.drawable.ic_launcher_background);
//        holder.image.setImageDrawable(asta.getImg());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nome;
        private TextView descrizione;
        private  TextView det1, det2;
        private ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.asta_nome);
            descrizione = itemView.findViewById(R.id.asta_descrizione);
            det1 = itemView.findViewById(R.id.asta_det1);
            det2 = itemView.findViewById(R.id.asta_det2);
            image = itemView.findViewById(R.id.asta_image);
        }
    }
}
