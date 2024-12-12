package com.lessons.samocounter.money.classhelper.lists;

import java.util.ArrayList;
import java.util.Collections;

public class ArrayListForSpinner {
    public static class Start {
        private static ArrayList<String> arrayListForSpinnerStart = new ArrayList<>();

        public static ArrayList<String> getArray(){
            return arrayListForSpinnerStart;
        }

        public static void addData(String addString){
            String[] ari = addString.split("-");
            arrayListForSpinnerStart.add(ari[1]);
        }

    }

    public static class Finish {
        private static ArrayList<String> arrayListForSpinnerFinish;

        public static ArrayList<String> getArray(){
            return arrayListForSpinnerFinish;
        }

        public static void copyAndReverse(ArrayList<String> arrayListForSpinnerStart){
            arrayListForSpinnerFinish = new ArrayList<>(arrayListForSpinnerStart);
            Collections.reverse(arrayListForSpinnerFinish);
        }
    }
}
