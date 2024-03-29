package com.main.dietidealsclient.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.ComponentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.main.dietidealsclient.Adapters.AsteAdapterList;
import com.main.dietidealsclient.Controller.AsteController;
import com.main.dietidealsclient.Controller.TipoAccount;
import com.main.dietidealsclient.Controller.UserProfileController;
import com.main.dietidealsclient.Model.Asta;
import com.main.dietidealsclient.Model.Offerta;
import com.main.dietidealsclient.Model.Utente;
import com.main.dietidealsclient.R;
import com.main.dietidealsclient.RecyclerAsteInterface;
import com.main.dietidealsclient.Utility.LoggedUser;
import com.main.dietidealsclient.Utility.Logger;
import com.main.dietidealsclient.Utility.MyException;

import java.util.List;

public class HomeActivity extends ComponentActivity {

    private RecyclerView recyclerViewPrimo, recyclerViewSecondo;
    private AsteAdapterList adapterPrimo, adapterSecondo;
    private AsteController asteController;
    private TipoAccount userType;
    public HomeActivity(){
        asteController = new AsteController();
    }

    //Todo Esplode se accedo a un account che a fatto offerte ad altre aste

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
            Logger.log("HomePage","Cambio Activity -> CercaAstePage");
            gotoCercaAsteActivity();
        });
        findViewById(R.id.home_compratore_crea_asta_inversa).setOnClickListener(view -> {
            Logger.log("HomePage","Cambio Activity -> CreaAstaPage");
            gotoCreaAstaActivity();
        });
        findViewById(R.id.home_compratore_cambia_tipo).setOnClickListener(view -> {
            gotoChageAccountType();
        });
        findViewById(R.id.home_compratore_edit_profile).setOnClickListener(view -> {
            Logger.log("HomePage","Cambio Activity -> ProfilePage");
            goToEditProfileActivity();
        });
        findViewById(R.id.home_compratore_notifiche_btn).setOnClickListener(view -> {
            Logger.log("HomePage","Cambio Activity -> NotifichePage");
            gotoNotificheActivity();
        });

        adapterPrimo.setClickListener(new RecyclerAsteInterface() {
            @Override
            public void onItemClick(int position) throws MyException {
                Log.d("MyDebug" , "CLICCATO" + adapterPrimo.getData().get(position));
                showAstaDetails(adapterPrimo.getData().get(position));
            }
        });


        adapterSecondo.setClickListener(new RecyclerAsteInterface() {
            @Override
            public void onItemClick(int position) throws MyException {
                showAstaDetails(adapterSecondo.getData().get(position));
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        setUserType();
        updateAsteLists();
        updateTesti();
    }

    private void updateTesti() {
        if (userType.equals(TipoAccount.VENDITORE)) {
            ((TextView)findViewById(R.id.aste_title_text1)).setText(R.string.le_tue_aste);
            ((TextView)findViewById(R.id.aste_title_text2)).setText(R.string.le_aste_inverse_a_cui_hai_partecipato);
            ((Button)findViewById(R.id.home_compratore_cambia_tipo)).setText(R.string.compra);
            ((Button)findViewById(R.id.home_compratore_crea_asta_inversa)).setText(R.string.crea_asta);
        } else {
            ((TextView)findViewById(R.id.aste_title_text1)).setText(R.string.le_aste_a_cui_hai_partecipato);
            ((TextView)findViewById(R.id.aste_title_text2)).setText(R.string.le_tue_aste_inverse);
            ((Button)findViewById(R.id.home_compratore_cambia_tipo)).setText(R.string.vendi);
            ((Button)findViewById(R.id.home_compratore_crea_asta_inversa)).setText(R.string.crea_asta_inversa);
        }

    }


    private void setUserType() {

        userType = (TipoAccount)getIntent().getSerializableExtra("TIPO");

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
        LoggedUser.update();
        if(userType.equals(TipoAccount.COMPRATORE)) {
            adapterPrimo = updateAsteListpartecipate(recyclerViewPrimo, this::onClickPrimoRecycler);
            adapterSecondo = updateAsteListCreate(recyclerViewSecondo, this::onClickSecondoRecycler);
        } else if (userType.equals(TipoAccount.VENDITORE)) {
            adapterPrimo = updateAsteListCreate(recyclerViewPrimo, this::onClickPrimoRecycler);
            adapterSecondo = updateAsteListpartecipate(recyclerViewSecondo, this::onClickSecondoRecycler);
        }
        Log.d("myDebug", "Primo: " + adapterPrimo.toString());
        Log.d("myDebug", "Secondo: " + adapterSecondo.toString());

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
        Logger.log("HomePage","Primo accesso utente. Cambio activity -> ProfilePage");
        Utente loggedUser = LoggedUser.getInstance().getLoggedUser();
        if(loggedUser.getNome() == null){
            goToEditProfileActivity();
        }
    }

    private void goToEditProfileActivity() {
        Intent myIntent = new Intent(HomeActivity.this, EditProfileActivity.class);
        myIntent.putExtra("TIPO",userType);
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
        finish();
    }

    private void gotoChageAccountType() {
        Intent myIntent = new Intent(HomeActivity.this, HomeActivity.class);
        if(userType.equals(TipoAccount.VENDITORE)) {
            Logger.log("HomePage","Passaggio a modalità Compratore");
            userType = TipoAccount.COMPRATORE;
        }
        else {
            Logger.log("HomePage","Passaggio a modalità Venditore");
            userType = TipoAccount.VENDITORE;
        }
        myIntent.putExtra("TIPO",userType);
        HomeActivity.this.startActivity(myIntent);
        finish();
    }

    public void onClickPrimoRecycler(int pos) throws MyException {
        showAstaDetails(adapterPrimo.getData().get(pos));
    }

    public void onClickSecondoRecycler(int pos) throws MyException {
        showAstaDetails(adapterSecondo.getData().get(pos));
    }

    private void showAstaDetails(Asta asta) throws MyException {
        Logger.log("HomePage","Mostra dettagli asta -> " + asta.getNomeProdotto());
        asta.setCreatore(new UserProfileController().getAstaOwner(asta.getId()));
        Intent myIntent = new Intent(HomeActivity.this, AstaDetailsActivity.class);
        myIntent.putExtra("ASTA", asta);
        Offerta bestOff = asta.getBestOffer();
        if (bestOff != null){
            myIntent.putExtra("PRICE", asta.getBestOffer().getValore());
        } else {
            myIntent.putExtra("PRICE", 0);
        }
        //TODO getYourOffer
        myIntent.putExtra("OFFER", 0);
        HomeActivity.this.startActivity(myIntent);
    }

    private void gotoNotificheActivity() {
        Intent myIntent = new Intent(HomeActivity.this, NotificheActivity.class);
        myIntent.putExtra("TIPO",userType);
        HomeActivity.this.startActivity(myIntent);
    }
}
