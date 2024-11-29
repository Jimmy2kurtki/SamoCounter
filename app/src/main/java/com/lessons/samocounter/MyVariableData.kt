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

    private val vityaSchedule   = intArrayOf(0,  1,  1,  1,  1,  0,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1)
    private val degterSchedule  = intArrayOf(1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1)
    private val faraSchedule    = intArrayOf(1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1)
    private val nurgSchedule    = intArrayOf(1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0)
    private val checkSchedule   = intArrayOf(0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0)
    private val yaSchedule      = intArrayOf(1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0)

    private val zaharSchedule   = intArrayOf(1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1)
    private val szhenyaSchedule = intArrayOf(0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1)
    private val karcevSchedule  = intArrayOf(0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1)
    private val lehaSchedule    = intArrayOf(1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1)
    private val ostSchedule     = intArrayOf(1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0)
    private val volodyaSchedule = intArrayOf(1,  0,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1)

    private val nameWorkers = listOf<String>(VITYA,DEGTER,FARA,NURG,CHECK,YA,ZAHAR,KARCEV,LEHA,OST,SZHENYA,VOLODYA)

    private val scheduleWorkers = listOf<IntArray>(vityaSchedule,degterSchedule,faraSchedule, nurgSchedule,
        checkSchedule,yaSchedule,zaharSchedule,karcevSchedule,lehaSchedule,ostSchedule,szhenyaSchedule,volodyaSchedule)

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