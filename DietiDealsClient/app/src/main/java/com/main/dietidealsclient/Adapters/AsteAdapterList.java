package com.main.dietidealsclient.Adapters;



import android.annotation.SuppressLint;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.dietidealsclient.Controller.AsteController;
import com.main.dietidealsclient.Model.Asta;
import com.main.dietidealsclient.Model.AstaClassica;
import com.main.dietidealsclient.Model.AstaInversa;
import com.main.dietidealsclient.Model.AstaSilenziosa;
import com.main.dietidealsclient.Model.Offerta;
import com.main.dietidealsclient.R;
import com.main.dietidealsclient.RecyclerAsteInterface;
import com.main.dietidealsclient.Utility.LoggedUser;
import com.main.dietidealsclient.Utility.MyException;

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
        asta.setOfferte(new AsteController().getOfferteByAsta(asta.getId()));
        Offerta bestOffer = asta.getBestOffer();
        Log.e("list", "onBindViewHolder" + position);
        holder.nome.setText(asta.getNomeProdotto());
        holder.tipo.setText(asta.getTypeAsString());

        //Gestione det1
        if (asta instanceof AstaSilenziosa silenziosa) {
            holder.det1.setText("");
            if(silenziosa.getScaduta())
                holder.det1.setText("Da accettare");
        }
        else if (asta instanceof AstaClassica classica){
            if (classica.getScaduta()) {
                holder.det1.setText("Scaduta!");
            } else {
                if(bestOffer.getValore() > 0)
                    holder.det1.setText(bestOffer.getValoreAsString());
                else
                    holder.det1.setText(classica.getMinPriceAsString());
            }
        }
        else if (asta instanceof AstaInversa inversa) {
            if (inversa.getScaduta()) {
                holder.det1.setText("Scaduta!");
            } else {
                if(bestOffer.getValore() > 0)
                    holder.det1.setText(bestOffer.getValoreAsString());
                else
                    holder.det1.setText(inversa.getMinPriceAsString());
            }
        }

        holder.det2.setText(asta.getDurata());
        holder.image.setOnClickListener(view -> {
            holder.notify();
        });
        //TODO Gestione immagine
        holder.image.setImageResource(R.drawable.add_image);
//      holder.image.setImageDrawable(asta.getImg());
    }

    @Override
    public int getItemCount() {
        if(data==null){
            return 0;
        }else {
            return data.size();
        }
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
                    if(pos != RecyclerView.NO_POSITION) {
                        try {
                            recyclerAsteInterface.onItemClick(pos);
                        } catch (MyException e) {
                            Toast.makeText(view.getContext(), "ERROR", Toast.LENGTH_LONG).show();

                        }
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        recyclerAsteInterface.onItemClick(getAdapterPosition()); // passing click to interface
                    } catch (MyException e) {
                        Toast.makeText(view.getContext(), "ERROR", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
