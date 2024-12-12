package com.lessons.samocounter.money.classhelper.texts;

import java.util.ArrayList;

public class ArrayListForTv {
    private static ArrayList<String> arrayListForTv = new ArrayList<>();

    public static ArrayList<String> getArray(){
        return arrayListForTv;
    }

    public static void addData(String addString){
        arrayListForTv.add(addString);
    }
}
