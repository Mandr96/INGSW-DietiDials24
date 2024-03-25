package com.main.dietidealsclient.Adapters;

import android.annotation.SuppressLint;

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
import com.main.dietidealsclient.Model.Notifica;
import com.main.dietidealsclient.Model.Offerta;
import com.main.dietidealsclient.R;
import com.main.dietidealsclient.RecyclerAsteInterface;
import com.main.dietidealsclient.Utility.LoggedUser;

import java.util.List;

public class NotificheAdapterList extends RecyclerView.Adapter<NotificheAdapterList.NotificheHolder> {
    private RecyclerAsteInterface recyclerAsteInterface;

    private List<Notifica> list;

    public NotificheAdapterList(List<Notifica> data, RecyclerAsteInterface recyclerAsteInterface) {
        this.list = data;
        this.recyclerAsteInterface = recyclerAsteInterface;
    }

    public List<Notifica> getNotificheList() {
        return list;
    }

    @NonNull
    @Override
    public NotificheHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_notifica, parent, false);
        view.getLayoutParams().width = parent.getWidth();
        return new NotificheHolder(view,recyclerAsteInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificheHolder holder, int position) {
        Notifica not = list.get(position);
        holder.oggettoView.setText(not.getOggetto());
        holder.testoView.setText(not.getTesto());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setClickListener(RecyclerAsteInterface context) {
        this.recyclerAsteInterface = context;
    }

    public static class NotificheHolder extends RecyclerView.ViewHolder {

        private TextView oggettoView;
        private TextView testoView;

        public NotificheHolder(View itemView, RecyclerAsteInterface recyclerAsteInterface) {
            super(itemView);
            oggettoView = itemView.findViewById(R.id.notifica_oggetto);
            testoView = itemView.findViewById(R.id.notifica_testo);

            itemView.setOnClickListener(view -> {
                if(recyclerAsteInterface != null) {
                    int pos = getAbsoluteAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION)
                        recyclerAsteInterface.onItemClick(pos);
                }
            });
        }
    }
}

