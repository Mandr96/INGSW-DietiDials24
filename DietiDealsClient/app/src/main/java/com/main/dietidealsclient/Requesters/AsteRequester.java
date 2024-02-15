package com.main.dietidealsclient.Requesters;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.main.dietidealsclient.Model.Asta;
import com.main.dietidealsclient.Model.Offerta;
import com.main.dietidealsclient.Model.Utente;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import okhttp3.Response;

public class AsteRequester {

    //TODO da testare
    public Asta getAstaById(Long id) throws InterruptedException {
        AtomicReference<Asta> asta = new AtomicReference<>();
        asta.set(null);
        Thread t = new Thread(() -> {
            try {
                Response response = RequestUtility.sendGetRequest("asta/get/"+id, true);
                String jsBody = response.body().string();
                Log.d("myDebug", "Body received: "+jsBody);
                asta.set(new ObjectMapper().readValue(jsBody, Asta.class));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
        t.join();
        return asta.get();
    }

    //TODO da testare
    public List<Offerta> getOfferteByUser(String email) throws InterruptedException{
        AtomicReference<ArrayList<Offerta>> offerte = new AtomicReference<>();
        offerte.set(new ArrayList<>());
        Thread t = new Thread(() -> {
            try {
                Response response = RequestUtility.sendGetRequest("offerta/getByUser/"+email, true);
                String jsBody = response.body().string();
                Log.d("myDebug", "Body received: "+jsBody);
                offerte.set(new ObjectMapper().readValue(jsBody, ArrayList.class));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
        t.join();
        return offerte.get();
    }

    //TODO da testare
    public List<Offerta> getOfferteByAsta(Long astaID) throws InterruptedException {
        AtomicReference<ArrayList<Offerta>> offerte = new AtomicReference<>();
        offerte.set(new ArrayList<>());
        Thread t = new Thread(() -> {
            try {
                Response response = RequestUtility.sendGetRequest("offerta/getByAsta/" + astaID, true);
                String jsBody = response.body().string();
                Log.d("myDebug", "Body received: " + jsBody);
                offerte.set(new ObjectMapper().readValue(jsBody, ArrayList.class));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
        t.join();
        return offerte.get();
    }
}
