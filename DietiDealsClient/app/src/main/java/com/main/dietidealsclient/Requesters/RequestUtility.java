package com.main.dietidealsclient.Requesters;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestUtility {

    private static String baseUrl = "http://192.168.1.101:8080/";
    private static String token = "";

    public static  Response sendGetRequest(String path, Boolean authRequired) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder();
        if (authRequired)
            requestBuilder.addHeader("Authorization", "Bearer " + token);
        Request request = requestBuilder.url(baseUrl + path)
                .get()
                .build();
        return client.newCall(request).execute();
    }

    public static Response sendPostRequest(String path, Boolean authRequired, String jsonBody) throws IOException {
        MediaType JSON = MediaType.get("application/json");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(jsonBody, JSON);
        Request.Builder requestBuilder = new Request.Builder();
        if(authRequired)
            requestBuilder.addHeader("Authorization","Bearer "+token);
        Request request = requestBuilder.url(baseUrl+path)
                .post(body)
                .build();
        return client.newCall(request).execute();
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String newToken) {
        token = newToken;
    }
}
