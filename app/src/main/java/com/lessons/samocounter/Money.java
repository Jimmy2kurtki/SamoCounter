package com.lessons.samocounter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

public class Money extends AppCompatActivity {

    DBHelper dbHelper; //БДшка

    ArrayList<String> arrayListSim = new ArrayList<>(); //список всех сэмов

    ArrayList<String> arrayListForSpinnerStart = new ArrayList<>();  //список дат для спиннера левого

    ArrayList<String> arrayListForSpinnerFinish = new ArrayList<>();  //список дат для спиннера правого

    ArrayAdapter<String> adapterSpinnerSimStart;//адаптер для спиннера левого

    ArrayAdapter<String> adapterSpinnerSimFinish;//адаптер для спиннера правого

    TextView textViewMoneyAtDates, textViewMoneyPerDay;

    Spinner spinnerStartDate, spinnerFinishDate;



    String startDate;


    String finishDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);

        dbHelper = new DBHelper(this);


        getData();

        adapterSpinnerSimStart = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayListForSpinnerStart);
        adapterSpinnerSimStart.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStartDate = findViewById(R.id.spinner_start_date);
        spinnerStartDate.setAdapter(adapterSpinnerSimStart);

        adapterSpinnerSimFinish = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayListForSpinnerFinish);
        adapterSpinnerSimFinish.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFinishDate = findViewById(R.id.spinner_finish_date);
        spinnerFinishDate.setAdapter(adapterSpinnerSimFinish);

        //обработка выбора в спиннере левом
        spinnerStartDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //задаем в строку startDate выбор
                setStartDate(i);
                pickDates(getStartDate(),getFinishDate());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //обработка выбора в спиннере правом
        spinnerFinishDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //задаем в строку finishDate выбор
                setFinishDate(i);
                pickDates(getStartDate(),getFinishDate());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    void setStartDate(int i){
        startDate = arrayListForSpinnerStart.get(i);
    }

    public String getStartDate() {
        return startDate;
    }

    public void setFinishDate(int i) {
        finishDate = arrayListForSpinnerFinish.get(i);
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void pickDates(String start,String finish){


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
        Collections.addAll(arrayListForSpinnerStart, arrDate);
        Set<String> set = new LinkedHashSet<>(arrayListForSpinnerStart);

        arrayListForSpinnerStart.clear();
        arrayListForSpinnerStart.addAll(set);

        arrayListForSpinnerFinish.clear();
        arrayListForSpinnerFinish.addAll(arrayListForSpinnerStart);
        Collections.reverse(arrayListForSpinnerFinish);
    }

    public void onBackPressed() {
        super.onBackPressed();
        try {
            Intent intent = new Intent(Money.this, CalendarSim.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {

        }
    }
}