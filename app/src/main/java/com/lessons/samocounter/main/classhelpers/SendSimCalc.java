package com.lessons.samocounter.main.classhelpers;

import java.util.ArrayList;
import java.util.Arrays;

public class SendSimCalc {
    public static String sendSim = "" + FilterRemove.arr[0] + "\n";
    public static void calc(String[] checkboxArray){
        ArrayList<String> arrayListForSEND = new ArrayList<>();
        arrayListForSEND.addAll(Arrays.asList(checkboxArray));
        for(int i = 0; i < arrayListForSEND.size(); i++){
            if(FilterRemove.checkboxStateInfo[i].equals("1")){
                sendSim = sendSim + checkboxArray[i].replaceAll("\n", " ") + "\n";
            }
        }
    }
}
