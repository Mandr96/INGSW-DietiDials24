package com.main.dietidealsclient.Requesters;


import android.util.Log;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.channels.Channels;
import java.nio.channels.Pipe;
import java.nio.charset.StandardCharsets;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.internal.http.HttpHeaders;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

public class RequestUtility {

    private static String baseUrl = "http://34.105.133.232:8080/";
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

    public static Response sendPostFileRequest(String path, Boolean authRequired, File file) throws IOException {
        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart(
                        "userfile",
                        "asta.png",
                        RequestBody.create(file, MEDIA_TYPE_PNG))
                .build();

        Request.Builder requestBuilder = new Request.Builder();
        if(authRequired)
            requestBuilder.addHeader("Authorization","Bearer "+token);

        Request request = requestBuilder.url("")
                .post(body)
                .build();

        OkHttpClient client = new OkHttpClient();
        return client.newCall(request).execute();
    }

    private static RequestBody createRequestBodyForStream(final MediaType mediaType, final InputStream inputStream) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return mediaType;
            }

            @Override
            public long contentLength() {
                try {
                    return inputStream.available();
                } catch (IOException e) {
                    return 0;
                }
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source = null;
                try {
                    source = Okio.source(inputStream);
                    sink.writeAll(source);
                } finally {
                    Util.closeQuietly(source);
                }
            }
        };
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String newToken) {
        token = newToken;
    }
}
