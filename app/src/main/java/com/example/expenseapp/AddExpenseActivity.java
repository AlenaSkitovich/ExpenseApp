package com.example.expenseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.expenseapp.helpers.AddExpenseThread;
import com.example.expenseapp.helpers.ExpenseBody;

import java.util.Calendar;
import java.util.Date;

public class AddExpenseActivity extends AppCompatActivity {

    private EditText sum, name;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        sum = findViewById(R.id.sum);
        name = findViewById(R.id.ex_name);
        add = findViewById(R.id.add_sum);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date = new Date(System.currentTimeMillis());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                SharedPreferences sharedPreferences = getSharedPreferences("app", Context.MODE_PRIVATE);
                ExpenseBody expenseBody = new ExpenseBody(sum.getText().toString(),
                        name.getText().toString(),
                        String.valueOf(date.getTime()),
                        String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)),
                        String.valueOf(calendar.get(Calendar.MONTH)),
                        String.valueOf(calendar.get(Calendar.YEAR)),
                        sharedPreferences.getAll().get("login").toString());
                Log.e("expense",expenseBody.toString());
                AddExpenseThread addThread = new AddExpenseThread(expenseBody);
                addThread.start();
                while (addThread.isAlive());
                finish();
            }
        });
    }
}