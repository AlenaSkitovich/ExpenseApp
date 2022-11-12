package com.example.expenseapp;

import static com.example.expenseapp.helpers.Constants.LOGINEXCEPT;
import static com.example.expenseapp.helpers.Constants.LOGINNOTFOUND;
import static com.example.expenseapp.helpers.Constants.LOGINSUCCESS;
import static com.example.expenseapp.helpers.Constants.REGISTEREXCEPT;
import static com.example.expenseapp.helpers.Constants.REGISTERNOTFOUND;
import static com.example.expenseapp.helpers.Constants.REGISTERSUCCESS;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.expenseapp.helpers.RegisterThread;

import javax.net.ssl.HttpsURLConnection;

public class RegistrationActivity extends AppCompatActivity {

    private EditText login;
    private EditText password;
    private EditText name;
    private EditText lastName;

    private String loginStr;
    private String passwordStr;
    private String nameStr;
    private String lastNameStr;

    private Button register;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REGISTERSUCCESS:
                    Toast.makeText(getApplicationContext(), (String) msg.obj, Toast.LENGTH_LONG).show();
                    break;
                case REGISTERNOTFOUND:
                    Toast.makeText(getApplicationContext(), (String) msg.obj, Toast.LENGTH_LONG).show();
                    break;
                case REGISTEREXCEPT:
                    Toast.makeText(getApplicationContext(), (String) msg.obj, Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        login = (EditText) findViewById(R.id.et_login);
        password = (EditText) findViewById(R.id.et_password);
        name = (EditText) findViewById(R.id.et_name);
        lastName = (EditText) findViewById(R.id.et_lastname);
        register = findViewById(R.id.bt_register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginStr = login.getText().toString().trim();
                passwordStr = password.getText().toString().trim();
                nameStr = name.getText().toString().trim();
                lastNameStr = lastName.getText().toString().trim();
                if (loginStr.equals("") || passwordStr.equals("") || nameStr.equals("") || lastNameStr.equals("")) {
                    Toast.makeText(RegistrationActivity.this, "Поля не могут быть пустыми", Toast.LENGTH_SHORT).show();
                } else {
                    HttpsURLConnection connection = null;
                    RegisterThread thread = new RegisterThread(loginStr, passwordStr, nameStr, lastNameStr, connection, handler);
                    thread.start();
                }
            }
        });
    }
}