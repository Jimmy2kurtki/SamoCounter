package com.lessons.samocounter

import com.lessons.samocounter.schedule.Day


const val VITYA = "Янковский В"
const val DEGTER = "Дегтерев Д"
const val FARA = "Ибрагимов А"
const val ILYUSHA = "Степаненко И"
const val CHECK = "Чекмарев Д"
const val YA = "Никитин М"
const val ZAHAR = "Машуков З"
const val KARCEV = "Карцев Д"
const val LEHA = "Вокин А"
const val OST = "Островский А"
const val SZHENYA = "практиканты Женя"
const val VOLODYA = "практиканты Володя"

const val MOUNTH = "September"

class VariableData{
    private val imageIdList = listOf(
        R.drawable.weekend,
        R.drawable.work8,
        R.drawable.work10
    )

    val  singleElement = R.layout.single_element

    private val vityaSchedule   = intArrayOf(0,0, 1,1,1,2, 0,0, 2,1,1,1, 0,0, 2,2,2,1, 0,0, 1,2,2,2, 0,0, 1,1,1,1)
    private val degterSchedule  = intArrayOf(1,1,1, 0,0, 2,2,2,2, 0,0, 1,1,2,2, 0,0, 1,1,1,1, 0,0, 2,2,1,1, 0,0, 1)
    private val faraSchedule    = intArrayOf(1,1,1, 0,0, 2,2,2,2, 0,0, 1,1,2,2, 0,0, 1,1,1,1, 0,0, 2,2,1,1, 0,0, 1)
    private val ilyushaSchedule = intArrayOf(0, 1,1,1,1, 0,0, 2,2,1,1, 0,0, 2,2,2,2, 0,0, 1,1,2,2, 0,0, 1,1,1,1, 0)
    private val checkSchedule   = intArrayOf(0,0, 1,1,1,2, 0,0, 2,1,1,1, 0,0, 2,2,2,1, 0,0, 1,2,2,2, 0,0, 1,1,1,1)
    private val yaSchedule      = intArrayOf(0, 1,1,1,1, 0,0, 1,1,2,2, 0,0, 2,2,2,2, 0,0, 1,1,1,1, 0,0, 2,2,2,2, 0)
    private val zaharSchedule   = intArrayOf(1,1,1,1, 0,0, 1,1,1,2, 0,0, 2,1,1,1, 0,0, 2,2,2,1, 0,0, 1,2,2,2, 0,0)
    private val karcevSchedule  = intArrayOf(1, 0,0, 1,1,1,1, 0,0, 2,2,2,2, 0,0, 1,1,2,2, 0,0, 1,1,1,1, 0,0, 2,2,1)
    private val lehaSchedule    = intArrayOf(1,1, 0,0, 1,1,1,1, 0,0, 2,2,2,1, 0,0, 1,2,2,2, 0,0, 1,1,1,2, 0,0, 2,1)
    private val ostSchedule     = intArrayOf(1,1,1, 0,0, 1,1,1,1, 0,0, 2,2,1,1, 0,0, 2,2,2,2, 0,0, 1,1,2,2, 0,0, 1)
    private val szhenyaSchedule = intArrayOf(1, 0,0, 1,1,1,1, 0,0, 2,2,2,2, 0,0, 1,1,2,2, 0,0, 1,1,1,1, 0,0, 2,2,1)
    private val volodyaSchedule = intArrayOf(1, 0,0, 1,1,1,1, 0,0, 2,2,2,2, 0,0, 1,1,2,2, 0,0, 1,1,1,1, 0,0, 2,2,1)


    private val nameWorkers = listOf<String>(VITYA,DEGTER,FARA,ILYUSHA,CHECK,YA,ZAHAR,KARCEV,LEHA,OST,SZHENYA,VOLODYA)

    private val scheduleWorkers = listOf<IntArray>(vityaSchedule,degterSchedule,faraSchedule,ilyushaSchedule,
        checkSchedule,yaSchedule,zaharSchedule,karcevSchedule,lehaSchedule,ostSchedule,szhenyaSchedule,volodyaSchedule)

    fun getImageId(intImage: Int): Int{
        return imageIdList[intImage]
    }

    fun getNameWorkers(): List<String>{
        return nameWorkers
    }

    fun getScheduleWorkers(intName: Int): IntArray{
        return when(intName){
            0 -> vityaSchedule
            1 -> degterSchedule
            2 -> faraSchedule
            3 -> ilyushaSchedule
            4 -> checkSchedule
            5 -> yaSchedule
            6 -> zaharSchedule
            7 -> karcevSchedule
            8 -> lehaSchedule
            9 -> ostSchedule
            10 -> szhenyaSchedule
            else -> volodyaSchedule
        }
    }



}