package com.lessons.samocounter.schedule.calendar;


import static com.lessons.samocounter.MyConstKt.KEY_SELECT;
import static com.lessons.samocounter.MyConstKt.MOUNTH;
import static com.lessons.samocounter.MyConstKt.NAME_PREF_SELECT_SPINNER;
import static com.lessons.samocounter.MyConstKt.YEAR;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

import com.lessons.samocounter.R;
import com.lessons.samocounter.VariableData;
import com.lessons.samocounter.schedule.WorkScheduleActivity;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import org.checkerframework.checker.nullness.qual.NonNull;

public class CalendarActivity extends AppCompatActivity {
    private final ArrayList<CalendarDay> markedDates = new ArrayList<>();
    private SharedPreferences pref;
    private int selectedWorker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        initAll();
        createCalendar();
    }

    private void initAll(){
        pref = getSharedPreferences(NAME_PREF_SELECT_SPINNER, MODE_PRIVATE);
        selectedWorker = pref.getInt(KEY_SELECT, 0);
    }

    private void createCalendar(){
        MaterialCalendarView materialCalendarView = findViewById(R.id.calendar_view);

        CalendarDay today = CalendarDay.today();
        materialCalendarView.setSelectedDate(today);
        MaterialCalendarView.State state = materialCalendarView.state();
        state.edit()
                .setFirstDayOfWeek(Calendar.MONDAY)
                .commit();

        addDate();

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                if (markedDates.contains(date)) {
                    // Если дата отмечена, делаем что-то
                    System.out.println("Выбранная дата отмеченная!");
                }
            }
        });

        DayViewDecorator decorator = new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                return markedDates.contains(day);
            }
            @Override
            public void decorate(DayViewFacade view) {
                view.addSpan(new DotSpan(10, getResources().getColor(R.color.black))); // Замените your_color на нужный цвет
            }
        };

        materialCalendarView.addDecorator(decorator);
    }

    private void addDate(){
        VariableData variableData = new VariableData();
        int[] arraySchedule = variableData.getScheduleWorkers(selectedWorker);
        for (int i = 0; i < arraySchedule.length; i++) {
            if(arraySchedule[i] == 1){
                markedDates.add(CalendarDay.from(YEAR,MOUNTH-1,i+1));
            }
        }
    }

    public void onBackPressed() {
        super.onBackPressed();

        try {
            Intent intent = new Intent(CalendarActivity.this, WorkScheduleActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {

        }
    }
}