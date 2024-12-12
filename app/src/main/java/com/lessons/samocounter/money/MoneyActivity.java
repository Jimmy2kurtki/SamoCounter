package com.lessons.samocounter.money;


import android.annotation.SuppressLint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.lessons.samocounter.R;
import com.lessons.samocounter.main.MainActivity;
import com.lessons.samocounter.money.classhelper.lists.ArrayListForSpinner;
import com.lessons.samocounter.money.classhelper.InitLists;
import com.lessons.samocounter.money.classhelper.texts.StringForTv;


public class MoneyActivity extends AppCompatActivity {

    private TextView tvMoneyAtDates, tvMoneyPerDay, tvHoursAtDates, tvHoursPerDay;
    private Spinner spinnerStartDate, spinnerFinishDate;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);

        initAll();

        StringForTv.TotalMoney.init(spinnerStartDate.getSelectedItemPosition(),spinnerFinishDate.getSelectedItemPosition());

        setTextAll();
    }

    private void initAll() {

        InitLists.initAll(MoneyActivity.this);

        initSp();
        initTv();


    }

    private void initTv() {
        tvMoneyAtDates = findViewById(R.id.tv_money_at_dates);
        tvMoneyPerDay = findViewById(R.id.tv_money_per_day);
        tvHoursAtDates = findViewById(R.id.tv_hours_at_date);
        tvHoursPerDay = findViewById(R.id.tv_hours_per_day);
    }


    private void initSp() {

        ArrayAdapter<String> adapterSpinnerSimStart = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, ArrayListForSpinner.Start.getArray());
        adapterSpinnerSimStart.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStartDate = findViewById(R.id.spinner_start_date);
        spinnerStartDate.setAdapter(adapterSpinnerSimStart);
        spinnerStartDate.setSelection(0);


        ArrayAdapter<String> adapterSpinnerSimFinish = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, ArrayListForSpinner.Start.getArray());
        adapterSpinnerSimFinish.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFinishDate = findViewById(R.id.spinner_finish_date);
        spinnerFinishDate.setAdapter(adapterSpinnerSimFinish);
        spinnerFinishDate.setSelection(ArrayListForSpinner.Start.getArray().size()-1);
    }

    private void setTextAll(){
        tvMoneyAtDates.setText(StringForTv.TotalMoney.getTotalMoney());
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