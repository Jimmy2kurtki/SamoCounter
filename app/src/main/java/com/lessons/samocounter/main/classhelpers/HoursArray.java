package com.lessons.samocounter.main.classhelpers;

import static com.lessons.samocounter.MyConstKt.KEY_HOURS;

import android.content.Context;

import com.lessons.samocounter.GlobalDataHolder;
import com.lessons.samocounter.VariableData;


public class HoursArray {
    private static VariableData variableData = new VariableData();
    private static String hoursArray = "";

    public static void changeHoursArray(String newHoursElement, Context context){
        GlobalDataHolder globalDataHolder = new GlobalDataHolder(context);
        hoursArray = globalDataHolder.getSavedData(KEY_HOURS);
        if (hoursArray != null && hoursArray.contains(variableData.getDateText())){
            changeLastElement(newHoursElement);
        } else {
            if(hoursArray == null){
                hoursArray = newHoursElement + "_";
            }else {
                hoursArray = hoursArray + newHoursElement + "_";
            }
        }
        globalDataHolder.saveData(KEY_HOURS, hoursArray);
    }

    public static String getTodayHours(Context context){
        GlobalDataHolder globalDataHolder = new GlobalDataHolder(context);
        hoursArray = globalDataHolder.getSavedData(KEY_HOURS);
        if (hoursArray != null && hoursArray.contains(variableData.getDateText())){
            String[] ar = hoursArray.split("_");

            String lastElement = ar[ar.length - 2];
            String[] arLastElement = lastElement.split("-");
            return arLastElement[0];
        } else {
            return "9:00";
        }
    }

    private static void changeLastElement(String newHoursElement){
        String[] ar = hoursArray.split("_");
        ar[ar.length-1] = newHoursElement;
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : ar) {
            stringBuilder.append(string).append("_");
        }
        hoursArray = String.valueOf(stringBuilder);
    }
}
