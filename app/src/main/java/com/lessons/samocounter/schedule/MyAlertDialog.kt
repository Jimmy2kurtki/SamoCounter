package com.lessons.samocounter.schedule

import android.view.View
import androidx.appcompat.app.AlertDialog
import com.lessons.samocounter.MOUNTH
import com.lessons.samocounter.VariableData

class MyAlertDialog {

    private val variableData = VariableData()

    fun alertDialogShift(position: Int, parent: View){
        val arrayNamesShift = mutableListOf<String>()
        for(i in 0..11){
            for(j in 0..(changeOfShift(position).size-1)){
                if(i == changeOfShift(position)[j]){
                    arrayNamesShift.add(variableData.getNameWorkers()[i])
                }
            }
        }

        val stringBuilder = StringBuilder()
        for (i in 0..arrayNamesShift.size-1) {
            stringBuilder.append(arrayNamesShift[i]).append("\n")
        }
        val string: String = stringBuilder.toString()
        var positoinString: String = (position+1).toString()
        if (position < 9) positoinString = "0$positoinString"
        AlertDialog.Builder(parent.context).setTitle("Подмениться $positoinString.${MOUNTH} с...")
            .setMessage(string).create().show()
    }

    fun changeOfShift(position: Int): MutableList<Int>{
        val arrayShift = mutableListOf<Int>()
        for(i in 0..11){
            arrayShift.add(variableData.getScheduleWorkers(i)[position])
        }
        val arrayNumbersWorkers = mutableListOf<Int>()
        for(i in 0..11){
            if(arrayShift[i] == 0) arrayNumbersWorkers.add(i)
        }
        return arrayNumbersWorkers
    }
}