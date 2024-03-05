package com.main.dietidealsclient.Activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.EditText;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;

import com.main.dietidealsclient.R;
import com.main.dietidealsclient.Requesters.AsteRequester;

public class CercaAsteActivity extends ComponentActivity {

    public CercaAsteActivity(){
        AsteRequester asteRequester = new AsteRequester();
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_aste);
        Bundle datipassati = getIntent().getExtras();
        String tipo = datipassati.getString("TIPO");
        Log.e("TIPO PASSATO",tipo);

        findViewById(R.id.search_btn).setOnClickListener(view -> {
            search();
        });

        findViewById(R.id.search_btton_back).setOnClickListener(view -> {

        });
    }


    private void search(){
        String keyword = ((EditText)findViewById(R.id.search_keyword_edit_text)).getText().toString();
//         (Spinner)(findViewById(R.id.search_type)).get
//        findViewById(R.id.search_categoria)
    }
}
