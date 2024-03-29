package com.main.dietidealsclient.Requesters;

import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.main.dietidealsclient.Model.Asta;
import com.main.dietidealsclient.Model.Offerta;
import com.main.dietidealsclient.Utility.LoggedUser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import okhttp3.Response;

public class AsteRequester {

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

    public List<Offerta> getOfferteByUser(String email) throws InterruptedException{
        AtomicReference<ArrayList<Offerta>> offerte = new AtomicReference<>();
        offerte.set(new ArrayList<>());
        Thread t = new Thread(() -> {
            try {
                Response response = RequestUtility.sendGetRequest("offerta/byUser/"+email, true);
                String jsBody = response.body().string();
                Log.d("myDebug", "Body received: "+jsBody);
                offerte.set(new ObjectMapper().readValue(jsBody, new TypeReference<ArrayList<Offerta>>() {}));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
        t.join();
        return offerte.get();
    }

    public List<Offerta> getOfferteByAsta(Long astaID) throws InterruptedException {
        AtomicReference<ArrayList<Offerta>> offerte = new AtomicReference<>();
        offerte.set(new ArrayList<>());
        Thread t = new Thread(() -> {
            try {
                Response response = RequestUtility.sendGetRequest("offerta/byAsta/" + astaID, true);
                String jsBody = response.body().string();
                Log.d("myDebug", "Body received: " + jsBody);
                offerte.set(new ObjectMapper().readValue(jsBody, new TypeReference<ArrayList<Offerta>>() {}));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
        t.join();
        return offerte.get();
    }

    //TODO KEYWORD PUO essere vuota!
    public List<Asta> cercaAsta(String tipo, String categoria, String kw, Integer pag) throws InterruptedException {
        Log.d("myDebug", "Ricerca[tipo: "+tipo+", cat: "+categoria+", kw: "+kw+", pag: "+pag+"]");
        AtomicReference<ArrayList<Asta>> result = new AtomicReference<>();
        result.set(new ArrayList<>());
        Thread t = new Thread(() -> {
            try {
                Response response = RequestUtility.sendGetRequest("asta/cerca/"+tipo.toLowerCase()+"/"+categoria.toLowerCase()+"/"+kw+"/"+pag,true);
                String jsBody = response.body().string();
                Log.d("myDebug", "Body received: " + jsBody);
                result.set(new ObjectMapper().readValue(jsBody, new TypeReference<ArrayList<Asta>>() {}));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
        t.join();
        return result.get();
    }

    public List<Asta> getAstePartecipate() throws InterruptedException {
        AtomicReference<ArrayList<Asta>> result = new AtomicReference<>();
        result.set(new ArrayList<>());
        Thread t = new Thread(() -> {
            try {
                Response response = RequestUtility.sendGetRequest("asta/partecipate/"+ LoggedUser.getInstance().getLoggedUser().getEmail(),true);
                String jsBody = response.body().string();
                Log.d("myDebug", "Body received: " + jsBody);
                result.set(new ObjectMapper().readValue(jsBody, new TypeReference<ArrayList<Asta>>() {}));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
        t.join();
        return result.get();
    }

    public Long inserisciAsta(Asta asta) throws InterruptedException {
        AtomicReference<Long> astaID = new AtomicReference<>();
        astaID.set(-1L);
        Thread t = new Thread(() -> {
            try {
                var mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(asta);
                Log.d("myDebug", "JSON asta encoding sent: " + json);
                Response response = RequestUtility.sendPostRequest("asta/nuova", true, json);
                String jsBody = response.body().string();
                Log.d("myDebug", "Body received: " + jsBody);
                astaID.set(Long.valueOf(jsBody));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
        t.join();
        return astaID.get();
    }

    public Long inviaOfferta(Offerta offer) throws InterruptedException {
        AtomicReference<Long> offerID = new AtomicReference<>();
        offerID.set(-1L);
        Thread t = new Thread(() -> {
            try {
                var mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(offer);
                Log.d("myDebug", "JSON offerta encoding sent: " + json);
                Response response = RequestUtility.sendPostRequest("offerta/nuova", true, json);
                String jsBody = response.body().string();
                Log.d("myDebug", "Body received: " + jsBody);
                offerID.set(Long.valueOf(jsBody));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
        t.join();
        return offerID.get();
    }

    public void setAstaImg(Long astaID, File img) throws InterruptedException {
        Thread t = new Thread(() -> {
            try {
                Response response = RequestUtility.sendPostFileRequest("asta/setImg", true, img);
                String responseBody = response.toString();
                Log.d("myDebug", "Http response [ImgSet]: "+responseBody);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
        t.join();
    }

    public String getAstaOwnerEmail(Long id) throws InterruptedException {
        AtomicReference<String> astaOwner = new AtomicReference<>("");
        Thread t = new Thread(() -> {
            try {
                Response response = RequestUtility.sendGetRequest("asta/getcreatore/"+id, true);
                String jsBody = response.body().string();
                Log.d("myDebug", "Body received: "+jsBody);
                astaOwner.set(jsBody);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
        t.join();
        return astaOwner.get();
    }
}
