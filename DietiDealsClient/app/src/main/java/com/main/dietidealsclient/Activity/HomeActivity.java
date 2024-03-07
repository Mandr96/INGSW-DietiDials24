package com.main.dietidealsclient.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.ComponentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.main.dietidealsclient.AsteAdapterList;
import com.main.dietidealsclient.Controller.AsteController;
import com.main.dietidealsclient.Controller.TipoAccount;
import com.main.dietidealsclient.Model.Asta;
import com.main.dietidealsclient.Model.Utente;
import com.main.dietidealsclient.R;
import com.main.dietidealsclient.RecyclerAsteInterface;
import com.main.dietidealsclient.Utility.LoggedUser;

import java.util.List;

public class HomeActivity extends ComponentActivity {

    private RecyclerView recyclerViewPrimo, recyclerViewSecondo;
    private AsteAdapterList adapterPrimo, adapterSecondo;
    private AsteController asteController;
    private TipoAccount userType;
    public HomeActivity(){
        asteController = new AsteController();
    }

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_home);

        recyclerViewPrimo = findViewById(R.id.recycle_view_aste_normali);
        recyclerViewSecondo = findViewById(R.id.recycle_view_aste_inverse);
        setUserType();

        inizializeProfile();
        updateAsteLists();
        updateTesti();

        findViewById(R.id.home_compratore_cerca_aste).setOnClickListener(view -> {
            gotoCercaAsteActivity();
        });

        findViewById(R.id.home_compratore_crea_asta_inversa).setOnClickListener(view -> {
            gotoCreaAstaActivity();
        });

        findViewById(R.id.home_compratore_cambia_tipo).setOnClickListener(view -> {
            gotoChageAccountType();
        });


        RecyclerAsteInterface recyclerAsteInterfacePrimo = new RecyclerAsteInterface() {
            @Override
            public void onItemClick(int position) {

                Log.d("MyDebug" , "CLICCATO" + adapterPrimo.getData().get(position));
            }
        };
        adapterPrimo.setClickListener(recyclerAsteInterfacePrimo);

        RecyclerAsteInterface recyclerAsteInterfaceSecondo = new RecyclerAsteInterface() {
            @Override
            public void onItemClick(int position) {

                Log.d("MyDebug" , "CLICCATO" + adapterSecondo.getData().get(position));
            }
        };
        adapterSecondo.setClickListener(recyclerAsteInterfaceSecondo);
    }

    private void updateTesti() {
        if (userType.equals(TipoAccount.VENDITORE)) {
            ((TextView)findViewById(R.id.aste_title_text1)).setText("Le tue aste");
            ((TextView)findViewById(R.id.aste_title_text2)).setText("Le aste inverse a cui hai partecipato");
            ((Button)findViewById(R.id.home_compratore_cambia_tipo)).setText("Compra");
        } else {
            ((TextView)findViewById(R.id.aste_title_text1)).setText("Le aste a cui hai partecipato");
            ((TextView)findViewById(R.id.aste_title_text2)).setText("Le tue aste Inverse");
            ((Button)findViewById(R.id.home_compratore_cambia_tipo)).setText("Vendi");
        }

    }


    private void setUserType() {
        try{
            userType = (TipoAccount)getIntent().getSerializableExtra("TIPO");
        } catch (NullPointerException e){
            userType = TipoAccount.COMPRATORE;
        }
        if (userType == null){
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
            adapterPrimo = updateAsteListpartecipate(recyclerViewPrimo, this::onClickPrimoRecycler);
            adapterSecondo = updateAsteListCreate(recyclerViewSecondo, this::onClickSecondoRecycler);
        } else if (userType.equals(TipoAccount.VENDITORE)) {
            adapterPrimo = updateAsteListCreate(recyclerViewPrimo, this::onClickPrimoRecycler);
            adapterSecondo = updateAsteListpartecipate(recyclerViewSecondo, this::onClickSecondoRecycler);
        }
        Log.d("myDebug", "Primo: "+adapterPrimo.toString());
        Log.d("myDebug", "Secondo: "+adapterSecondo.toString());

        //TODO findViewById con recycle_view_aste_normali e l altro
    }

    //Aste a cui hai partecipato
    private AsteAdapterList updateAsteListCreate(RecyclerView recyclerView, RecyclerAsteInterface recyclerAsteInterface) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Asta> data = asteController.getAsteUtente(userType);
        Log.d("MyDebug userType", userType.toString());
        Log.d("MyDebug updateAsteListCreate", data.toString());

        AsteAdapterList adapterList = new AsteAdapterList(data, recyclerAsteInterface);
        recyclerView.setAdapter(adapterList);
        return adapterList;
    }

    private AsteAdapterList updateAsteListpartecipate(RecyclerView recyclerView, RecyclerAsteInterface recyclerAsteInterface) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Asta> astePartecipate = asteController.getAstePartecipate(userType);
        Log.d("MyDebug userType", userType.toString());
        Log.d("MyDebug updateAsteListpartecipate", astePartecipate.toString());

        AsteAdapterList adapterList = new AsteAdapterList(astePartecipate, recyclerAsteInterface);
        recyclerView.setAdapter(adapterList);
        return adapterList;
    }

    /** Se il profilo non contiene Nome manda alla pagina modifica profilo */
    private void inizializeProfile() {
        Utente loggedUser = LoggedUser.getInstance().getLoggedUser();
        if(loggedUser.getNome() == null){
            goToEditProfileActivity();
        }
    }

    private void goToEditProfileActivity() {
        Intent myIntent = new Intent(HomeActivity.this, EditProfileActivity.class);
        HomeActivity.this.startActivity(myIntent);
    }

    private void gotoCercaAsteActivity() {
        Intent myIntent = new Intent(HomeActivity.this, CercaAsteActivity.class);
        myIntent.putExtra("TIPO",userType);
        HomeActivity.this.startActivity(myIntent);
    }

    private void gotoCreaAstaActivity() {
        Intent myIntent = new Intent(HomeActivity.this, CreaAstaActivity.class);
        myIntent.putExtra("TIPO",userType);
        HomeActivity.this.startActivity(myIntent);
    }

    private void gotoChageAccountType() {
        Intent myIntent = new Intent(HomeActivity.this, HomeActivity.class);
        userType = userType.equals(TipoAccount.VENDITORE) ? TipoAccount.COMPRATORE : TipoAccount.VENDITORE;
        myIntent.putExtra("TIPO",userType);
        HomeActivity.this.startActivity(myIntent);
        finish();
    }

    public void onClickPrimoRecycler(int pos){
        showAstaDetails(adapterPrimo.getData().get(pos));
    }

    public void onClickSecondoRecycler(int pos){
        showAstaDetails(adapterSecondo.getData().get(pos));
    }

    private void showAstaDetails(Asta asta) {
        Intent myIntent = new Intent(HomeActivity.this, AstaDetailsActivity.class);
        myIntent.putExtra("ASTA", asta);
        myIntent.putExtra("PRICE", asta.getBestOffer().getValore());
        //TODO getYourOffer
        myIntent.putExtra("OFFER", 0);
        HomeActivity.this.startActivity(myIntent);
    }
}
