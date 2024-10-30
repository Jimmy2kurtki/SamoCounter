package com.lessons.samocounter

import com.lessons.samocounter.schedule.Day


const val VITYA = "Янковский В"
const val DEGTER = "Дегтерев Д"
const val FARA = "Ибрагимов А"
const val NURG = "Нургалеев К"
const val CHECK = "Чекмарев Д"
const val YA = "Никитин М"
const val ZAHAR = "Машуков З"
const val SZHENYA = "Казанцев Е"
const val KARCEV = "Карцев Д"
const val LEHA = "Вокин А"
const val OST = "Островский А"
const val VOLODYA = "Сичькарь В"

const val MOUNTH = ".11"

class VariableData{
    private val imageIdList = listOf(
        R.drawable.weekend,
        R.drawable.work8,
        R.drawable.work10
    )

    val  singleElement = R.layout.single_element
// поделить на выходные и рабочие в интерфейсе
    // кнопка подмениться
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

}