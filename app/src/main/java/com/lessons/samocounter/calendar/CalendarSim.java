package com.lessons.samocounter.calendar;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.lessons.samocounter.DB.DBHelper;
import com.lessons.samocounter.DB.Data;
import com.lessons.samocounter.MainActivity;
import com.lessons.samocounter.R;
import com.lessons.samocounter.money.MoneyCount;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
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

    TextView textViewSumSim, money, textViewSend; // поля с колвом сэмов и денег

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_sim);

        dbHelper = new DBHelper(this);

        money = findViewById(R.id.money);
        textViewSumSim = findViewById(R.id.textView_sumSim);
        textViewSend = findViewById(R.id.textViewSend);


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
        getListViewSimAtDate();

        textViewSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sendSim = "";
                ArrayList<String> arrayListForSEND = new ArrayList<>();
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                for(String s:arrayListForListView){
                    String[] arr = s.split(" ");
                    arrayListForSEND.add(arr[0]);
                }
                for(int i = 0; i <= arrayListForSEND.size(); i++){
                    if (i != arrayListForSEND.size()) {
                        sendSim = sendSim + (i + 1) + ") " + arrayListForSEND.get(i) + "\n";
                    } else {
                        String[] arr = arrayListForListView.get(0).split(" ");
                        sendSim = sendSim + arr[2];
                    }
                }
                sendIntent.putExtra(Intent.EXTRA_TEXT, sendSim);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, null));


            }
        });


    }

    private void getListViewSimAtDate(){
        listViewSimAtDate.setAdapter(adapterListView);

        listViewSimAtDate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CalendarSim.this);
                ConstraintLayout cl = (ConstraintLayout) getLayoutInflater().inflate(R.layout.dialog_delete,null);
                String removeString = arrayListForListView.get(i);
                String removeString1 = removeString.replaceAll("EASY", "");
                removeString1 = removeString1.replaceAll("NORM", "");
                removeString1 = removeString1.replaceAll("HARD", "");
                builder.setMessage(removeString1)
                        .setView(cl)
                        .setCancelable(true)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dbHelper.deleteOne(removeString);
                                adapterListView.notifyDataSetChanged();
                            }
                        })
                        .setNeutralButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                AlertDialog dialogDelete = builder.create();
                dialogDelete.show();
            }
        });
    }

    void getData() {
        LinkedList<Data> list = dbHelper.getAll();
        String text = "";
        String dateText = "";
        for(Data d:list) text = text + d.nameSim.replaceAll(".BIKE/\\?", "") + " " + d.emh + " " + d.date + "\n";
        for(Data d:list) dateText = dateText + d.date + "\n";

        String[] arr = text.split("\n");
        Collections.addAll(arrayListSim, arr);
        Collections.reverse(arrayListSim);

        String[] arrDate = dateText.split("\n");
        Collections.addAll(arrayListForSpinner, arrDate);
        Set<String> set = new LinkedHashSet<>(arrayListForSpinner);
        arrayListForSpinner.clear();
        arrayListForSpinner.addAll(set);
        Collections.reverse(arrayListForSpinner);

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
        MoneyCount moneyCount = new MoneyCount();
        textViewSumSim.setText(String.valueOf(arrayListForListView.size()));
        money.setText(String.valueOf(moneyCount.moneyCount(arrayListForListView.size(),true)));
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