package com.main.dietidealsclient;

import com.main.dietidealsclient.Requesters.AsteRequester;
import com.main.dietidealsclient.Requesters.UtentiRequester;

import javax.security.auth.login.LoginException;


public class MainTest {

    public static void start() {
        UtentiRequester userRequester = new UtentiRequester();
        AsteRequester asteRequester = new AsteRequester();
        try {
            userRequester.jwtLogin("prova", "1234");

        } catch (InterruptedException | LoginException e) {
            throw new RuntimeException(e);
        }

    }
}