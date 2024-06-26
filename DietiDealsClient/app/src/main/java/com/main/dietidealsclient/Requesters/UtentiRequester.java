package com.main.dietidealsclient.Requesters;

import android.os.Debug;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.main.dietidealsclient.Model.Asta;
import com.main.dietidealsclient.Model.Notifica;
import com.main.dietidealsclient.Model.Offerta;
import com.main.dietidealsclient.Model.Utente;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import javax.security.auth.login.LoginException;

import okhttp3.Response;

public class UtentiRequester {

    public void signup(String email,
                       String nome,
                       String cognome,
                       String password,
                       String shortBio,
                       String city,
                       File profilePic,
                       ArrayList<Notifica> notifiche,
                       ArrayList<Asta> aste,
                       ArrayList<Offerta> offerte
                       ) throws InterruptedException {
        Thread t = new Thread(() -> {
            try {
                var mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(new Utente(email, nome, cognome, password, shortBio, city, profilePic, notifiche, aste, offerte));
                Log.d("myDebug", "JSON utente encoding sent: "+json);
                Response response = RequestUtility.sendPostRequest("auth/register", false, json);
                Log.d("myDebug", "Body received: "+response.body().string());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
        t.join();
    }

    public void jwtLogin(String email, String password) throws InterruptedException, LoginException {
        AtomicReference<String> newToken = new AtomicReference<>("error");
        Thread t = new Thread(() -> {
            try {
                Response response = RequestUtility.sendPostRequest("auth/authenticate", false, "{\"email\":\""+email+"\",\"password\":\""+password+"\"}");
                String jsBody = response.body().string();
                JSONObject jsonObject = new JSONObject(jsBody);
                newToken.set(jsonObject.getString("access_token"));
            } catch (IOException | JSONException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
        t.join();
        if(newToken.get().equals("error")){
            throw new LoginException("no token");
        }
        RequestUtility.setToken(newToken.get());
    }

    //TODO unire con jwtLogin? //Si pensa di far fare la registrazione e mandare alla pagina MODIFICA PROFILO
    public void jwtRegister(String email, String password) throws InterruptedException, LoginException {
        AtomicReference<String> newToken = new AtomicReference<>("error");
        Thread t = new Thread(() -> {
            try {
                Response response = RequestUtility.sendPostRequest("auth/register", false, "{\"email\":\""+email+"\",\"password\":\""+password+"\"}");
                String jsBody = response.body().string();
                JSONObject jsonObject = new JSONObject(jsBody);
                newToken.set(jsonObject.getString("access_token"));
            } catch (IOException | JSONException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
        t.join();
        if(newToken.get().equals("error")){
            throw new LoginException("no token");
        }
        RequestUtility.setToken(newToken.get());
    }

    //TODO Funziona (bisogna aggiungere foto e testare)
    public void updateUtente(Utente user) throws JsonProcessingException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        AtomicReference<String> json = new AtomicReference<>(objectMapper.writeValueAsString(user));
        Thread t = new Thread(() -> {
            try {
                Response response = RequestUtility.sendPostRequest("utente/update", true, json.get());
                json.set(response.body().string());
                Log.v("jsonR", json.get());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
        t.join();
    }

    public Utente getUtenteByEmail(String email) throws InterruptedException {
        AtomicReference<Utente> user = new AtomicReference<>();
        user.set(null);
        Thread t = new Thread(() -> {
            try {
                Response response = RequestUtility.sendGetRequest("utente/get/"+email, true);
                String jsBody = response.body().string();
                Log.d("myDebug", "Body received: "+jsBody);
                user.set(new ObjectMapper().readValue(jsBody, Utente.class));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
        t.join();
        return user.get();
    }

    public Utente getAstaOwner(Long astaID) throws InterruptedException {
        AtomicReference<Utente> user = new AtomicReference<>();
        user.set(null);
        Thread t = new Thread(() -> {
            try {
                Response response = RequestUtility.sendGetRequest("utente/owner/asta/"+astaID, true);
                String jsBody = response.body().string();
                Log.d("myDebug", "Body received [AstaOwner]:"+jsBody);
                user.set(new ObjectMapper().readValue(jsBody, Utente.class));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
        t.join();
        return user.get();
    }

    public Utente getOfferOwner(Long offerID) throws InterruptedException {
        AtomicReference<Utente> user = new AtomicReference<>();
        user.set(null);
        Thread t = new Thread(() -> {
            try {
                Response response = RequestUtility.sendGetRequest("utente/owner/offerta/"+offerID, true);
                String jsBody = response.body().string();
                Log.d("myDebug", "Body received [OffertaOwner]:"+jsBody);
                user.set(new ObjectMapper().readValue(jsBody, Utente.class));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
        t.join();
        return user.get();
    }

    public void setUserImg(String email, File file) throws InterruptedException {
        Thread t = new Thread(() -> {
            try {
                Response response = RequestUtility.sendPostFileRequest("utente/setImg/"+email, true, file);
                String jsBody = response.body().string();
                Log.d("myDebug", "Body received [ImgSet]: "+jsBody);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
        t.join();
    }
}
