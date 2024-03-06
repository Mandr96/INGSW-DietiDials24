package com.main.dietidealsclient.Activity;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;

import com.main.dietidealsclient.Controller.AsteController;
import com.main.dietidealsclient.Controller.TipoAccount;
import com.main.dietidealsclient.Model.Asta;
import com.main.dietidealsclient.R;

import java.util.ArrayList;
import java.util.List;

public class RisultatiRicercaActivity extends ComponentActivity {

    TipoAccount userType;
    String keyword;
    String categoria;
    String tipoAsta;
    int page;
    List<Asta> results;
    AsteController asteController;
    public RisultatiRicercaActivity() {
        asteController = new AsteController();
        page = 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_aste);

        //setUserType();
        //keyword = getIntent().getStringExtra("KEYWORD");
        //categoria = getIntent().getStringExtra("CATEGORIA");
        //tipoAsta = getIntent().getStringExtra("ASTA_TIPO");
        //results =  (ArrayList<Asta>)getIntent().getSerializableExtra("RESULTS");
        //Log.d("myDebug", "RESULTS: "+results);
    }

    private void setUserType() {
        if(getIntent().getStringExtra("TIPO").equals(TipoAccount.VENDITORE.toString())) {
            userType = TipoAccount.VENDITORE;
        }
        else {
            userType = TipoAccount.COMPRATORE;
        }
    }
}
