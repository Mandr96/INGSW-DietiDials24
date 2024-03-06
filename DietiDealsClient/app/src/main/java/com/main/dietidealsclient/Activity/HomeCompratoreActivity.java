package com.main.dietidealsclient.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.ComponentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.main.dietidealsclient.AsteAdapterList;
import com.main.dietidealsclient.Controller.AsteController;
import com.main.dietidealsclient.Controller.TipoAccount;
import com.main.dietidealsclient.Model.Asta;
import com.main.dietidealsclient.Model.Utente;
import com.main.dietidealsclient.R;
import com.main.dietidealsclient.Utility.LoggedUser;

import java.util.List;
import java.util.Objects;

public class HomeCompratoreActivity extends ComponentActivity {

    private RecyclerView recyclerViewPrimo, recyclerViewSecondo;
    private AsteAdapterList adapterPrimo, adapterSecondo;
    private AsteController asteController;
    private TipoAccount userType;
    public HomeCompratoreActivity(){
        asteController = new AsteController();
    }

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_home_compratore);

        recyclerViewPrimo = findViewById(R.id.recycle_view_aste_normali);
        recyclerViewSecondo = findViewById(R.id.recycle_view_aste_inverse);

        setUserType();

        inizializeProfile();
        updateAsteLists();

        findViewById(R.id.home_compratore_cerca_aste).setOnClickListener(view -> {
            gotoCercaAsteActivity();
        });

        findViewById(R.id.home_compratore_crea_asta_inversa).setOnClickListener(view -> {
            gotoCreaAstaActivity();
        });
    }

    private void setUserType() {
        try{
            userType = ((Objects.equals(Objects.requireNonNull(getIntent().getExtras()).getString("TIPO"), "VENDITORE")) ? TipoAccount.VENDITORE : TipoAccount.COMPRATORE);
        } catch (NullPointerException e){
            userType = TipoAccount.COMPRATORE;
        }
        Log.d("setUserType" , userType.toString());
    }

    /**
     * Compratore
     *      lista 1 -> Aste a cui ho partecipato come compratore
     *      lista 2 -> Aste create come comrpatore (inversa)
     *
     * Venditore
     *      lista 1 -> Aste create da me come venditore
     *      lista 2 -> Aste a cui ho partecipato come venditore (inversa)
     */

    private void updateAsteLists() {
        if(userType.equals(TipoAccount.COMPRATORE)) {
            updateAsteListpartecipate(recyclerViewPrimo, adapterPrimo);
            updateAsteListCreate(recyclerViewSecondo,adapterSecondo);
        } else if (userType.equals(TipoAccount.VENDITORE)) {
            updateAsteListCreate(recyclerViewPrimo,adapterPrimo);
            updateAsteListpartecipate(recyclerViewSecondo, adapterSecondo);
        }

        //TODO findViewById con recycle_view_aste_normali e l altro
    }

    //Aste a cui hai partecipato
    private void updateAsteListCreate(RecyclerView recyclerView, AsteAdapterList adapterList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Asta> data = asteController.getAsteUtente(userType);
        Log.d("MyDebug userType", userType.toString());
        Log.d("MyDebug updateAsteListCreate", data.toString());

        adapterList = new AsteAdapterList(data);
        recyclerView.setAdapter(adapterList);
    }

    private void updateAsteListpartecipate(RecyclerView recyclerView, AsteAdapterList adapterList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Asta> astePartecipate = asteController.getAstePartecipate(userType);
        Log.d("MyDebug userType", userType.toString());
        Log.d("MyDebug updateAsteListpartecipate", astePartecipate.toString());

        adapterList = new AsteAdapterList(astePartecipate);
        recyclerView.setAdapter(adapterList);
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
        myIntent.putExtra("TIPO",userType);
        HomeCompratoreActivity.this.startActivity(myIntent);
        finish();
    }

    private void gotoCreaAstaActivity() {
        Intent myIntent = new Intent(HomeCompratoreActivity.this, CreaAstaActivity.class);
        myIntent.putExtra("TIPO",userType);
        HomeCompratoreActivity.this.startActivity(myIntent);
        finish();
    }
}
