package com.lessons.samocounter

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class VariableData{
    private val currentDate = Date()
    private val dateFormat = SimpleDateFormat("dd.MM", Locale.getDefault())
    private val dateText = dateFormat.format(currentDate)

    val  singleElement = R.layout.element_rv_schedule

    private val imageIdList = listOf(
        R.drawable.weekend,
        R.drawable.work8,
        R.drawable.work10
    )

    private val vityaSchedule   = intArrayOf(0,  1,  1,  1,  1,  0,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0)
    private val degterSchedule  = intArrayOf(1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1)
    private val faraSchedule    = intArrayOf(1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1)
    private val litvinSchedule  = intArrayOf(1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1)
    private val checkSchedule   = intArrayOf(0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0)
    private val yaSchedule      = intArrayOf(1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1)

    private val zaharSchedule   = intArrayOf(1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1)
    private val szhenyaSchedule = intArrayOf(0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0)
    private val karcevSchedule  = intArrayOf(0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0)
    private val lehaSchedule    = intArrayOf(1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1)
    private val volodyaSchedule = intArrayOf(1,  0,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1)
    private val tumashSchedule  = intArrayOf(1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1)

    private val nameWorkers = listOf<String>(VITYA,DEGTER,FARA,LITVIN,CHECK,YA,ZAHAR,SZHENYA,KARCEV,LEHA,VOLODYA,TUMASH)

    private val scheduleWorkers = listOf<IntArray>(vityaSchedule,degterSchedule,faraSchedule, litvinSchedule,
        checkSchedule,yaSchedule,zaharSchedule,szhenyaSchedule,karcevSchedule,lehaSchedule,volodyaSchedule,tumashSchedule)

    fun getImageId(intImage: Int): Int{
        return imageIdList[intImage]
    }
  
    fun getNameWorkers(): List<String>{
        return nameWorkers
    }

    fun getScheduleWorkers(intName: Int): IntArray{
        return scheduleWorkers[intName]
    }

    fun getDateText(): String{
        return dateText
    }
}