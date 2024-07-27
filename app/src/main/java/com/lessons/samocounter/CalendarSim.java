package com.lessons.samocounter;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;


public class CalendarSim extends AppCompatActivity {

    DBHelper dbHelper; //БДшка

    ArrayList<String> arrayListSim = new ArrayList<>(); //список всех сэмов

    ArrayList<String> arrayListForSpinner = new ArrayList<>(); //список дат для спиннера

    ArrayList<String> arrayListForListView = new ArrayList<>(); //список сэмов по выбранной дате для листа

    ArrayAdapter<String> adapterSpinnerSim;//адаптер для спиннера

    ArrayAdapter<String> adapterListView; //адаптер для листа

    ListView listViewSimAtDate; //лист с сэмами

    TextView textViewSumSim, money; // поля с колвом сэмов и денег




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_sim);

        dbHelper = new DBHelper(this);

        money = findViewById(R.id.money);
        textViewSumSim = findViewById(R.id.textView_sumSim);


        //получаем ВСЕ сэмики и даты из БД в arrayListSim и arrayListForSpinner
        getData();


        //создание и подключение адаптера к спиннеру
        adapterSpinnerSim = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayListForSpinner);
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

        //лист с сэмиками
        adapterListView = new ArrayAdapter<>(this, R.layout.design_list, R.id.number_Sim, arrayListForListView);

        listViewSimAtDate = findViewById(R.id.listView);
        listViewSimAtDate.setAdapter(adapterListView);


    }

    void getData() {
        LinkedList<Data> list = dbHelper.GetAll();
        String text = "";
        String dateText = "";
        for(Data d:list) text = text + d.nameSim + " " + d.emh + " " + d.date + "\n";
        for(Data d:list) dateText = dateText + d.date + "\n";

        String[] arr = text.split("\n");
        Collections.addAll(arrayListSim, arr);
        Collections.reverse(arrayListSim);

        String[] arrDate = dateText.split("\n");
        Collections.addAll(arrayListForSpinner, arrDate);
        Set<String> set = new HashSet<>(arrayListForSpinner);
        arrayListForSpinner.clear();
        arrayListForSpinner.addAll(set);

    }

    void pickSimAtDate(int i) {
        String s = arrayListForSpinner.get(i);

        arrayListForListView.clear();

        for (int j = 0; j < arrayListSim.size(); j++){
            String sim = arrayListSim.get(j);
            boolean contains = sim.contains(s);
            if (contains) {
                arrayListForListView.add(sim);
            }
        }
        adapterListView.notifyDataSetChanged();
        countSimAndMoney(arrayListForListView.size());

    }

    void countSimAndMoney(int count){

        textViewSumSim.setText(String.valueOf(count));

        int intMoney;
        if (count <= 23) {
            intMoney = count * 146;
        } else if (count <= 30) {
            intMoney = ((count - 23) * 190) + (23 * 146);
        } else if (count <= 33) {
            intMoney = (7 * 190) + (23 * 146) + ((count - 23 - 7) * 210);
        } else {
            intMoney = (3 * 210) + (7 * 190) + (23 * 146) + ((count - 23 - 7 - 3) * 230);
        }
        money.setText(String.valueOf(intMoney));
    }

    public void onBackPressed() {
        super.onBackPressed();
        try {
            Intent intent = new Intent(CalendarSim.this, MainActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {

        }
    }


}