package com.example.expenseapp.helpers;


import static com.example.expenseapp.helpers.Constants.urlLog;

import android.util.Log;


import org.htmlunit.org.apache.http.HttpEntity;
import org.htmlunit.org.apache.http.HttpResponse;
import org.htmlunit.org.apache.http.client.HttpClient;
import org.htmlunit.org.apache.http.client.methods.HttpUriRequest;
import org.htmlunit.org.apache.http.client.methods.RequestBuilder;
import org.htmlunit.org.apache.http.impl.client.HttpClients;
import org.htmlunit.org.apache.http.util.EntityUtils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class LoginThread extends Thread {

    public int getStatus() {
        return status;
    }
    public String getAuth(){return auth;}

    private RegistrationBody body;
    private String auth;
    private int status = 0;

    public LoginThread(RegistrationBody body) {
        this.body = body;

    }

    @Override
    public void run() {
        super.run();
        try {
            Log.e("link", urlLog);
            HttpUriRequest httpUriRequest1 =
                    RequestBuilder.post(Constants.urlLog)
                            .addParameter("login", body.getLogin())
                            .addParameter("password", body.getPassword())
                            /*.setCharset(StandardCharsets.UTF_8)*/
                            .build();
            HttpClient httpClient = HttpClients.createDefault();
            HttpResponse httpResponse = httpClient.execute(httpUriRequest1);

            /*HttpEntity entity = (HttpEntity) httpResponse.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");
            Log.e("str", responseString);*/

            status = httpResponse.getStatusLine().getStatusCode();
            auth = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent())).readLine();
            System.out.println(status);
        } catch (IOException e) {
            System.out.println("Error!");
            e.printStackTrace();
        }
    }
}










