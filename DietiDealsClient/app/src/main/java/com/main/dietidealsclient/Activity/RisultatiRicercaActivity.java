package com.main.dietidealsclient.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.main.dietidealsclient.Adapters.AsteAdapterList;
import com.main.dietidealsclient.Controller.AsteController;
import com.main.dietidealsclient.Controller.TipoAccount;
import com.main.dietidealsclient.Controller.UserProfileController;
import com.main.dietidealsclient.Model.Asta;
import com.main.dietidealsclient.Model.Offerta;
import com.main.dietidealsclient.R;
import com.main.dietidealsclient.RecyclerAsteInterface;
import com.main.dietidealsclient.Utility.Logger;

import java.util.ArrayList;
import java.util.List;

public class RisultatiRicercaActivity extends ComponentActivity {

    TipoAccount userType;
    String keyword;
    String categoria;
    String tipoAsta;
    ImageView backBtn;
    int page;
    List<Asta> results;
    AsteController asteController;
    public RisultatiRicercaActivity() {
        asteController = new AsteController();
        page = 0;
    }

    private RecyclerView recyclerView;
    private AsteAdapterList adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risultati_ricerca);

        keyword = getIntent().getStringExtra("KEYWORD");
        categoria = getIntent().getStringExtra("CATEGORIA");
        tipoAsta = getIntent().getStringExtra("ASTA_TIPO");
        results =  (ArrayList<Asta>)getIntent().getSerializableExtra("RESULTS");

        setUserType();
        backBtn = findViewById(R.id.search_btton_back);
        recyclerView = findViewById(R.id.risultati_recycler_view);
        ricercaSuValori();

        adapter.setClickListener(new RecyclerAsteInterface() {
            @Override
            public void onItemClick(int position) {
                Log.d("MyDebug" , "CLICCATO" + adapter.getData().get(position));
                showAstaDetails(adapter.getData().get(position));
            }
        });
        backBtn.setOnClickListener(view -> {
            Logger.log("RisultatiRicercaPage","'Indietro' premuto");
            goBack();
        });

        Log.d("myDebug", "RESULTS: "+results);
    }

    private void setUserType() {
        userType = (TipoAccount)getIntent().getSerializableExtra("TIPO");
    }

    private void ricercaSuValori() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Asta> data = results;
        adapter = new AsteAdapterList(data, this::onClickRecycler);
        recyclerView.setAdapter(adapter);
    }

    //COPIATO
    private void showAstaDetails(Asta asta) {
        Logger.log("RisultatiRicercaPage","Visualizza dettagli asta -> "+asta.getNomeProdotto());
        asta.setCreatore(new UserProfileController().getAstaOwner(asta.getId()));
        Intent myIntent = new Intent(RisultatiRicercaActivity.this, AstaDetailsActivity.class);
        myIntent.putExtra("ASTA", asta);
        Offerta bestOff = asta.getBestOffer();
        if (bestOff != null){
            myIntent.putExtra("PRICE", asta.getBestOffer().getValore());
        } else {
            myIntent.putExtra("PRICE", 0);
        }
        //TODO getYourOffer
        myIntent.putExtra("OFFER", 0);
        RisultatiRicercaActivity.this.startActivity(myIntent);
    }

    public void onClickRecycler(int pos){
//        showAstaDetails(adapterPrimo.getData().get(pos));
    }

    public void goBack() {
        finish();
    }
}
