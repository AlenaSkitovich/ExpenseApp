package com.example.expenseapp.helpers;

import static com.example.expenseapp.helpers.Constants.LOGINEXCEPT;
import static com.example.expenseapp.helpers.Constants.LOGINNOTFOUND;
import static com.example.expenseapp.helpers.Constants.LOGINSUCCESS;
import static com.example.expenseapp.helpers.Constants.urlLog;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import com.example.expenseapp.LoginActivity;

import java.io.Closeable;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class LoginThread extends Thread {
    private String login;
    private String password;
    /*private HttpURLConnection connection;
    private Handler handler;*/

    public LoginThread(String login, String password,HttpURLConnection connection,Handler handler) {
        this.login = login;
        this.password = password;
        /*this.connection = connection;
        this.handler = handler;*/
    }

    @Override
    public void run() {


    }
        /*
        try {
            URL url= new URL(Constants.urlLog);
            String logData = "login=" + URLEncoder.encode(login, "utf-8")
                    + "&password=" + URLEncoder.encode(password, "utf-8");
            connection = HttpConnectionUtils.getConnection(logData,url);
            int code = connection.getResponseCode();
            if (code == 200) {
                InputStream inputStream = connection.getInputStream();
                String str = StreamChangeStrUtils.toChange(inputStream);
                Message message = Message.obtain();
                message.what = LOGINSUCCESS;
                message.obj = str;
                handler.sendMessage(message);
            } else {
                Message message = Message.obtain();
                message.what = LOGINNOTFOUND;
                message.obj = "Исключение регистрации ... Повторите попытку позже";
                handler.sendMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Message message = Message.obtain();
            message.what = LOGINEXCEPT;
            message.obj = "Неисправность сервера ... повторите попытку позже";
            handler.sendMessage(message);
        }
    }*/
}

