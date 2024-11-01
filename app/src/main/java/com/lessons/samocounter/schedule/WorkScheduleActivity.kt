package com.lessons.samocounter.schedule

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.lessons.samocounter.KEY_SELECT
import com.lessons.samocounter.MOUNTH
import com.lessons.samocounter.MainActivity
import com.lessons.samocounter.R
import com.lessons.samocounter.VariableData
import com.lessons.samocounter.databinding.ActivityScheduleBinding
import com.lessons.samocounter.NAME_PREF_SELECT_SPINNER
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WorkScheduleActivity : AppCompatActivity() {

    var selectedWorker: Int = 0
    lateinit var binding: ActivityScheduleBinding
    private val adapterRv = ScheduleAdapter()
    private lateinit var pref: SharedPreferences
    lateinit var spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = getSharedPreferences(NAME_PREF_SELECT_SPINNER, MODE_PRIVATE)
        selectedWorker = pref.getInt(KEY_SELECT,0)

        val variableData = VariableData()

        val adapterSpinner = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, variableData.getNameWorkers())
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner = findViewById(R.id.spinnerSchedule)
        spinner.adapter = adapterSpinner
        spinner.setSelection(selectedWorker)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {

                init(createDaysList(variableData.getScheduleWorkers(position)))
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }
    }

    private fun init(list:MutableList<Day>){

        binding.apply {
            rvSchedule.layoutManager = GridLayoutManager(this@WorkScheduleActivity, 6)
            rvSchedule.adapter = adapterRv
            adapterRv.addList(list)
        }
    }

    fun createDaysList(list: IntArray): MutableList<Day>{
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
    override fun onBackPressed() {
        super.onBackPressed()
//тут
        val edit: SharedPreferences.Editor = pref.edit()
        edit.putInt(KEY_SELECT,spinner.selectedItemPosition)
        edit.apply()
        try {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } catch (e: Exception) {
            // Handle exception if needed
        }
    }
}