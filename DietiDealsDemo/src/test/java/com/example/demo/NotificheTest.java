package com.example.demo;

import com.example.demo.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class NotificheTest {

    Utente userA , userB , userVend;
    AstaTest astaA;

    Notifica creaNot(String cod,Utente user){
        return new NotificaTest(cod,cod,false,user);
    }

    AstaTest creaAsta(Utente creatore){
        AstaTest asta = new AstaTest(0L, null, "nome", "desc", "Boh", null,false, creatore, new ArrayList<Offerta>(), 0F);
        return asta;
    }

    void creaOfferta(float val, Utente user, Asta asta){
        asta.getOfferte().add(new Offerta(0L, val, null,true, user,asta));
    }

    void offerteBase(){
        creaOfferta(2F, userA, astaA);
        creaOfferta(5F, userB, astaA);
        creaOfferta(10F, userA, astaA);
    }

    @BeforeEach
    public void setup(){
        userA = new Utente(
                "provaA",
                "Mario",
                "Rossi",
                "pas",
                "...",
                "Roma",
                null,
                new ArrayList<Notifica>(),
                new ArrayList<Asta>(),
                new ArrayList<Offerta>()
        );
        userB = new Utente(
                "provaB",
                "Franco",
                "Cerrotta",
                "pas",
                "...",
                "Napoli",
                null,
                new ArrayList<Notifica>(),
                new ArrayList<Asta>(),
                new ArrayList<Offerta>()
        );
        userVend = new Utente(
                "vendi",
                "enzo",
                "fr",
                "pas",
                "...",
                "Napoli",
                null,
                new ArrayList<Notifica>(),
                new ArrayList<Asta>(),
                new ArrayList<Offerta>()
        );
        astaA = creaAsta(userVend);
    }

    @Test
    void utenteVincitore() {
        offerteBase();
//        astaA = creaAsta(userVend);
        Notifica not = creaNot("a1",userA);
//        creaOfferta(10F, userA, astaA);
//        assertEquals(astaA.inviaNotifiche(userA) , not);
        assertTrue(astaA.inviaNotifiche(userA).equals(not));
    }

    @Test
    void ctentePerdente() {
        offerteBase();
        Notifica not = creaNot("a3",userB);
        assertTrue(astaA.inviaNotifiche(userB).equals(not));
    }

    @Test
    void creatoreVenditaOk() {
        offerteBase();
        Notifica not = creaNot("a2",userVend);
        assertTrue(astaA.inviaNotifiche(userVend).equals(not));
    }

    @Test
    void creatoreVenditaNo() {
        Notifica not = creaNot("a4",userVend);
        assertTrue(astaA.inviaNotifiche(userVend).equals(not));
    }

    @Test
    void utenteNonOfferente() {
//        Notifica not = creaNot("a4",userVend);
        assertThrows(IllegalArgumentException.class , () -> astaA.inviaNotifiche(userA));
    }

    @Test
    void creatoreHaOfferta() {
        assertThrows(IllegalArgumentException.class , () -> astaA.inviaNotifiche(userVend));
    }

    @Test
    void astaNull() {
        astaA = null;
        assertThrows(NullPointerException.class , () -> astaA.inviaNotifiche(userVend));
    }

    @Test
    void utenteNull() {
        offerteBase();
        userB = null;
        assertThrows(IllegalArgumentException.class , () -> astaA.inviaNotifiche(userB));
    }
}
