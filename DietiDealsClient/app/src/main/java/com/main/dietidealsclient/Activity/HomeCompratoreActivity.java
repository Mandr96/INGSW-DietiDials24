package com.main.dietidealsclient.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.ComponentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.main.dietidealsclient.AsteAdapterList;
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

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_home_compratore);
        inizializeProfile();
        updateAsteList();
    }

    private void updateAsteList() {
        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Asta> data = LoggedUser.getInstance().getLoggedUser().getAste();
        //TEST  TEST  TEST  TEST  TEST  TEST
//        AsteRequester astReq = new AsteRequester();
//        Asta tmp;
//        try {
//            tmp = astReq.getAstaById(1L);
//            data.add(tmp);
//            tmp = astReq.getAstaById(2L);
//            data.add(tmp);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        } //TEST  TEST  TEST  TEST  TEST  TEST  TEST
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
}
