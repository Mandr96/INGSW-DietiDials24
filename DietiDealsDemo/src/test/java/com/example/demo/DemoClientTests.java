package com.example.demo;

import com.example.demo.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;

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

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "utente/create"))
                .headers("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json)).build();
        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());

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
                .headers("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json)).build();
        response = http.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Response status code: "+response.statusCode());
        System.out.println("Response body       : "+response.body());


    }
}
