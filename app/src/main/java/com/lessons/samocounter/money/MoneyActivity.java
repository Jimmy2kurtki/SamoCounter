package com.lessons.samocounter.money;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.lessons.samocounter.DBHelper;
import com.lessons.samocounter.Data;
import com.lessons.samocounter.MoneyCount;
import com.lessons.samocounter.main.MainActivity;
import com.lessons.samocounter.R;
import com.lessons.samocounter.VariableData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

public class MoneyActivity extends AppCompatActivity {

    private final VariableData variableData = new VariableData();
    private DBHelper dbHelper; //БДшка

    private final ArrayList<String> arrayListSim = new ArrayList<>(); //список всех сэмов

    private final ArrayList<String> arrayListForSpinnerStart = new ArrayList<>();  //список дат для спиннера левого

    private final ArrayList<String> arrayListForSpinnerFinish = new ArrayList<>();  //список дат для спиннера правого

    private TextView textViewMoneyAtDates, textViewMoneyPerDay, textViewSimAtDates, textViewSimPerDay;

    private String startDate;

    private String finishDate = variableData.getDateText();

    private int indexDataStart, indexDataFinish, totalMoney, moneyPerDay, totalSim, simPerDay, indexStart, indexFinish;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);

        dbHelper = new DBHelper(this);

        textViewMoneyAtDates = findViewById(R.id.textView_money_at_dates);
        textViewMoneyPerDay = findViewById(R.id.textView_money_per_day);
        textViewSimAtDates = findViewById(R.id.textView_sim_at_date);
        textViewSimPerDay = findViewById(R.id.textView_sim_per_day);

        getData();

        //адаптер для спиннера левого
        ArrayAdapter<String> adapterSpinnerSimStart = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayListForSpinnerStart);
        adapterSpinnerSimStart.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinnerStartDate = findViewById(R.id.spinner_start_date);
        spinnerStartDate.setAdapter(adapterSpinnerSimStart);

        //адаптер для спиннера правого
        ArrayAdapter<String> adapterSpinnerSimFinish = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayListForSpinnerFinish);
        adapterSpinnerSimFinish.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinnerFinishDate = findViewById(R.id.spinner_finish_date);
        spinnerFinishDate.setAdapter(adapterSpinnerSimFinish);

        //обработка выбора в спиннере левом
        spinnerStartDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                totalMoney = 0;
                moneyPerDay = 0;
                //задаем в строку startDate выбор
                setStartDate(i);

                pickSimForDates(getStartDate(),getFinishDate(),getIndexDataStart(),getIndexDataFinish());

                setTextViewMoney();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //обработка выбора в спиннере правом
        spinnerFinishDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                totalMoney = 0;
                moneyPerDay = 0;

                //задаем в строку finishDate выбор
                setFinishDate(i);

                pickSimForDates(getStartDate(),getFinishDate(),getIndexDataStart(),getIndexDataFinish());

                setTextViewMoney();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerFinishDate.setSelection(arrayListForSpinnerFinish.size()-1);

    }

    public void setTextViewMoney(){
        textViewMoneyAtDates.setText(String.valueOf(totalMoney));
        textViewMoneyPerDay.setText(String.valueOf(moneyPerDay));
        textViewSimAtDates.setText(String.valueOf(totalSim));
        textViewSimPerDay.setText(String.valueOf(simPerDay));
    }

    public void pickSimForDates(String start,String finish,int indexDataStart, int indexDataFinish){

        ArrayList<String> al = new ArrayList<>(arrayListSim);

        Collections.reverse(al);

        String[] arr = new String[al.size()];

        for (int i = 0; i < arr.length; i++){
            arr[i] = al.get(i);
        }

        for(int i = 0; i < arr.length; i++){
            String s = arr[i];
            boolean contains = s.contains(start);
            if(contains){
                indexStart = i;
                break;
            }
        }

        for (int i = arr.length-1; i > 0; i--){
            String s = arr[i];
            boolean contains = s.contains(finish);
            if(contains){
                indexFinish = i+1;
                break;
            }
        }

        if (indexStart <= indexFinish) {
            String[] cutListSim = Arrays.copyOfRange(arr, indexStart, indexFinish);

            if (indexDataStart <= indexDataFinish) {
                String[] arr1 = new String[arrayListForSpinnerStart.size()];

                for (int i = 0; i < arr1.length; i++) {
                    arr1[i] = arrayListForSpinnerStart.get(i);
                }

                // обрезать массив arr1  в cutListSpinner             1 индекс            2 индекс
                String[] cutListSpinner = Arrays.copyOfRange(arr1, indexDataStart, indexDataFinish + 1);

                circleForCutListSim(cutListSim, cutListSpinner);
            }

        }
    }

    public void circleForCutListSim(String[] cutListSim, String[] cutListSpinner) {
        int countSimInTotalSim = 0;
        ArrayList<Integer> countSimAtDates = new ArrayList<>();

        for (int i = 0; i < cutListSpinner.length; i++) {

            int countSim = 0;

            for (int x = 0; x < cutListSim.length; x++){

                boolean contains = cutListSim[x].contains(cutListSpinner[i]);
                if (contains) {
                    countSim++;
                    countSimInTotalSim++;
                }
            }

            if(countSim != 0) {
                countSimAtDates.add(countSim);
            }
        }
        sumMoney(countSimAtDates);
        totalSim = countSimInTotalSim;

        if (countSimAtDates.size() != 0) {
            simPerDay = totalSim / countSimAtDates.size();
        }
    }

    public void sumMoney(ArrayList<Integer> countSimAtDates) {
        MoneyCount moneyCount = new MoneyCount();
        for (int i = 0; i < countSimAtDates.size(); i++) {

            countSimAtDates.set(i, moneyCount.moneyCount(countSimAtDates.get(i),true));

        }

        for (int i = 0; i < countSimAtDates.size(); i++) {

            totalMoney = totalMoney + countSimAtDates.get(i);

        }

        if (countSimAtDates.size() != 0) {
            moneyPerDay = totalMoney / countSimAtDates.size();
        }

    }


    void getData() {
        LinkedList<Data> list = dbHelper.getAll();
        String text = "";
        String dateText = "";
        for (Data d : list) text = text + d.nameSim + " " + d.emh + " " + d.date + "\n";
        for (Data d : list) dateText = dateText + d.date + "\n";

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
    }



    void setStartDate(int i) {
        startDate = arrayListForSpinnerStart.get(i);
        indexDataStart = i;
    }

    public String getStartDate() {

        return startDate;
    }

    public int getIndexDataStart() {
        return indexDataStart;
    }

    public void setFinishDate(int i) {
        finishDate = arrayListForSpinnerFinish.get(i);
        indexDataFinish = i;
    }

    public String getFinishDate() {

        return finishDate;
    }

    public int getIndexDataFinish() {
        return indexDataFinish;
    }

    public void onBackPressed() {
        super.onBackPressed();
        try {
            Intent intent = new Intent(MoneyActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {

        }
    }

}