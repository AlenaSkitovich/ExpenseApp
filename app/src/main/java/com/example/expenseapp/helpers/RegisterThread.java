package com.example.expenseapp.helpers;


import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.expenseapp.MainActivity;

import org.htmlunit.org.apache.http.HttpResponse;
import org.htmlunit.org.apache.http.client.HttpClient;
import org.htmlunit.org.apache.http.client.methods.HttpUriRequest;
import org.htmlunit.org.apache.http.client.methods.RequestBuilder;
import org.htmlunit.org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


public class RegisterThread extends Thread {

    public int getStatus() {
        return status;
    }

    public String getAuth() {
        return auth;
    }

    private RegistrationBody body;
    private String auth;
    private int status = 0;

    public RegisterThread(RegistrationBody body) {
        this.body = body;
    }

    @Override
    public void run() {
        super.run();
        try {
            Log.e("link", Constants.urlReg);
            HttpUriRequest httpUriRequest1 =
                    RequestBuilder.post(Constants.urlReg)
                            .addParameter("login", body.getLogin())
                            .addParameter("password", body.getPassword())
                            .addParameter("name", body.getName())
                            .addParameter("lastName", body.getLastName())
                            .addParameter("url", body.getUrl())
                            /*.setCharset(StandardCharsets.UTF_8)*/
                            .build();
            HttpClient httpClient = HttpClients.createDefault();
            HttpResponse httpResponse = httpClient.execute(httpUriRequest1);
            status = httpResponse.getStatusLine().getStatusCode();
            auth = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent())).readLine();
            System.out.println(status);
        } catch (IOException e) {
            System.out.println("Error!");
            e.printStackTrace();
        }
    }
}


