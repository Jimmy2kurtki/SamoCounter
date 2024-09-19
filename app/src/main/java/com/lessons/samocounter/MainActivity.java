package com.lessons.samocounter;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.lessons.samocounter.calendar.CalendarSim;
import com.lessons.samocounter.DB.DBHelper;
import com.lessons.samocounter.DB.Data;
import com.lessons.samocounter.money.MoneyCount;
import com.lessons.samocounter.qrsim.VremennoeReshenie;
import com.lessons.samocounter.schedule.WorkScheduleActivity;
import com.lessons.samocounter.snake.MainActivitySnake;
import com.lessons.samocounter.money.Money;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Date currentDate = new Date();
    DateFormat dateFormat = new SimpleDateFormat("dd.MM", Locale.getDefault());
    String dateText = dateFormat.format(currentDate);
    ArrayAdapter<String> adapter;
    DBHelper dbHelper;
    SharedPreferences pref;
    private TextView textViewEasy, textViewNorm, textViewHard, textViewSumSim, money;
    private TextView buttonEasy, buttonNorm, buttonHard;
    private ListView listView;
    private int intTextViewEasy = 0, intTextViewNorm = 0, intTextViewHard = 0, intTextViewSumSim, intMoney;
    private ArrayList<String> arrayListSim = new ArrayList<>();
    private long backPressedTime;
    private Toast backToast;
    private TextView allButtonsForMethod;
    private androidx.appcompat.widget.Toolbar Toolbar;

    MoneyCount moneyCount = new MoneyCount();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LeftMaterialDrawer();

        pref = getSharedPreferences("ID", MODE_PRIVATE);

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

        //решение проблемы со слушателем (нужно было 2 раза нажать)
        prefireTouchListener(buttonEasy);
        prefireTouchListener(buttonNorm);
        prefireTouchListener(buttonHard);

        //получение сэмов из бд в arrayListSim
        getData();

        //получение сэмов из countSim.txt в intTextViewSumSim
        getCountSim();

        //лист с сэмиками
        adapter = new ArrayAdapter<>(this, R.layout.design_list, R.id.number_Sim, arrayListSim);
        getListView();


    }

    //лист с сэмами
    public void getListView() {

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                ConstraintLayout cl = (ConstraintLayout) getLayoutInflater().inflate(R.layout.dialog_delete, null);
                String removeString = arrayListSim.get(position);
                String removeString1 = removeString.replaceAll("EASY", "");
                removeString1 = removeString1.replaceAll("NORM", "");
                removeString1 = removeString1.replaceAll("HARD", "");
                removeString1 = removeString1.replaceAll(dateText, "");
                builder.setMessage(removeString1)
                        .setView(cl)
                        .setCancelable(true)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                removeSim(removeString, position);
                                adapter.notifyDataSetChanged();
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
        })
        ;
    }
    @SuppressLint("ResourceAsColor")
    private void LeftMaterialDrawer(){

        PrimaryDrawerItem workSchedule = new  PrimaryDrawerItem().withIdentifier(7).withName("Work Schedule")
                .withSelectable(false).withTextColor(Color.rgb(80, 90, 100));
        PrimaryDrawerItem calendar = new  PrimaryDrawerItem().withIdentifier(2).withName("Calendar")
                .withSelectable(false).withTextColor(Color.rgb(80, 90, 100));
        PrimaryDrawerItem money = new  PrimaryDrawerItem().withIdentifier(3).withName("Money")
                .withSelectable(false).withTextColor(Color.rgb(80, 90, 100));
        PrimaryDrawerItem generateQr = new  PrimaryDrawerItem().withIdentifier(5).withName("Generate QR")
                .withSelectable(false).withTextColor(Color.rgb(80, 90, 100));
        PrimaryDrawerItem calculatorSim = new  PrimaryDrawerItem().withIdentifier(5).withName("Calculator SIM")
                .withSelectable(false).withTextColor(Color.rgb(80, 90, 100));
        PrimaryDrawerItem dataTransfer = new  PrimaryDrawerItem().withIdentifier(6).withName("Data Transfer")
                .withSelectable(false).withTextColor(Color.rgb(80, 90, 100));
        PrimaryDrawerItem snake = new  PrimaryDrawerItem().withIdentifier(4).withName("Snake")
                .withSelectable(false).withTextColor(Color.rgb(80, 90, 100));

        Drawer result = new DrawerBuilder()
                .withSelectedItem(-1)
                .withActivity(this)
                .withToolbar(Toolbar)
                .addDrawerItems(
                        new DividerDrawerItem(), new DividerDrawerItem(), new DividerDrawerItem(),
                        workSchedule,
                        new DividerDrawerItem(), new DividerDrawerItem(), new DividerDrawerItem(),
                        calendar,
                        new DividerDrawerItem(), new DividerDrawerItem(), new DividerDrawerItem(),
                        money,
                        new DividerDrawerItem(), new DividerDrawerItem(), new DividerDrawerItem(),
                        generateQr,
                        new DividerDrawerItem(), new DividerDrawerItem(), new DividerDrawerItem(),
                        calculatorSim,
                        new DividerDrawerItem(), new DividerDrawerItem(), new DividerDrawerItem(),
                        dataTransfer,
                        new DividerDrawerItem(), new DividerDrawerItem(), new DividerDrawerItem(),
                        snake,
                        new DividerDrawerItem(), new DividerDrawerItem(), new DividerDrawerItem()
                )
                .build();

        int color = Color.parseColor("#D18B00");
        result.getDrawerLayout().setStatusBarBackgroundColor(color);
        calendar.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent intent = new Intent(MainActivity.this, CalendarSim.class);
                startActivity(intent);
                finish();
                return false;
            }
        });
        workSchedule.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent intent = new Intent(MainActivity.this, WorkScheduleActivity.class);
                startActivity(intent);
                finish();
                return false;
            }
        });
        money.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent intent = new Intent(MainActivity.this, Money.class);
                startActivity(intent);
                finish();
                return false;
            }
        });
        snake.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent intent = new Intent(MainActivity.this, MainActivitySnake.class);
                startActivity(intent);
                finish();
                return false;
            }
        });
        generateQr.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent intent = new Intent(MainActivity.this, VremennoeReshenie.class);
                startActivity(intent);
                finish();
                return false;
            }
        });
        calculatorSim.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent intent = new Intent(MainActivity.this, Calculator.class);
                startActivity(intent);
                finish();
                return false;
            }
        });
        dataTransfer.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent intent = new Intent(MainActivity.this, DataTransfer.class);
                startActivity(intent);
                finish();
                return false;
            }
        });
    }



    //удаление сэма
    public void removeSim(String removeString, int i) {

        boolean indexEasy = removeString.contains("EASY");
        boolean indexNorm = removeString.contains("NORM");
        boolean indexData = removeString.contains(dateText);
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

            saveCountSim();
        }
    }

    //подсчет и вывод колва сэмов на экран
    public void setCountSim(@NonNull TextView btn) {
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
        saveCountSim();
    }

    //сохранение номера сэма в бд
    public void saveData(String numberSim, String strBtn) {

        Data data = new Data(numberSim, strBtn, dateText);
        dbHelper.addOne(data);
    }

    //получение всех сэмов из бд
    public void getData() {
        LinkedList<Data> list = dbHelper.getAll();
        String text = "";
        for (Data d : list)
            if (d.date.equals(dateText))
                text = text + d.nameSim + " " + d.emh + " " + d.date + "\n";

        if (text.isEmpty()) {

        } else {
            String[] arr = text.split("\n");

            Collections.addAll(arrayListSim, arr);
            Collections.reverse(arrayListSim);
            getListView();
        }
    }

    //сохранение сегодняшнее колво сэмов в тхт
    public void saveCountSim() {
        String allCountForTxt = (intTextViewEasy + " " + intTextViewNorm + " " + intTextViewHard + " " + dateText);

        try {
            FileOutputStream fileOutput = openFileOutput("countSim.txt", MODE_PRIVATE);
            fileOutput.write((allCountForTxt).getBytes());
            fileOutput.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //получение сегодняшнего колва сэмов из тхт
    public void getCountSim() {
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
                if (arr[3].equals(dateText)) {
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


    //добавление номера сэма в список
    public void setNumberSim(String numberSim, TextView btn) {
        numberSim = numberSim.toUpperCase();
        numberSim = numberSim.replaceAll("\\s+", "");
        numberSim = numberSim.replaceAll("EASY", "");
        numberSim = numberSim.replaceAll("NORM", "");
        numberSim = numberSim.replaceAll("HARD", "");
        numberSim = numberSim.replaceAll(dateText, "");
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

        setCountSim(btn);
        arrayListSim.add(0, numberSim + " " + strBtn + " " + dateText);
        getListView();

        saveData(numberSim, strBtn);
    }

    public void prefireTouchListener(TextView b) {
        View v = (View) b;
        touchListener(v);
    }

    //слушатели кнопок
    @SuppressLint("ClickableViewAccessibility")
    public void touchListener(View v) {
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

    //номер сэма текстом
    private void customDialog(TextView btn) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ConstraintLayout cl = (ConstraintLayout) getLayoutInflater().inflate(R.layout.dialog, null);
        builder.setCancelable(false)
                .setView(cl);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AlertDialog alertDialog = (AlertDialog) dialogInterface;
                EditText editTextUserNumber = alertDialog.findViewById(R.id.editText_user_number);
                assert editTextUserNumber != null;
                String numberSim = editTextUserNumber.getText().toString();
                setNumberSim(numberSim, btn);
            }
        }).setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
    }

    //номер сэма QRкодом
    public void clickScanner(TextView b) {
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
                contents = contents.replaceAll("https://wsh.bike\\?s=", "");
                contents = contents.replaceAll("https", "");
                contents = contents.replaceAll("//wsh.bike\\?s=", "");
                contents = contents.replaceAll("//wsh", "");
                contents = contents.replaceAll(".bike\\?s=", "");
                contents = contents.replaceAll("s=", "");
                contents = contents.replaceAll(":", "");
                contents = contents.replaceAll("\\\\", "");
                contents = contents.replaceAll(".BIKE/\\?", "");
                setNumberSim(contents, allButtonsForMethod);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    //кнопка назад
    public void onBackPressed() {

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