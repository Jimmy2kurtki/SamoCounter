package com.lessons.samocounter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MoneyOnDayActivity extends AppCompatActivity {
    ListView listViewMoney, listViewNombers;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayListMoneyOnDay = new ArrayList<>();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_on_day);
        listViewMoney = findViewById(R.id.listViewMoney);
        listViewNombers = findViewById(R.id.listViewNombers);

        try {
            FileOutputStream fileOutputStream  = openFileOutput("moneyOnDay.txt", MODE_APPEND);
            fileOutputStream.close();
            FileInputStream fileInputStream = openFileInput("moneyOnDay.txt");
            InputStreamReader reader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);

            StringBuilder stringBuilder = new StringBuilder();
            String lines = "";
            while ((lines = bufferedReader.readLine()) != null) {
                stringBuilder.append(lines).append("\n");
            }
            String string = stringBuilder.toString();
            if (string.isEmpty()) {

            } else {
                String[] arr = string.split("\n");
                Collections.addAll(arrayListMoneyOnDay, arr);
                Collections.reverse(arrayListMoneyOnDay);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        adapter = new ArrayAdapter<>(this,R.layout.design_list, R.id.listViewMoney, arrayListMoneyOnDay);
    }
}