package com.example.expenseapp.helpers;

import android.util.Log;

import org.htmlunit.org.apache.http.HttpResponse;
import org.htmlunit.org.apache.http.client.HttpClient;
import org.htmlunit.org.apache.http.client.methods.HttpUriRequest;
import org.htmlunit.org.apache.http.client.methods.RequestBuilder;
import org.htmlunit.org.apache.http.impl.client.HttpClients;

import java.nio.charset.StandardCharsets;

public class EditPhotoThread extends Thread {

    private String url, login;
    private String status;

    public EditPhotoThread(String url, String login) {
        this.url = url;
        this.login = login;
    }

    @Override
    public void run() {
    try {
            Log.e("link", Constants.urlMain + "user/editPhoto");
            HttpUriRequest httpUriRequest3 = RequestBuilder.post(Constants.urlMain + "user/editPhoto")
                    .addParameter("url", url)
                    .addParameter("login", login)
                    .setCharset(StandardCharsets.UTF_8)
                    .build();
            HttpClient httpClient = HttpClients.createDefault();
            HttpResponse httpResponse = httpClient.execute(httpUriRequest3);
            status = String.valueOf(httpResponse.getStatusLine().getStatusCode());
            System.out.println(status);

        } catch (Exception e) {
            System.out.println("Error!");
            e.printStackTrace();
        }
    }
}
