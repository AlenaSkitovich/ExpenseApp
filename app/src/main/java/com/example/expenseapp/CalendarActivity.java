package com.example.expenseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expenseapp.helpers.ExpenseBody;
import com.example.expenseapp.helpers.GetDailyExpThread;

import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    private ListView listView;
    private int size;
    List<ExpenseBody> list;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year,
                                            int month, int dayOfMonth) {
                mYear = year;
                mMonth = month;
                mDay = dayOfMonth;
                /*String selectedDate = new StringBuilder().append(mMonth + 1)
                        .append("-").append(mDay).append("-").append(mYear)
                        .append(" ").toString();
                Toast.makeText(getApplicationContext(), selectedDate, Toast.LENGTH_LONG).show();*/
                listView = findViewById(R.id.listView1);
                SharedPreferences sharedPreferences = getSharedPreferences("app", Context.MODE_PRIVATE);
                GetDailyExpThread getDailyExpThread = new GetDailyExpThread(sharedPreferences.getAll().get("id").toString(),
                        String.valueOf(mYear),
                        String.valueOf(mMonth),
                        String.valueOf(mDay));
                getDailyExpThread.start();
                while (getDailyExpThread.isAlive()) ;
                list = getDailyExpThread.getList();
                Log.e("list",list.toString());
                size = list.size();
                listView.setAdapter(new CalendarActivity.CustomAdapter());
            }
        });
    }

    private class CustomAdapter extends BaseAdapter {

        private int position;
        private View convertView;
        private ViewGroup parent;

        @Override
        public int getCount() {
            return size;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.custom_list, null);
            TextView textView = convertView.findViewById(R.id.name);
            TextView textView1 = convertView.findViewById(R.id.sum);

            textView.setText(list.get(position).getName());
            textView1.setText(list.get(position).getSum());
            return convertView;
        }
    }
}
