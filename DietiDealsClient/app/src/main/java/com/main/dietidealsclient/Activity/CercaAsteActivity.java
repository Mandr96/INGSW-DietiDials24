package com.main.dietidealsclient.Activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;

public class CercaAsteActivity extends ComponentActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Bundle datipassati = getIntent().getExtras();
        String tipo = datipassati.getString("tipo");
        Log.e("TIPO PASSATO",tipo);
    }
}
