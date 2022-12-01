package com.example.expenseapp.helpers;

import android.util.Log;

import org.htmlunit.org.apache.http.HttpResponse;
import org.htmlunit.org.apache.http.client.HttpClient;
import org.htmlunit.org.apache.http.client.methods.HttpUriRequest;
import org.htmlunit.org.apache.http.client.methods.RequestBuilder;
import org.htmlunit.org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class AddExpenseThread extends Thread {

    private ExpenseBody expenseBody;

    private String auth;
    private int status = 0;


    public int getStatus() {
        return status;
    }

    public String getAuth() {
        return auth;
    }

    public AddExpenseThread(ExpenseBody expenseBody) {
        this.expenseBody = expenseBody;
    }

    @Override
    public void run() {
        super.run();
        try {
            Log.e("link", Constants.urlMain + "expense/add");
            HttpUriRequest httpUriRequest2 =
                    RequestBuilder.post(Constants.urlMain + "expense/add")
                            .addParameter("sum", expenseBody.getSum())
                            .addParameter("name", expenseBody.getName())
                            .addParameter("time", expenseBody.getTime())
                            .addParameter("day", expenseBody.getDay())
                            .addParameter("month", expenseBody.getMonth())
                            .addParameter("year", expenseBody.getYear())
                            .addParameter("login", expenseBody.getLogin())
                            .setCharset(StandardCharsets.UTF_8)
                            .build();
            HttpClient httpClient = HttpClients.createDefault();
            HttpResponse httpResponse = httpClient.execute(httpUriRequest2);

            status = httpResponse.getStatusLine().getStatusCode();
            auth = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent())).readLine();
            System.out.println(status);
        } catch (Exception e) {
            System.out.println("Error!");
            e.printStackTrace();
        }
    }
}
