package com.lessons.samocounter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class CalendarSim extends AppCompatActivity {

    ArrayList<String> arrayListSim; //список всех сэмов

    ArrayList<String> arrayListForSpinner; //список дат для спиннера

    ArrayList<String> arrayListForListView; //список сэмов по выбранной дате для листа

    ArrayAdapter<String> adapterSpinnerSim;//адаптер для спиннера

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_sim);

        //получаем ВСЕ сэмики из user_data.txt в arrayListSim
        getData();

        //создание и подключение адаптера к спиннеру
        adapterSpinnerSim = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,arrayListForSpinner);
        adapterSpinnerSim.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinnerSim = findViewById(R.id.spinnerSim);
        spinnerSim.setAdapter(adapterSpinnerSim);

        //обработка выбора в спиннере
        spinnerSim.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //создаем список сэмов выбранной даты
                pickSimAtDate(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    void getData() {
        try {
            FileOutputStream fileOutput = openFileOutput("user_data.txt", MODE_APPEND);
            fileOutput.close();
            FileInputStream fileInput = openFileInput("user_data.txt");
            InputStreamReader reader = new InputStreamReader(fileInput);
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
                Collections.addAll(arrayListSim,arr);
                Collections.reverse(arrayListSim);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void pickSimAtDate(int i) {
        String s = arrayListForSpinner.get(i);

        for (int j = 0; j < arrayListSim.size(); j++){
            String sim = arrayListSim.get(j);
            boolean contains = sim.contains(s);
            if (contains) {
                arrayListForListView.add(sim);
            }
        }
    }
}