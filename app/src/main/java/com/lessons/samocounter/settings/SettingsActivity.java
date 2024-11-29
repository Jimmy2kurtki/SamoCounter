package com.lessons.samocounter.settings;

import static com.lessons.samocounter.MyConstKt.BARABAN;
import static com.lessons.samocounter.MyConstKt.CONTROLLER;
import static com.lessons.samocounter.MyConstKt.DASHBOARD;
import static com.lessons.samocounter.MyConstKt.KRILO;
import static com.lessons.samocounter.MyConstKt.KUROK;
import static com.lessons.samocounter.MyConstKt.MK;
import static com.lessons.samocounter.MyConstKt.RAMA;
import static com.lessons.samocounter.MyConstKt.RUCHKI;
import static com.lessons.samocounter.MyConstKt.SHINA;
import static com.lessons.samocounter.MyConstKt.STOIKA;
import static com.lessons.samocounter.MyConstKt.TROS;
import static com.lessons.samocounter.MyConstKt.ZAMOK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.lessons.samocounter.GlobalDataHolder;
import com.lessons.samocounter.R;
import com.lessons.samocounter.main.MainActivity;

import java.lang.reflect.Field;

public class SettingsActivity extends AppCompatActivity {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch settingsTire, settingsBrakedrum, settingsCable,
                   settingsRack, settingsDashboard, settingsTrigger,
                   settingsMk, settingsFender, settingsController,
                   settingsBrakehendle, settingsLock, settingsFrame;

    private SharedPreferences sharedPreferences;

    boolean settingsTireChecked, settingsBrakedrumChecked, settingsCableChecked,
            settingsRackChecked, settingsDashboardChecked, settingsTriggerChecked,
            settingsMkChecked, settingsFenderChecked, settingsControllerChecked,
            settingsBrakehendleChecked, settingsLockChecked, settingsFrameChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initAll();

        initAllChecked();

        getSwitchChecked();

        allSwitchListner();

    }

    private void initAll(){
        settingsTire = findViewById(R.id.settings_tire);
        settingsBrakedrum = findViewById(R.id.settings_brakedrum);
        settingsCable = findViewById(R.id.settings_cable);

        settingsRack = findViewById(R.id.settings_rack);
        settingsDashboard = findViewById(R.id.settings_dashboard);
        settingsTrigger = findViewById(R.id.settings_trigger);

        settingsMk = findViewById(R.id.settings_mk);
        settingsFender = findViewById(R.id.settings_fender);
        settingsController = findViewById(R.id.settings_controller);

        settingsBrakehendle = findViewById(R.id.settings_brakehendle);
        settingsLock = findViewById(R.id.settings_lock);
        settingsFrame = findViewById(R.id.settings_frame);


        sharedPreferences = getPreferences(Context.MODE_PRIVATE);

    }

    private void initAllChecked(){

        settingsTireChecked = sharedPreferences.getBoolean(SHINA,false);
        settingsBrakedrumChecked = sharedPreferences.getBoolean(BARABAN,false);
        settingsCableChecked = sharedPreferences.getBoolean(TROS,false);

        settingsRackChecked = sharedPreferences.getBoolean(STOIKA,false);
        settingsDashboardChecked = sharedPreferences.getBoolean(DASHBOARD,false);
        settingsTriggerChecked = sharedPreferences.getBoolean(KUROK,false);

        settingsMkChecked = sharedPreferences.getBoolean(MK,false);
        settingsFenderChecked = sharedPreferences.getBoolean(KRILO,false);
        settingsControllerChecked = sharedPreferences.getBoolean(CONTROLLER,false);

        settingsBrakehendleChecked = sharedPreferences.getBoolean(RUCHKI,false);
        settingsLockChecked = sharedPreferences.getBoolean(ZAMOK,false);
        settingsFrameChecked = sharedPreferences.getBoolean(RAMA,false);
    }

    private void getSwitchChecked(){
        settingsTire.setChecked(settingsTireChecked);
        settingsBrakedrum.setChecked(settingsBrakedrumChecked);
        settingsCable.setChecked(settingsCableChecked);

        settingsRack.setChecked(settingsRackChecked);
        settingsDashboard.setChecked(settingsDashboardChecked);
        settingsTrigger.setChecked(settingsTriggerChecked);

        settingsMk.setChecked(settingsMkChecked);
        settingsFender.setChecked(settingsFenderChecked);
        settingsController.setChecked(settingsControllerChecked);

        settingsBrakehendle.setChecked(settingsBrakehendleChecked);
        settingsLock.setChecked(settingsLockChecked);
        settingsFrame.setChecked(settingsFrameChecked);
    }

    private void allSwitchListner(){
        switchListner(settingsTire, SHINA);
        switchListner(settingsBrakedrum, BARABAN);
        switchListner(settingsCable, TROS);

        switchListner(settingsRack, STOIKA);
        switchListner(settingsDashboard, DASHBOARD);
        switchListner(settingsTrigger, KUROK);

        switchListner(settingsMk, MK);
        switchListner(settingsFender, KRILO);
        switchListner(settingsController, CONTROLLER);

        switchListner(settingsBrakehendle, RUCHKI);
        switchListner(settingsLock, ZAMOK);
        switchListner(settingsFrame, RAMA);
    }

    private void switchListner(@SuppressLint("UseSwitchCompatOrMaterialCode") Switch settings, String setting_key){
        settings.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(setting_key, b);
                editor.apply();
                GlobalDataHolder globalDataHolder = new GlobalDataHolder(SettingsActivity.this);
                globalDataHolder.saveData(setting_key, b);
            }
        });
    }

    public boolean isSettingChecked(String setingName) throws NoSuchFieldException, IllegalAccessException {
        Field field = this.getClass().getDeclaredField("settings" + setingName + "Checked");
        field.setAccessible(true);
        return field.getBoolean(this);
    }

    public void setSettingChecked(String settingName, boolean value) throws NoSuchFieldException, IllegalAccessException {
        Field field = this.getClass().getDeclaredField("settings" + settingName + "Checked");
        field.setAccessible(true);
        field.setBoolean(this,value);
    }

    public void onBackPressed() {
        super.onBackPressed();

        try {
            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {

        }
    }
}