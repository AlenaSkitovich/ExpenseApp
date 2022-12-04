package com.example.expenseapp.helpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetDailyExpThread extends Thread {

    private String id;
    private String year;
    private String month;
    private String day;

    private List<ExpenseBody> list = new ArrayList<>();

    public List<ExpenseBody> getList() {
        return list;
    }

    public GetDailyExpThread(String id, String year, String month, String day) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(Constants.urlMain + "expense/getDailyExp/" + id +"/"+ year+"/" + month+"/" + day);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(url.openStream()));
            JSONArray jsonArray = new JSONArray(bufferedReader.readLine());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ExpenseBody expenseBody = new ExpenseBody(jsonObject.get("sum").toString(),
                        jsonObject.get("name").toString(),
                        jsonObject.get("time").toString());
                list.add(expenseBody);

            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }
}
