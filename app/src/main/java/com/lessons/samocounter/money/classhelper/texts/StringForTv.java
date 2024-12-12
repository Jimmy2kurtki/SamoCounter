package com.lessons.samocounter.money.classhelper.texts;

import com.lessons.samocounter.MoneyCount;
import com.lessons.samocounter.money.classhelper.lists.ArrayListForSpinner;

import java.util.ArrayList;

public class StringForTv {
    public static class TotalMoney{
        private static String totalMoney;

        public static void init(int startDate, int finishDate){
            ArrayList<String> pickedDates = new ArrayList<>(ArrayListForTv.getArray()
                    .subList(startDate,finishDate));
            if(pickedDates.size() > 1) {

                ArrayList<Integer> pickedMoney = new ArrayList<>();

                for (int i = 0; i < pickedDates.size(); i++) {
                    String[] ar = pickedDates.get(i).split("-");

                    pickedMoney.add(Integer.valueOf(MoneyCount.Companion.moneyCountHours(ar[0])));
                }

                for (int i = 0; i < pickedMoney.size(); i++) {
                    totalMoney = String.valueOf(Integer.parseInt(totalMoney) + pickedMoney.get(i));
                }
            } else {
                if(pickedDates.size() == 1) {
                    String[] ar = pickedDates.get(0).split("_");
                    String[] ari = ar[0].split("-");
                    totalMoney = String.valueOf(Integer.parseInt(MoneyCount.Companion.moneyCountHours(ari[0])));
                }
                if(startDate == finishDate){
                    String[] ar = ArrayListForTv.getArray().get(startDate).split("_");
                    String[] ari = ar[0].split("-");
                    totalMoney = String.valueOf(Integer.parseInt(MoneyCount.Companion.moneyCountHours(ari[0])));
                }
            }
        }

        public static String getTotalMoney(){
            return totalMoney;
        }
    }
}
