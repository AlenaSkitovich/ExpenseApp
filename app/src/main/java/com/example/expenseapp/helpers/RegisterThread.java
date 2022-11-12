package com.example.expenseapp.helpers;

import static com.example.expenseapp.helpers.Constants.REGISTEREXCEPT;
import static com.example.expenseapp.helpers.Constants.REGISTERNOTFOUND;
import static com.example.expenseapp.helpers.Constants.REGISTERSUCCESS;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class RegisterThread extends Thread{

    private String login;
    private String password;
    private String name;
    private String lastName;
    private HttpURLConnection connection;
    private Handler handler;




    public RegisterThread(String login,
                          String password,
                          String name,
                          String lastName,
                          HttpURLConnection connection,
                          Handler handler) {

        this.login = login;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.connection = connection;
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            URL url= new URL(Constants.urlReg);
            String data="login="+ URLEncoder.encode(login,"utf-8")
                    +"&password="+ URLEncoder.encode(password,"utf-8")+"name="+ URLEncoder.encode(name,"utf-8")
                    +"&lastName="+ URLEncoder.encode(lastName,"utf-8");
            connection=HttpConnectionUtils.getConnection(data,url);
            int code = connection.getResponseCode();
            if(code==200){
                InputStream inputStream = connection.getInputStream();
                String str = StreamChangeStrUtils.toChange(inputStream);
                Message message = Message.obtain();
                message.obj=str;
                message.what=REGISTERSUCCESS;
                handler.sendMessage(message);
            }
            else {
                Message message = Message.obtain();
                message.what=REGISTERNOTFOUND;
                message.obj = "Исключение регистрации ... повторите попытку позже";
                handler.sendMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Message message = Message.obtain();
            message.what=REGISTEREXCEPT;
            message.obj = "Неисправность сервера ... повторите попытку позже";
            handler.sendMessage(message);
        }
    }
}

