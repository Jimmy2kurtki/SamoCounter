package com.lessons.samocounter.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.lessons.samocounter.MoneyCount;
import com.lessons.samocounter.R;
import com.lessons.samocounter.VariableData;
import com.lessons.samocounter.main.classhelpers.CheckboxArray;
import com.lessons.samocounter.main.classhelpers.ClearNumberSim;
import com.lessons.samocounter.main.classhelpers.FilterRemove;
import com.lessons.samocounter.main.classhelpers.SendSimCalc;
import com.lessons.samocounter.money.MoneyActivity;
import com.lessons.samocounter.otherDays.OtherDaysActivity;
import com.lessons.samocounter.qrsim.VremennoeReshenieActivity;
import com.lessons.samocounter.schedule.WorkScheduleActivity;
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

    private TextView textViewSumSim, money, buttonNorm, allButtonsForMethod;
    private ListView listView;

    private int intTextViewEasy, intTextViewNorm, intTextViewHard, intTextViewSumSim;
    private final ArrayList<String> arrayListSimFull = new ArrayList<>();
    private final ArrayList<String> arrayListSim = new ArrayList<>();
    private long backPressedTime;
    private final MoneyCount moneyCount = new MoneyCount();

    boolean[] checkboxState;

    String[] checkboxArray = CheckboxArray.getCheckboxArray();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LeftMaterialDrawer();

        initAll();

        prefireTouchListener(buttonNorm);

        getDataBD();

        getCountSimTXT();

        createListView();
    }

    private void initAll(){
        dbHelper = new DBHelper(this);
        money = findViewById(R.id.money);
        textViewSumSim = findViewById(R.id.textView_sumSim);
        buttonNorm = findViewById(R.id.button_norm);
        listView = findViewById(R.id.listView);

        checkboxState = new boolean[27];

        adapter = new ArrayAdapter<>(this, R.layout.design_list, R.id.number_Sim, arrayListSim);


    }

    private void createListView() {
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                FilterRemove.getFiltret(arrayListSimFull, arrayListSim, position);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                ConstraintLayout cl = (ConstraintLayout) getLayoutInflater().inflate(R.layout.dialog_info_main, null);

                builder.setView(cl)
                                .setCancelable(true);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                GridLayout checkBoxesContainer = cl.findViewById(R.id.gridLayout_checkboxes_info);
                checkBoxesContainer.setColumnCount(3);

                int coord = 0;
                for (int i = 0; i < checkboxArray.length; i++) {
                    if (FilterRemove.checkboxStateInfo[i].equals("1")) {
                        TextView textView = (TextView) getLayoutInflater().inflate(R.layout.textview_alertdialog_main_info, null);
                        textView.setText(checkboxArray[i]);

                        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                        params.rowSpec = GridLayout.spec(coord / 3);
                        params.columnSpec = GridLayout.spec(coord % 3);
                        textView.setLayoutParams(params);
                        coord++;
                        checkBoxesContainer.addView(textView);
                    }
                }

                final TextView textViewAlert = cl.findViewById(R.id.textView_sim_info);
                textViewAlert.setText(FilterRemove.removeString1);


                TextView positiveButton = cl.findViewById(R.id.button_positive_info);
                TextView sendButton  = cl.findViewById(R.id.button_send_info);
                TextView negativeButton = cl.findViewById(R.id.button_delete_info);

                positiveButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {

                        alertDialog.dismiss();
                    }
                });

                sendButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SendSimCalc.calc(checkboxArray);
                        Intent sendIntent = new Intent(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, SendSimCalc.sendSim);
                        sendIntent.setType("text/plain");
                        startActivity(Intent.createChooser(sendIntent, null));
                    }
                });

                String finalRemoveString = FilterRemove.removeString1;
                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialogDelete(position, FilterRemove.removeString, finalRemoveString);
                        alertDialog.dismiss();
                    }
                });
            }
        });
    }

    private void AlertDialogDelete(int position, String removeString, String removeString1){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        ConstraintLayout cl = (ConstraintLayout) getLayoutInflater().inflate(R.layout.dialog_delete_main, null);

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

        Drawer result = newDrawerBuilder(workSchedule, otherDays, money, generateQr, snake);

        int color = Color.parseColor("#505963");

        result.getDrawerLayout().setStatusBarBackgroundColor(color);
        clickOnLeftMaterialDrawer(otherDays, OtherDaysActivity.class);
        clickOnLeftMaterialDrawer(workSchedule,WorkScheduleActivity.class);
        clickOnLeftMaterialDrawer(money, MoneyActivity.class);
        clickOnLeftMaterialDrawer(snake,MainActivitySnake.class);
        clickOnLeftMaterialDrawer(generateQr, VremennoeReshenieActivity.class);
    }

    private Drawer newDrawerBuilder(PrimaryDrawerItem workSchedule, PrimaryDrawerItem otherDays,
                                    PrimaryDrawerItem money, PrimaryDrawerItem generateQr,
                                    PrimaryDrawerItem snake) {
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

        boolean indexData = removeString.contains(variableData.getDateText());
        if (indexData) {
            intTextViewNorm--;
            intTextViewSumSim = intTextViewNorm;
            moneyCount.moneyCount(intTextViewSumSim,true);
            money.setText(String.valueOf(moneyCount.moneyCount(intTextViewSumSim,true)));
            textViewSumSim.setText(String.valueOf(intTextViewSumSim));
            dbHelper.deleteOne(removeString);
            arrayListSim.remove(i);
            arrayListSimFull.remove(i);

            saveCountSimTXT();
        }
    }

    private void setAllCountSim(@NonNull TextView btn) {

        intTextViewNorm++;
        intTextViewSumSim = intTextViewNorm;
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
        String textFull = "";
        for (Data d : list)
            if (d.date.equals(variableData.getDateText()))
                text = text + d.nameSim + " " + d.date + "\n";
        for(Data d : list)
            if (d.date.equals(variableData.getDateText()))
                textFull = textFull + d.nameSim + " " + d.emh + " "  + d.date + "\n";

        if (text.isEmpty()) {

        } else {
            String[] arr = text.split("\n");
            String[] arrFull = textFull.split("\n");

            Collections.addAll(arrayListSim, arr);
            Collections.reverse(arrayListSim);
            Collections.addAll(arrayListSimFull, arrFull);
            Collections.reverse(arrayListSimFull);

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

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        moneyCount.moneyCount(intTextViewSumSim,true);
        money.setText(String.valueOf(moneyCount.moneyCount(intTextViewSumSim,true)));
    }

    private void setNumberSimInArrayListSim(String numberSim, TextView btn) {
        numberSim = ClearNumberSim.clear(numberSim);
        String strBtn = stringCheckBoxState();

        setAllCountSim(btn);
        arrayListSimFull.add(0, numberSim + " " + strBtn + " " + variableData.getDateText());
        arrayListSim.add(0, numberSim + " " + variableData.getDateText());
        createListView();

        saveDataBD(numberSim, strBtn);
    }

    private void prefireTouchListener(TextView z) {
        touchListener((View) z);
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


    private void customDialog(TextView btn) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ConstraintLayout cl = (ConstraintLayout) getLayoutInflater().inflate(R.layout.dialog_scooter_number_main, null);
        builder.setCancelable(false)
                .setView(cl);

        GridLayout checkBoxesContainer = cl.findViewById(R.id.gridLayout_checkboxes);
        checkBoxesContainer.setColumnCount(3);
        int coord = 0;
        for (int i = 0; i < checkboxArray.length; i++) {
            CheckBox checkBox = (CheckBox) getLayoutInflater().inflate(R.layout.checkbox_alertdialog_main, null);
            checkBox.setText(checkboxArray[i]);
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
        checkboxState = new boolean[12];
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