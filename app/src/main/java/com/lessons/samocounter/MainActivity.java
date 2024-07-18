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
    private int intTextViewEasy, intTextViewNorm, intTextViewHard;
    private ArrayList<String> arrayListSim = new ArrayList<>();



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



        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,reversedArrayListSim());
        listView.setAdapter(adapter);

    }

    public ArrayList<String>  reversedArrayListSim(){
        ArrayList<String> reversedArrayListSim = new ArrayList<>(arrayListSim);
        Collections.reverse(reversedArrayListSim);
        return reversedArrayListSim;
    }

    public void setCountSim(Button btn) {
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
                    numberSim = "-----";
                }
                setCountSim(btn);
                arrayListSim.add(numberSim);
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
            Date currentDate = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            String dateText = dateFormat.format(currentDate);

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
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}