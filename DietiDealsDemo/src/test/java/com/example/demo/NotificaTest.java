package com.example.demo;

import com.example.demo.model.Notifica;
import com.example.demo.model.Utente;

public class NotificaTest extends Notifica {
    public NotificaTest(String cod, String cod1, boolean b, Utente user) {
        super(cod,cod1,b,user);
    }

    @Override
    public boolean equals(Object obj){
        Notifica not = (Notifica) obj;
        System.out.println("not = " + not.getOggetto() + " ogetto = " + oggetto);
        return not.getOggetto().equals(oggetto);
    }
}
