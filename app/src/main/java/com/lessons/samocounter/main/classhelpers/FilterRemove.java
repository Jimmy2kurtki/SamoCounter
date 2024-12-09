package com.lessons.samocounter.main.classhelpers;

import com.lessons.samocounter.VariableData;

import java.util.ArrayList;

public class FilterRemove {
    public static String[] checkboxStateInfo;
    public static String removeString;
    public static String removeString1;
    public static String[] arr;

    public static void getFiltret(ArrayList<String>arrayListSimFull, ArrayList<String>arrayListSim,int position ){
        final VariableData variableData = new VariableData();
        String positionString = arrayListSimFull.get(position);
        arr = positionString.split(" ");
        if (arr[1].equals("EASY") || arr[1].equals("NORM") || arr[1].equals("HARD")){
            arr[1] = "111111111111";
        }
        String binaryCheckboxState = arr[1];
        checkboxStateInfo = binaryCheckboxState.split("");

        removeString = arrayListSimFull.get(position);
        removeString1 = arrayListSim.get(position);
        removeString1 = removeString1.replaceAll(variableData.getDateText(), "");
    }

}
