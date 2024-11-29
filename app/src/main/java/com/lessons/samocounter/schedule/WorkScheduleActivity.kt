package com.lessons.samocounter.schedule

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.lessons.samocounter.KEY_SELECT
import com.lessons.samocounter.main.MainActivity
import com.lessons.samocounter.R
import com.lessons.samocounter.VariableData
import com.lessons.samocounter.databinding.ActivityScheduleBinding
import com.lessons.samocounter.NAME_PREF_SELECT_SPINNER
import com.lessons.samocounter.schedule.calendar.CalendarActivity

class WorkScheduleActivity : AppCompatActivity() {

    private lateinit var spinner: Spinner
    private lateinit var btnCalendar: TextView
    private lateinit var pref: SharedPreferences
    private lateinit var binding: ActivityScheduleBinding

    private val variableData = VariableData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAll()

        btnCalendar.setOnClickListener{
            editPref()
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
            finish()
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {

                init(createDaysList(variableData.getScheduleWorkers(position)))
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }

    }

    private fun initAll(){

        pref = getSharedPreferences(NAME_PREF_SELECT_SPINNER, MODE_PRIVATE)
        val selectedWorker = pref.getInt(KEY_SELECT,0)
        val adapterSpinner = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, variableData.getNameWorkers())
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner = findViewById(R.id.spinnerSchedule)
        spinner.adapter = adapterSpinner
        spinner.setSelection(selectedWorker)

        btnCalendar = findViewById(R.id.btnCalendar)
    }

    private fun init(list:MutableList<Day>){

        val adapterRv = ScheduleAdapter()

        binding.apply {
            rvSchedule.layoutManager = GridLayoutManager(this@WorkScheduleActivity, 6)
            rvSchedule.adapter = adapterRv
            adapterRv.addList(list)
        }
    }

    private fun createDaysList(list: IntArray): MutableList<Day>{
        var mutableList = mutableListOf<Day>()
        for(i in list){
            var img:Int


            if(i==0) img = R.drawable.weekend
            else if(i==1) img = R.drawable.work8
            else img = R.drawable.work10

            val day = Day(img, i)
            mutableList.add(day)
        }
        return mutableList
    }

    private fun editPref(){
        val edit: SharedPreferences.Editor = pref.edit()
        edit.putInt(KEY_SELECT,spinner.selectedItemPosition)
        edit.apply()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        editPref()

        try {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } catch (e: Exception) {
        }
    }
}