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
import java.util.ArrayList;

public class DemoClientTests {
    private static String baseUrl = "http://localhost:8080/";
    static String token = "";

    public static void main(String[] args) throws IOException, InterruptedException {

        HttpRequest request;
        HttpResponse<String> response;
        HttpClient http = HttpClient.newHttpClient();

        Utente user = new Utente(
                "gianm","gianmarco", "lembo", "password", "...", "Capri",
                new ArrayList<Notifica>(), new ArrayList<Asta>(), new ArrayList<Offerta>()
        );
        RegistraUtente(user);
        LoginUtente("gianm" , "password");

        /*

        request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "utente/prova"))
                .headers("Authorization","Bearer "+token)
                .headers("Content-Type","application/json")
                .GET().build();
        response = http.send(request, HttpResponse.BodyHandlers.ofString());
        Utente user = new ObjectMapper().readValue(response.body(), Utente.class);

        System.out.println("Response status code: "+response.statusCode());
        System.out.println("Response body       : "+response.body());

        Offerta offer = new Offerta(
                0L,
                10f,
                Timestamp.from(Instant.now()),
                false,
                user,
                null
        );
        var mapper = new ObjectMapper();
        var json = mapper.writeValueAsString(offer);

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

        request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "asta/cerca/AstaClassica/any/cosa/0"))
                .headers("Authorization","Bearer "+token)
                .headers("Content-Type","application/json")
                .GET().build();
        response = http.send(request, HttpResponse.BodyHandlers.ofString());

        DebugResponse(response);
    }

    private static void LoginUtente(String email, String password) throws IOException, InterruptedException {
        HttpRequest request;
        HttpResponse<String> response;
        HttpClient http = HttpClient.newHttpClient();

        request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "auth/authenticate"))
                .headers("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}")).build();
        response = http.send(request, HttpResponse.BodyHandlers.ofString());

        SetToken(response);

        DebugResponse(response);
    }

    private static void RegistraUtente(Utente user) throws IOException, InterruptedException {
        var mapper = new ObjectMapper();
        var json = mapper.writeValueAsString(user);

        HttpRequest request;
        HttpResponse<String> response;
        HttpClient http = HttpClient.newHttpClient();

        request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "auth/register"))
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json)).build();
        response = http.send(request, HttpResponse.BodyHandlers.ofString());

        SetToken(response);

        DebugResponse(response);

    }

    public static void DebugResponse(HttpResponse<String> response){
        System.out.println("Response status code: "+response.statusCode());
        if(response.statusCode() != 403){
            System.out.println("Response body       : "+response.body());
        }
    }

    private static void UpdateUtente(Utente user) throws IOException, InterruptedException {
        var mapper = new ObjectMapper();
        var json = mapper.writeValueAsString(user);

        HttpRequest request;
        HttpResponse<String> response;
        HttpClient http = HttpClient.newHttpClient();

        request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "utente/update"))
                .headers("Authorization","Bearer "+token)
                .headers("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json)).build();
        response = http.send(request, HttpResponse.BodyHandlers.ofString());

        DebugResponse(response);

    }

    private static void SetToken(HttpResponse<String> response) {
        String js = response.body();
        try {
            JSONObject jsonObject = new JSONObject(js);
            token = jsonObject.getString("access_token");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        System.out.println("THIS IS THE TOKEN : " + token);
    }
}
