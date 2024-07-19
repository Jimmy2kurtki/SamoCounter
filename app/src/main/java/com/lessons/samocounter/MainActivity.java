package com.lessons.samocounter;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView textViewEasy,textViewNorm,textViewHard,textViewSumSim;
    private Button buttonEasy,buttonNorm,buttonHard;
    private ListView listView;
    private int intTextViewEasy = 0, intTextViewNorm = 0, intTextViewHard = 0;
    private ArrayList<String> arrayListSim = new ArrayList<>();
    private ArrayList<String> reversedArrayListSim = new ArrayList<>();
    Date currentDate = new Date();
    DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
    String dateText = dateFormat.format(currentDate);



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewEasy = findViewById(R.id.textView_easy);
        textViewNorm = findViewById(R.id.textView_norm);
        textViewHard = findViewById(R.id.textView_hard);
        textViewSumSim = findViewById(R.id.textView_sumSim);
        buttonEasy = findViewById(R.id.button_easy);
        buttonNorm = findViewById(R.id.button_norm);
        buttonHard = findViewById(R.id.button_hard);
        listView = findViewById(R.id.listView);
        getData();
        getCountSim();
        getListViev();


    }
    public void getListViev() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.design_list,R.id.number_Sim,reversedArrayListSim);
        listView.setAdapter(adapter);
    }


    public void setCountSim(@NonNull Button btn) {
        if (btn.equals(buttonEasy)) {
            intTextViewEasy++;
            textViewEasy.setText(String.valueOf(intTextViewEasy));
        } else if (btn.equals(buttonNorm)) {
            intTextViewNorm++;
            textViewNorm.setText(String.valueOf(intTextViewNorm));
        } else {
            intTextViewHard++;
            textViewHard.setText(String.valueOf(intTextViewHard));
        }
        textViewSumSim.setText(String.valueOf(intTextViewEasy+intTextViewNorm+intTextViewHard));
        saveCountSim();
    }

    public void clickOnButton(View v) {
            customDialog((Button)v);
    }

    private void customDialog(Button btn){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ConstraintLayout cl = (ConstraintLayout) getLayoutInflater().inflate(R.layout.dialog,null);
        builder.setCancelable(false)
                .setView(cl);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AlertDialog alertDialog = (AlertDialog) dialogInterface;
                EditText editTextUserNumber = alertDialog.findViewById(R.id.editText_user_number);
                String numberSim = editTextUserNumber.getText().toString();
                numberSim.replaceAll("\\s+","");
                if (numberSim.isEmpty()) {
                    numberSim = "- " + dateText;
                }
                setCountSim(btn);
                arrayListSim.add(numberSim + " " + dateText);
                reversedArrayListSim.clear();
                reversedArrayListSim.addAll(arrayListSim);
                Collections.reverse(reversedArrayListSim);
                getListViev();

                saveData(numberSim);
            }
        }).setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();


    }

    public void saveData(String numberSim){
        try {
            FileOutputStream fileOutput = openFileOutput("user_data.txt",MODE_APPEND);
            fileOutput.write((numberSim + " " + dateText + "\n").getBytes());
            fileOutput.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void getData(){
        try {
            FileOutputStream fileOutput = openFileOutput("user_data.txt",MODE_APPEND);
            fileOutput.close();
            FileInputStream fileInput = openFileInput("user_data.txt");
            InputStreamReader reader = new InputStreamReader(fileInput);
            BufferedReader bufferedReader = new BufferedReader(reader);

            StringBuilder stringBuilder = new StringBuilder();
            String lines = "";
            while ((lines = bufferedReader.readLine()) != null){
                stringBuilder.append(lines).append("\n");
            }
            String string = stringBuilder.toString();
            if (string.isEmpty()) {

            } else {
                String[] arr = string.split("\n");
                Collections.addAll(arrayListSim,arr);
                reversedArrayListSim.clear();
                reversedArrayListSim.addAll(arrayListSim);
                Collections.reverse(reversedArrayListSim);
                getListViev();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveCountSim(){
        String allCountForTxt = (intTextViewEasy + " " + intTextViewNorm + " " + intTextViewHard + " " + dateText);

        try {
            FileOutputStream fileOutput = openFileOutput("countSim.txt",MODE_PRIVATE);
            fileOutput.write((allCountForTxt).getBytes());
            fileOutput.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void getCountSim(){
        try {
            FileOutputStream fileOutput = openFileOutput("countSim.txt",MODE_APPEND);
            fileOutput.close();
            FileInputStream fileInput = openFileInput("countSim.txt");
            InputStreamReader reader = new InputStreamReader(fileInput);
            BufferedReader bufferedReader = new BufferedReader(reader);

            StringBuilder stringBuilder = new StringBuilder();
            String lines = "";
            while ((lines = bufferedReader.readLine()) != null){
                stringBuilder.append(lines).append(" ");
            }
            String string = stringBuilder.toString();
            if (string.isEmpty()) {

            } else {
                String[] arr = string.split(" ");
                if (arr[3].equals(dateText)) {
                    intTextViewEasy = Integer.parseInt(arr[0]);
                    intTextViewNorm = Integer.parseInt(arr[1]);
                    intTextViewHard = Integer.parseInt(arr[2]);
                    textViewSumSim.setText(String.valueOf(intTextViewEasy+intTextViewNorm+intTextViewHard));
                    textViewEasy.setText(String.valueOf(intTextViewEasy));
                    textViewNorm.setText(String.valueOf(intTextViewNorm));
                    textViewHard.setText(String.valueOf(intTextViewHard));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}