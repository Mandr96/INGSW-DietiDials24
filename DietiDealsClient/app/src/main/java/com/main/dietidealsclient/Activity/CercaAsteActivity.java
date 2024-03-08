package com.main.dietidealsclient.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;

import com.main.dietidealsclient.Controller.AsteController;
import com.main.dietidealsclient.Controller.TipoAccount;
import com.main.dietidealsclient.Model.Asta;
import com.main.dietidealsclient.R;

import java.util.ArrayList;
import java.util.List;

public class CercaAsteActivity extends ComponentActivity {

    ImageView btnBack;
    EditText editTextKw;
    Spinner spinnerCat;
    Spinner spinnerType;
    TipoAccount userType;
    AsteController asteController;


    public CercaAsteActivity(){
        asteController = new AsteController();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_aste);

        btnBack = findViewById(R.id.search_btton_back);
        editTextKw = findViewById(R.id.search_keyword_edit_text);
        spinnerCat = findViewById(R.id.search_categoria);
        spinnerType = findViewById(R.id.search_type);
        inizializeSpinners();
//        userType = Objects.requireNonNull(getIntent().getExtras()).getString("TIPO");
        userType = (TipoAccount)getIntent().getSerializableExtra("TIPO");

        Log.d("MyDebug UserType" , userType.toString() + userType.equals(TipoAccount.VENDITORE));

        if(userType.equals(TipoAccount.VENDITORE)) {
            spinnerType.setVisibility(View.INVISIBLE);
            findViewById(R.id.type_text).setVisibility(View.INVISIBLE);
        }

        findViewById(R.id.search_btn).setOnClickListener(view -> {
            search();
        });
        findViewById(R.id.search_btton_back).setOnClickListener(view -> {
            goBack();
        });
    }


    private void search(){
        //TODO debug
        String keyword = editTextKw.getText().toString();
        String categoria = spinnerCat.getSelectedItem().toString().replace(" ","_");
        String type = "Asta"+spinnerType.getSelectedItem().toString();
        ArrayList<Asta> results = (ArrayList<Asta>) asteController.ricerca(keyword, categoria, type, 0, userType);
        if(results.isEmpty()) {
            Toast.makeText(this, "Nessun risultato trovato", Toast.LENGTH_LONG).show();
            return;
        }
        Intent myIntent = new Intent(CercaAsteActivity.this, RisultatiRicercaActivity.class);
        //myIntent.putExtra("TIPO", userType.toString());
        //myIntent.putExtra("KEYWORD", keyword);
        //myIntent.putExtra("CATEGORIA", categoria);
        //myIntent.putExtra("ASTA_TIPO", type);
        //myIntent.putExtra("RESULTS", results);
        CercaAsteActivity.this.startActivity(myIntent);
        finish();
    }

    private void inizializeSpinners() {
        List<String> list = List.of(getResources().getStringArray(R.array.categories));
        ArrayList<String> entries = new ArrayList<>(list);
        entries.add(0, "Tutte");
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, entries);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerCat.setAdapter(spinnerArrayAdapter);
        list = List.of(getResources().getStringArray(R.array.aste_types));
        entries = new ArrayList<>(list);
        entries.add(0, "Tutte");
        spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, entries);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(spinnerArrayAdapter);
    }

    private void goBack() {
        Intent myIntent = new Intent(CercaAsteActivity.this, HomeActivity.class);
        CercaAsteActivity.this.startActivity(myIntent);
        finish();
    }
}
