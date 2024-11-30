package com.lessons.samocounter.main;

import static com.lessons.samocounter.MyConstKt.BARABAN;
import static com.lessons.samocounter.MyConstKt.CONTROLLER;
import static com.lessons.samocounter.MyConstKt.DASHBOARD;
import static com.lessons.samocounter.MyConstKt.KRILO;
import static com.lessons.samocounter.MyConstKt.KUROK;
import static com.lessons.samocounter.MyConstKt.MK;
import static com.lessons.samocounter.MyConstKt.NAME_CHECKBOX_WORK_BRAKEDRUM;
import static com.lessons.samocounter.MyConstKt.NAME_CHECKBOX_WORK_BRAKEHANDLE;
import static com.lessons.samocounter.MyConstKt.NAME_CHECKBOX_WORK_CABLE;
import static com.lessons.samocounter.MyConstKt.NAME_CHECKBOX_WORK_CONTROLLER;
import static com.lessons.samocounter.MyConstKt.NAME_CHECKBOX_WORK_DASHBOARD;
import static com.lessons.samocounter.MyConstKt.NAME_CHECKBOX_WORK_FENDER;
import static com.lessons.samocounter.MyConstKt.NAME_CHECKBOX_WORK_FRAME;
import static com.lessons.samocounter.MyConstKt.NAME_CHECKBOX_WORK_LOCK;
import static com.lessons.samocounter.MyConstKt.NAME_CHECKBOX_WORK_MK;
import static com.lessons.samocounter.MyConstKt.NAME_CHECKBOX_WORK_RACK;
import static com.lessons.samocounter.MyConstKt.NAME_CHECKBOX_WORK_TIRE;
import static com.lessons.samocounter.MyConstKt.NAME_CHECKBOX_WORK_TRIGGER;
import static com.lessons.samocounter.MyConstKt.RAMA;
import static com.lessons.samocounter.MyConstKt.RUCHKI;
import static com.lessons.samocounter.MyConstKt.SHINA;
import static com.lessons.samocounter.MyConstKt.STOIKA;
import static com.lessons.samocounter.MyConstKt.TROS;
import static com.lessons.samocounter.MyConstKt.ZAMOK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.lessons.samocounter.DBHelper;
import com.lessons.samocounter.Data;
import com.lessons.samocounter.GlobalDataHolder;
import com.lessons.samocounter.MoneyCount;
import com.lessons.samocounter.R;
import com.lessons.samocounter.VariableData;
import com.lessons.samocounter.money.MoneyActivity;
import com.lessons.samocounter.otherDays.OtherDaysActivity;
import com.lessons.samocounter.qrsim.VremennoeReshenieActivity;
import com.lessons.samocounter.schedule.WorkScheduleActivity;
import com.lessons.samocounter.settings.SettingsActivity;
import com.lessons.samocounter.snake.MainActivitySnake;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;


public class MainActivity extends AppCompatActivity {

    private final VariableData variableData = new VariableData();

    private ArrayAdapter<String> adapter;
    private DBHelper dbHelper;

    private TextView textViewEasy, textViewNorm, textViewHard, textViewSumSim, money, buttonEasy, 
            buttonNorm, buttonHard, allButtonsForMethod;
    private ListView listView;

    private int intTextViewEasy, intTextViewNorm, intTextViewHard, intTextViewSumSim;
    private final ArrayList<String> arrayListSim = new ArrayList<>();
    private long backPressedTime;
    private final MoneyCount moneyCount = new MoneyCount();

    boolean[] checkboxState;

    boolean settingsTireChecked, settingsBrakedrumChecked, settingsCableChecked,
            settingsRackChecked, settingsDashboardChecked, settingsTriggerChecked,
            settingsMkChecked, settingsFenderChecked, settingsControllerChecked,
            settingsBrakehendleChecked, settingsLockChecked, settingsFrameChecked;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LeftMaterialDrawer();

        initAll();

        prefireTouchListener(buttonEasy, buttonNorm, buttonHard);

        getDataBD();

        getCountSimTXT();

        createListView();
    }

    private void initAll(){
        dbHelper = new DBHelper(this);
        money = findViewById(R.id.money);
        textViewEasy = findViewById(R.id.textView_easy);
        textViewNorm = findViewById(R.id.textView_norm);
        textViewHard = findViewById(R.id.textView_hard);
        textViewSumSim = findViewById(R.id.textView_sumSim);
        buttonEasy = findViewById(R.id.button_easy);
        buttonNorm = findViewById(R.id.button_norm);
        buttonHard = findViewById(R.id.button_hard);
        listView = findViewById(R.id.listView);

        checkboxState = new boolean[12];

        adapter = new ArrayAdapter<>(this, R.layout.design_list, R.id.number_Sim, arrayListSim);
    }

    private void createListView() {
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                ConstraintLayout cl = (ConstraintLayout) getLayoutInflater().inflate(R.layout.dialog_delete_main, null);
                String removeString = arrayListSim.get(position);
                String removeString1 = removeString.replaceAll("EASY", "");
                removeString1 = removeString1.replaceAll("NORM", "");
                removeString1 = removeString1.replaceAll("HARD", "");
                removeString1 = removeString1.replaceAll(variableData.getDateText(), "");
                builder.setView(cl)
                        .setCancelable(true);

                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                final TextView textViewAlert = cl.findViewById(R.id.textView_sim);
                textViewAlert.setText(removeString1);
                TextView positiveButton = cl.findViewById(R.id.button_positive);
                TextView negativeButton = cl.findViewById(R.id.button_negative);

                positiveButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        removeSim(removeString, position);
                        adapter.notifyDataSetChanged();
                        alertDialog.dismiss();
                    }
                });

                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    private void LeftMaterialDrawer() {
        PrimaryDrawerItem workSchedule = new PrimaryDrawerItem().withIdentifier(7).withName("Work Schedule")
                .withSelectable(false).withTextColor(Color.rgb(80, 90, 100));
        PrimaryDrawerItem otherDays = new PrimaryDrawerItem().withIdentifier(2).withName("Other Days")
                .withSelectable(false).withTextColor(Color.rgb(80, 90, 100));
        PrimaryDrawerItem money = new PrimaryDrawerItem().withIdentifier(3).withName("Money")
                .withSelectable(false).withTextColor(Color.rgb(80, 90, 100));
        PrimaryDrawerItem generateQr = new PrimaryDrawerItem().withIdentifier(5).withName("Generate QR")
                .withSelectable(false).withTextColor(Color.rgb(80, 90, 100));
        PrimaryDrawerItem snake = new PrimaryDrawerItem().withIdentifier(4).withName("Snake")
                .withSelectable(false).withTextColor(Color.rgb(80, 90, 100));
        PrimaryDrawerItem settings = new PrimaryDrawerItem().withIdentifier(6).withName("Settings")
                .withSelectable(false).withTextColor(Color.rgb(80, 90, 100));

        Drawer result = newDrawerBuilder(workSchedule, otherDays, money, generateQr, snake, settings);

        int color = Color.parseColor("#505963");

        result.getDrawerLayout().setStatusBarBackgroundColor(color);
        clickOnLeftMaterialDrawer(otherDays, OtherDaysActivity.class);
        clickOnLeftMaterialDrawer(workSchedule,WorkScheduleActivity.class);
        clickOnLeftMaterialDrawer(money, MoneyActivity.class);
        clickOnLeftMaterialDrawer(snake,MainActivitySnake.class);
        clickOnLeftMaterialDrawer(generateQr, VremennoeReshenieActivity.class);
        clickOnLeftMaterialDrawer(settings, SettingsActivity.class);
    }

    private Drawer newDrawerBuilder(PrimaryDrawerItem workSchedule, PrimaryDrawerItem otherDays,
                                    PrimaryDrawerItem money, PrimaryDrawerItem generateQr,
                                    PrimaryDrawerItem snake, PrimaryDrawerItem settings) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextAppearance(this, R.style.AppToolbarTitleStyle);

        DrawerBuilder builder = new DrawerBuilder()
                .withSelectedItem(-1)
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        new DividerDrawerItem(), new DividerDrawerItem(), new DividerDrawerItem(),
                        workSchedule,
                        new DividerDrawerItem(), new DividerDrawerItem(), new DividerDrawerItem(),
                        otherDays,
                        new DividerDrawerItem(), new DividerDrawerItem(), new DividerDrawerItem(),
                        money,
                        new DividerDrawerItem(), new DividerDrawerItem(), new DividerDrawerItem(),
                        generateQr,
                        new DividerDrawerItem(), new DividerDrawerItem(), new DividerDrawerItem(),
                        snake,
                        new DividerDrawerItem(), new DividerDrawerItem(), new DividerDrawerItem(),
                        new DividerDrawerItem(), new DividerDrawerItem(), new DividerDrawerItem(),
                        settings,
                        new DividerDrawerItem(), new DividerDrawerItem(), new DividerDrawerItem()
                );

        Drawer result = builder.build();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                result.getDrawerLayout(),
                toolbar,
                R.string.open_drawer,
                R.string.close_drawer
        );
        result.getDrawerLayout().addDrawerListener(toggle);
        toggle.syncState();

        return result;
    }

    private void clickOnLeftMaterialDrawer(PrimaryDrawerItem primaryDrawerItem,Class<?> cls){
        primaryDrawerItem.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent intent = new Intent(MainActivity.this, cls);
                startActivity(intent);
                finish();
                return false;
            }
        });
    }

    private void removeSim(String removeString, int i) {

        boolean indexEasy = removeString.contains("EASY");
        boolean indexNorm = removeString.contains("NORM");
        boolean indexData = removeString.contains(variableData.getDateText());
        if (indexData) {
            if (indexEasy) {
                intTextViewEasy--;
                textViewEasy.setText(String.valueOf(intTextViewEasy));
            } else if (indexNorm) {
                intTextViewNorm--;
                textViewNorm.setText(String.valueOf(intTextViewNorm));
            } else {
                intTextViewHard--;
                textViewHard.setText(String.valueOf(intTextViewHard));
            }
            intTextViewSumSim = intTextViewEasy + intTextViewNorm + intTextViewHard;
            moneyCount.moneyCount(intTextViewSumSim,true);
            money.setText(String.valueOf(moneyCount.moneyCount(intTextViewSumSim,true)));
            textViewSumSim.setText(String.valueOf(intTextViewSumSim));
            dbHelper.deleteOne(removeString);
            arrayListSim.remove(i);

            saveCountSimTXT();
        }
    }

    private void setAllCountSim(@NonNull TextView btn) {
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
        intTextViewSumSim = intTextViewEasy + intTextViewNorm + intTextViewHard;
        moneyCount.moneyCount(intTextViewSumSim,true);
        money.setText(String.valueOf(moneyCount.moneyCount(intTextViewSumSim,true)));
        textViewSumSim.setText(String.valueOf(intTextViewSumSim));
        saveCountSimTXT();
    }

    private void saveDataBD(String numberSim, String strBtn) {

        Data data = new Data(numberSim, strBtn, variableData.getDateText());
        dbHelper.addOne(data);
    }

    private void getDataBD() {
        LinkedList<Data> list = dbHelper.getAll();

        String text = "";
        for (Data d : list)
            if (d.date.equals(variableData.getDateText()))
                text = text + d.nameSim + " " + d.emh + " " + d.date + "\n";

        if (text.isEmpty()) {

        } else {
            String[] arr = text.split("\n");

            Collections.addAll(arrayListSim, arr);
            Collections.reverse(arrayListSim);
            createListView();
        }

    }

    private void saveCountSimTXT() {
        String allCountForTxt = (intTextViewEasy + " " + intTextViewNorm + " " + intTextViewHard + " " + variableData.getDateText());

        try {
            FileOutputStream fileOutput = openFileOutput("countSim.txt", MODE_PRIVATE);
            fileOutput.write((allCountForTxt).getBytes());
            fileOutput.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getCountSimTXT() {
        try {
            FileOutputStream fileOutput = openFileOutput("countSim.txt", MODE_APPEND);
            fileOutput.close();
            FileInputStream fileInput = openFileInput("countSim.txt");
            InputStreamReader reader = new InputStreamReader(fileInput);
            BufferedReader bufferedReader = new BufferedReader(reader);

            StringBuilder stringBuilder = new StringBuilder();
            String lines;
            while ((lines = bufferedReader.readLine()) != null) {
                stringBuilder.append(lines).append(" ");
            }
            String string = stringBuilder.toString();
            if (string.isEmpty()) {

            } else {
                String[] arr = string.split(" ");
                if (arr[3].equals(variableData.getDateText())) {
                    intTextViewEasy = Integer.parseInt(arr[0]);
                    intTextViewNorm = Integer.parseInt(arr[1]);
                    intTextViewHard = Integer.parseInt(arr[2]);
                    intTextViewSumSim = intTextViewEasy + intTextViewNorm + intTextViewHard;
                    moneyCount.moneyCount(intTextViewSumSim,true);
                    textViewSumSim.setText(String.valueOf(intTextViewSumSim));

                    textViewEasy.setText(String.valueOf(intTextViewEasy));
                    textViewNorm.setText(String.valueOf(intTextViewNorm));
                    textViewHard.setText(String.valueOf(intTextViewHard));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        moneyCount.moneyCount(intTextViewSumSim,true);
        money.setText(String.valueOf(moneyCount.moneyCount(intTextViewSumSim,true)));
    }

    private void setNumberSimInArrayListSim(String numberSim, TextView btn) {
        numberSim = numberSim.toUpperCase();
        numberSim = numberSim.replaceAll("\\s+", "");
        numberSim = numberSim.replaceAll("EASY", "");
        numberSim = numberSim.replaceAll("NORM", "");
        numberSim = numberSim.replaceAll("HARD", "");
        numberSim = numberSim.replaceAll(variableData.getDateText(), "");
        if (numberSim.isEmpty()) {
            numberSim = "-----";
        }
        String strBtn = "";
        if (btn.equals(buttonEasy)) {
            strBtn = "EASY";
        } else if (btn.equals(buttonNorm)) {
            strBtn = "NORM";
        } else {
            strBtn = "HARD";
        }

        setAllCountSim(btn);
        arrayListSim.add(0, numberSim + " " + strBtn + " " + variableData.getDateText());
        createListView();

        saveDataBD(numberSim, strBtn);
    }

    private void prefireTouchListener(TextView z, TextView x, TextView c) {
        touchListener((View) z);
        touchListener((View) x);
        touchListener((View) c);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void touchListener(View v) {
        TextView b = (TextView) v;
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog(b);
            }
        });
        b.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                clickScanner(b);
                return false;
            }
        });
    }

    private void initAllChecked(){

        GlobalDataHolder globalDataHolder = new GlobalDataHolder(this);

        settingsTireChecked = globalDataHolder.getSavedData(SHINA);
        settingsBrakedrumChecked = globalDataHolder.getSavedData(BARABAN);
        settingsCableChecked = globalDataHolder.getSavedData(TROS);

        settingsRackChecked = globalDataHolder.getSavedData(STOIKA);
        settingsDashboardChecked = globalDataHolder.getSavedData(DASHBOARD);
        settingsTriggerChecked = globalDataHolder.getSavedData(KUROK);

        settingsMkChecked = globalDataHolder.getSavedData(MK);
        settingsFenderChecked = globalDataHolder.getSavedData(KRILO);
        settingsControllerChecked = globalDataHolder.getSavedData(CONTROLLER);

        settingsBrakehendleChecked = globalDataHolder.getSavedData(RUCHKI);
        settingsLockChecked = globalDataHolder.getSavedData(ZAMOK);
        settingsFrameChecked = globalDataHolder.getSavedData(RAMA);
    }

    private void customDialog(TextView btn) {
        initAllChecked();
        boolean isChecked = settingsTireChecked || settingsBrakedrumChecked || settingsCableChecked ||
                settingsRackChecked || settingsDashboardChecked || settingsTriggerChecked ||
                settingsMkChecked || settingsFenderChecked || settingsControllerChecked ||
                settingsBrakehendleChecked || settingsLockChecked || settingsFrameChecked;

        boolean[] arrayIsChecked = {settingsTireChecked, settingsBrakedrumChecked, settingsCableChecked,
                settingsRackChecked, settingsDashboardChecked, settingsTriggerChecked,
                settingsMkChecked, settingsFenderChecked, settingsControllerChecked,
                settingsBrakehendleChecked, settingsLockChecked, settingsFrameChecked};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ConstraintLayout cl = (ConstraintLayout) getLayoutInflater().inflate(R.layout.dialog_scooter_number_main, null);
        builder.setCancelable(false)
                .setView(cl);

        if (isChecked) {
            GridLayout checkBoxesContainer = cl.findViewById(R.id.gridLayout_checkboxes);
            checkBoxesContainer.setColumnCount(3);
            String[] options = {NAME_CHECKBOX_WORK_TIRE, NAME_CHECKBOX_WORK_BRAKEDRUM,
                    NAME_CHECKBOX_WORK_CABLE, NAME_CHECKBOX_WORK_RACK, NAME_CHECKBOX_WORK_DASHBOARD,
                    NAME_CHECKBOX_WORK_TRIGGER, NAME_CHECKBOX_WORK_MK, NAME_CHECKBOX_WORK_FENDER,
                    NAME_CHECKBOX_WORK_CONTROLLER, NAME_CHECKBOX_WORK_BRAKEHANDLE,
                    NAME_CHECKBOX_WORK_LOCK, NAME_CHECKBOX_WORK_FRAME};
            int coord = 0;
            for (int i = 0; i < options.length; i++) {
                if (arrayIsChecked[i]) {
                    CheckBox checkBox = (CheckBox) getLayoutInflater().inflate(R.layout.checkbox_alertdialog_main, null);
                    checkBox.setText(options[i]);
                    final int index = i;
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            checkboxState[index] = b;
                        }
                    });
                    GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                    params.rowSpec = GridLayout.spec(coord / 3); // Определяем строку
                    params.columnSpec = GridLayout.spec(coord % 3); // Определяем столбец
                    checkBox.setLayoutParams(params);
                    coord++;
                    checkBoxesContainer.addView(checkBox);
                }
            }
        }

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        final EditText editTextUserNumber = cl.findViewById(R.id.editText_user_number);

        TextView positiveButton = cl.findViewById(R.id.button_positive);
        TextView negativeButton = cl.findViewById(R.id.button_negative);

        positiveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String numberSim = editTextUserNumber.getText().toString();
                setNumberSimInArrayListSim(numberSim, btn);
                alertDialog.dismiss();
            }
        });

        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

    }

    private String stringCheckBoxState(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < checkboxState.length; i++) {
            if(checkboxState[i]) {
                stringBuilder.append("1");
            } else {
                stringBuilder.append("0");
            }
        }
        return stringBuilder.toString();
    }

    private void clickScanner(TextView b) {
        IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.setPrompt("Scan a QR code");
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        intentIntegrator.initiateScan();
        allButtonsForMethod = b;
    }

    //результат сканирования QR
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            String contents = intentResult.getContents();
            if (contents != null) {
                contents = contents.replaceAll("https://wsh.bike/\\?s=", "");
                contents = contents.replaceAll("https://wsh.bike\\?s=", "");
                contents = contents.replaceAll("https", "");
                contents = contents.replaceAll("//wsh.bike\\?s=", "");
                contents = contents.replaceAll("//wsh", "");
                contents = contents.replaceAll(".bike\\?s=", "");
                contents = contents.replaceAll("s=", "");
                contents = contents.replaceAll(":", "");
                contents = contents.replaceAll("\\\\", "");
                contents = contents.replaceAll("BIKE/\\?", "");
                setNumberSimInArrayListSim(contents, allButtonsForMethod);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onBackPressed() {

        Toast backToast = null;

        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Нажмите еще раз, что бы выйти", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}