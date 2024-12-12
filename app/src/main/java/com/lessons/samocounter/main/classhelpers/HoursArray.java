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
                hoursArray = newHoursElement;
            }else {
                hoursArray = hoursArray + "_" + newHoursElement;
            }
        }
        globalDataHolder.saveData(KEY_HOURS, hoursArray);
    }

    public static String getTodayHours(Context context) {
        GlobalDataHolder globalDataHolder = new GlobalDataHolder(context);
        String hoursArray = globalDataHolder.getSavedData(KEY_HOURS);

        if (hoursArray != null && hoursArray.contains(variableData.getDateText())) {
            String[] ar = hoursArray.split("_");

            String lastElement = ar[ar.length - 1];
            String[] arLastElement = lastElement.split("-");

            if (arLastElement.length > 0) {
                return arLastElement[0];
            } else {
                return "9:00";
            }
        } else {
            return "9:00";
        }
    }

    private static void changeLastElement(String newHoursElement){
        String[] ar = hoursArray.split("_");
        ar[ar.length-1] = newHoursElement;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ar.length; i++) {
            if(i == ar.length-1){
                stringBuilder.append(ar[i]);
            } else {
                stringBuilder.append(ar[i]).append("_");
            }
        }
        hoursArray = String.valueOf(stringBuilder);
    }
}
