package com.example.demo;

import com.example.demo.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

public class DemoClientTests {
    private static String baseUrl = "http://localhost:8080/";

    public static void main(String[] args) throws IOException, InterruptedException {

        HttpClient http = HttpClient.newHttpClient();
        /*
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "authenticate"))
                .headers("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString(
                        "{\"username\":\"test\", \"password\":\"test\"}"
                )).build();
        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Response status code: "+response.statusCode());
        System.out.println("Response body       : "+response.body());

        JSONObject json = new JSONObject(response.body());
        String token = json.getString("jwt");
        System.out.println("JWT Token: "+token);
        */

        Utente user = new Utente(
                "gianm",
                "gianmarco",
                "lembo",
                "password",
                "...",
                "Capri",
                new ArrayList<Notifica>(),
                new ArrayList<Asta>(),
                new ArrayList<Offerta>()
        );
        var mapper = new ObjectMapper();
        var json = mapper.writeValueAsString(user);
/*
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "auth/register"))
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json)).build();
        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Response status code: "+response.statusCode());
        System.out.println("Response body       : "+response.body());

        */
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "auth/authenticate"))
                .headers("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"email\":\"prova\",\"password\":\"1234\"}")).build();
        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Response status code: "+response.statusCode());
        System.out.println("Response body       : "+response.body());

        String token = "cazz";
        String js = response.body();
        try {
            JSONObject jsonObject = new JSONObject(js);
            token = jsonObject.getString("access_token");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        System.out.println("ok");
        System.out.println(token);

        Offerta offer = new Offerta(
                0L,
                10f,
                Timestamp.from(Instant.now()),
                false,
                user,
                null
        );
        mapper = new ObjectMapper();
        json = mapper.writeValueAsString(offer);

        request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "offerta/nuova"))
                .headers("Authorization","Bearer "+token)
                .headers("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json)).build();
        response = http.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Response status code: "+response.statusCode());
        System.out.println("Response body       : "+response.body());

        /*
        request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "asta/checkScadenza/2"))
                .headers("Content-Type","application/json")
                .GET().build();
        response = http.send(request, HttpResponse.BodyHandlers.ofString());
        */
    }
}
