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

            ArrayList<Integer> pickedMoney = new ArrayList<>();

            for (int i = 0; i < pickedDates.size(); i++) {
                String[] ar = pickedDates.get(i).split(" ");

                pickedMoney.add(Integer.valueOf(MoneyCount.Companion.moneyCountHours(ar[0])));
            }

            for (int i = 0; i < pickedMoney.size(); i++) {
                totalMoney = totalMoney + pickedMoney.get(i);
            }
        }
    }
}
