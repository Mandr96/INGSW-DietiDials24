package com.main.dietidealsclient;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.main.dietidealsclient.Controller.AsteController;
import com.main.dietidealsclient.Requesters.AsteRequester;
import com.main.dietidealsclient.Requesters.UtentiRequester;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

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