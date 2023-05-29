package com.deneme.restclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import javax.net.ssl.*;
import java.security.cert.X509Certificate;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    private Button getButton;
    private Button postButton;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HttpsTrustManager httpsTrustManager = new HttpsTrustManager();

        getButton = findViewById(R.id.button_send_getRequest);
        postButton = findViewById(R.id.button_send_postRequest);
        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  {
                getReq();
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getReq();
            }
        });
    }

    String url1 = "https://127.0.0.1:7059/api/Users/GetAll";
    String url2= "https://10.0.2.2:7059/api/Users/GetAll";
    String url= "https://10.0.2.2:44369/WeatherForecast";

    public void getReq(){

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }
                    Log.e("api","onResponse" + responseBody.string());
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("api","onfail" + e.getMessage());
            }

        });
    }


    public void post() throws IOException {
        OkHttpClient client = new OkHttpClient();
        String json = "dsşalkdjsaşd";
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        Headers responseHeaders = response.headers();
        for (int i = 0; i < responseHeaders.size(); i++) {
            System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
        }

        System.out.println(response.body().string());

    }

}