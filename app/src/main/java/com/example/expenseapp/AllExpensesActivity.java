package com.example.expenseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.expenseapp.helpers.ExpenseBody;
import com.example.expenseapp.helpers.GetExpenseThread;

import java.util.List;
import java.util.prefs.Preferences;

public class AllExpensesActivity extends AppCompatActivity {

    private ListView listView;
    private  int size;
    List<ExpenseBody>list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_expenses);

        listView = findViewById(R.id.listView);
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(AllExpensesActivity.this);
        GetExpenseThread getExpenseThread = new GetExpenseThread(sharedPreferences.getAll().get("id").toString());
        getExpenseThread.start();
        while(getExpenseThread.isAlive());
        List<ExpenseBody> list = getExpenseThread.getList();
        size = list.size();
        /*ArrayAdapter adapter = new ArrayAdapter(AllExpensesActivity.this, android.R.layout.simple_list_item_1,list);*/
        listView.setAdapter(new CustomAdapter());


    }
    private  class CustomAdapter extends BaseAdapter{

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
            convertView = getLayoutInflater().inflate(R.layout.custom_list,null);
            TextView textView = convertView.findViewById(R.id.name);
            TextView textView1 = convertView.findViewById(R.id.sum);

            textView.setText(list.get(position).getName());
            textView.setText(list.get(position).getSum());
            return convertView;
        }
    }
}