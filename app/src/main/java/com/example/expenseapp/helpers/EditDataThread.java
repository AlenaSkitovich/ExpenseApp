package com.example.expenseapp.helpers;

import org.htmlunit.org.apache.http.HttpResponse;
import org.htmlunit.org.apache.http.client.HttpClient;
import org.htmlunit.org.apache.http.client.methods.HttpUriRequest;
import org.htmlunit.org.apache.http.client.methods.RequestBuilder;
import org.htmlunit.org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class EditDataThread extends Thread {

    private RegistrationBody body;

    private String status;
    private String answer;

    public String getAnswer() {
        return answer;
    }

    public EditDataThread(RegistrationBody body) {
        this.body = body;
    }

    @Override
    public void run() {
        try {
            HttpUriRequest httpUriRequest4 =
                    RequestBuilder.post(Constants.urlMain + "user/editName")
                            .addParameter("name", body.getName())
                            .addParameter("lastName", body.getLastName())
                            .addParameter("login", body.getLogin())
                            .setCharset(StandardCharsets.UTF_8)
                            .build();
            HttpClient httpClient = HttpClients.createDefault();
            HttpResponse httpResponse = httpClient.execute(httpUriRequest4);
            status = String.valueOf(httpResponse.getStatusLine().getStatusCode());
            answer = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent())).readLine();
            System.out.println(answer);
        } catch (IOException e) {
            System.out.println("Error!");
            e.printStackTrace();
        }
    }
}


