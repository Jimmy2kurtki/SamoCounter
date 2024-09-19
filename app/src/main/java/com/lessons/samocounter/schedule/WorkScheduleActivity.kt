package com.lessons.samocounter.schedule

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.lessons.samocounter.MOUNTH
import com.lessons.samocounter.R
import com.lessons.samocounter.VariableData
import com.lessons.samocounter.databinding.ActivityScheduleBinding

class WorkScheduleActivity : AppCompatActivity() {

    lateinit var binding: ActivityScheduleBinding
    private val adapterRv = ScheduleAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val variableData = VariableData()
        val spinner = findViewById<Spinner>(R.id.spinnerSchedule)

        val adapterSpinner = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, variableData.getNameWorkers())
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapterSpinner

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
            rvSchedule.layoutManager = GridLayoutManager(this@WorkScheduleActivity, 3)
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

            val day = Day(img, "${list.indexOf(i)+1} $MOUNTH", i)
            mutableList.add(day)
        }
        return mutableList
    }
}