package com.lessons.samocounter.main.classhelpers;

import static com.lessons.samocounter.MyConstKt.KEY_HOURS;

import android.content.Context;

import com.lessons.samocounter.GlobalDataHolder;
import com.lessons.samocounter.VariableData;


public class HoursArray {
    private static VariableData variableData = new VariableData();
    private static String hourseArray = "";

    public static void changeHoursArray(String newHoursElement, Context context){
        GlobalDataHolder globalDataHolder = new GlobalDataHolder(context);
        hourseArray = globalDataHolder.getSavedData(KEY_HOURS);
        if (hourseArray != null && hourseArray.contains(variableData.getDateText())){
            changeLastElement(newHoursElement);
        } else {
            hourseArray = hourseArray + "_" + newHoursElement;
        }
        globalDataHolder.saveData(KEY_HOURS, hourseArray);
    }

    public static String getTodayHours(Context context){
        GlobalDataHolder globalDataHolder = new GlobalDataHolder(context);
        hourseArray = globalDataHolder.getSavedData(KEY_HOURS);
        if (hourseArray != null && hourseArray.contains(variableData.getDateText())){
            String[] ar = hourseArray.split("_");
            String lastElement = ar[ar.length - 1];
            String[] arLasrElement = lastElement.split(" ");
            return arLasrElement[0];
        } else {
            return "9:00";
        }
    }

    private static void changeLastElement(String newHoursElement){
        String[] ar = hourseArray.split("_");
        ar[ar.length-1] = newHoursElement;
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : ar) {
            stringBuilder.append(string).append("_");
        }
        hourseArray = String.valueOf(stringBuilder);
    }
}
