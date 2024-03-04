package com.main.dietidealsclient.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.ComponentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.main.dietidealsclient.AsteAdapterList;
import com.main.dietidealsclient.Controller.AsteController;
import com.main.dietidealsclient.Model.Asta;
import com.main.dietidealsclient.Model.Utente;
import com.main.dietidealsclient.R;
import com.main.dietidealsclient.Requesters.AsteRequester;
import com.main.dietidealsclient.Utility.LoggedUser;

import java.util.ArrayList;
import java.util.List;

public class HomeCompratoreActivity extends ComponentActivity {

    private RecyclerView recyclerView;
    private AsteAdapterList adapter;
    private AsteController asteController;

    public HomeCompratoreActivity(){
        asteController = new AsteController();
    }

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_home_compratore);
        inizializeProfile();
        updateAsteList();



        findViewById(R.id.home_compratore_cerca_aste).setOnClickListener(view -> {
            try {
                asteController.getAstePartecipateDaUtente();
            } catch (InterruptedException e) {
                Log.e("AsteController - getAstePartecipateDaUtente()","gg");
            }
//            gotoCercaAsteActivity();
        });

        findViewById(R.id.home_compratore_crea_asta_inversa).setOnClickListener(view -> {
            gotoCercaCreaAstaActivity();
        });
    }



    private void updateAsteList() {
        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Asta> data = asteController.getAsteUtente(AsteController.tipoHomepage.VENDITORE);

        adapter = new AsteAdapterList(data);
        recyclerView.setAdapter(adapter);
    }

    /** Se il profilo non contiene Nome manda alla pagina modifica profilo */
    private void inizializeProfile() {
        Utente loggedUser = LoggedUser.getInstance().getLoggedUser();
        if(loggedUser.getNome() == null){
            goToEditProfileActivity();
        }
    }

    private void goToEditProfileActivity() {
        Intent myIntent = new Intent(HomeCompratoreActivity.this, EditProfileActivity.class);
        HomeCompratoreActivity.this.startActivity(myIntent);
        finish();
    }

    private void gotoCercaAsteActivity() {
        Intent myIntent = new Intent(HomeCompratoreActivity.this, CercaAsteActivity.class);
        myIntent.putExtra("TIPO","COMPRATORE");
        HomeCompratoreActivity.this.startActivity(myIntent);
        finish();
    }

    private void gotoCercaCreaAstaActivity() {
        Intent myIntent = new Intent(HomeCompratoreActivity.this, CreaAstaActivity.class);
        myIntent.putExtra("TIPO","COMPRATORE");
        HomeCompratoreActivity.this.startActivity(myIntent);
        finish();
    }
}
