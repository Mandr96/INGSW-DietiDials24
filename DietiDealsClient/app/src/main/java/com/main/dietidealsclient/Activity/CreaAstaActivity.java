package com.main.dietidealsclient.Activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;

import com.main.dietidealsclient.Controller.AsteController;
import com.main.dietidealsclient.R;

public class CreaAstaActivity extends ComponentActivity {

    AsteController asteController;

    public CreaAstaActivity(){
        asteController = new AsteController();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_crea_asta);

//        findViewById(R.id.crea_btn).setOnClickListener(view -> {
//            try {
//                InserisciAsta();
//            } catch (InterruptedException e) {
//                Toast.makeText(this, "Inpossibile inserire asta",Toast.LENGTH_LONG).show();
//            }
//        });
    }

    private void InserisciAsta() throws InterruptedException {
//        String name = ((EditText)findViewById(R.id.new_articolo_name)).getText().toString();
//        String description = ((EditText)findViewById(R.id.new_articolo_description)).getText().toString();
//        String cat = ((Spinner)findViewById(R.id.new_articolo_cat)).getSelectedItem().toString(); //TODO da provare
//        String type = ((Spinner)findViewById(R.id.new_articolo_type)).getSelectedItem().toString(); //TODO da provare
//        String tmpStr = ((EditText)findViewById(R.id.new_articolo_minPrice)).getText().toString();
//        Float minPrice = Float.parseFloat(tmpStr);
//
//        //TODO controlli
//
//        asteController.createNewAsta(null,name,description,cat,null,type,minPrice);
    }
}
