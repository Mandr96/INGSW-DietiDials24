package com.main.dietidealsclient.Requesters;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.main.dietidealsclient.Model.Notifica;
import com.main.dietidealsclient.Model.Offerta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import okhttp3.Response;

public class NotificheRequester {

    //TODO da testare
    public List<Notifica> getNotificheByUser(String email) throws InterruptedException{
        AtomicReference<ArrayList<Notifica>> notifiche = new AtomicReference<>();
        notifiche.set(new ArrayList<>());
        Thread t = new Thread(() -> {
            try {
                Response response = RequestUtility.sendGetRequest("all/"+email, true);
                String jsBody = response.body().string();
                Log.d("myDebug", "Body received: "+jsBody);
                notifiche.set(new ObjectMapper().readValue(jsBody, ArrayList.class));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
        t.join();
        return notifiche.get();
    }

    //TODO da testare
    public List<Notifica> getUnread(String email) throws InterruptedException{
        AtomicReference<ArrayList<Notifica>> notifiche = new AtomicReference<>();
        notifiche.set(new ArrayList<>());
        Thread t = new Thread(() -> {
            try {
                Response response = RequestUtility.sendGetRequest("unread/"+email, true);
                String jsBody = response.body().string();
                Log.d("myDebug", "Body received: "+jsBody);
                notifiche.set(new ObjectMapper().readValue(jsBody, ArrayList.class));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
        t.join();
        return notifiche.get();
    }

    //TODO da testare
    public void setRead(Long id) throws InterruptedException{
        Thread t = new Thread(() -> {
            try {
                Response response = RequestUtility.sendGetRequest("setRead/"+id, true);
                String jsBody = response.body().string();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
        t.join();
    }
}
