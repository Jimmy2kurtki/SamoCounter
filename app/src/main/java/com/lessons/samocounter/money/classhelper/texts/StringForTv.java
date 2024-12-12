package com.lessons.samocounter.money.classhelper.texts;

import com.lessons.samocounter.MoneyCount;
import com.lessons.samocounter.main.classhelpers.HoursClass;

import java.util.ArrayList;

public class StringForTv {

    public static void initAll(int startDate,int finishDate){
        TotalMoney.init(startDate, finishDate);
        AvarageMoney.init();
        TotalHours.init();
    }

    public static class TotalMoney{
        private static String totalMoney;
        private static int pickedDatesSize;
        private static String totalHours;

        public static void init(int startDate, int finishDate){
            if (startDate <= finishDate) {
                ArrayList<String> pickedDates = new ArrayList<>(ArrayListForTv.getArray()
                        .subList(startDate, finishDate));
                if (pickedDates.size() > 1) {

                    ArrayList<Integer> pickedMoney = new ArrayList<>();

                    StringBuilder stringBuilder = new StringBuilder(totalHours);

                    for (int i = 0; i < pickedDates.size(); i++) {
                        String[] ar = pickedDates.get(i).split("-");

                        if (i == pickedDates.size()-1){
                            stringBuilder.append(ar[0]);
                        } else {
                            stringBuilder.append(ar[0]).append("=");
                        }
                        pickedMoney.add(Integer.valueOf(MoneyCount.Companion.moneyCountHours(ar[0])));
                    }

                    totalHours = stringBuilder.toString();

                    for (int i = 0; i < pickedMoney.size(); i++) {
                        totalMoney = String.valueOf(Integer.parseInt(totalMoney) + pickedMoney.get(i));
                    }
                } else {
                    if (pickedDates.size() == 1) {
                        String[] ar = pickedDates.get(0).split("_");
                        String[] ari = ar[0].split("-");
                        totalHours = ari[0];
                        totalMoney = String.valueOf(Integer.parseInt(MoneyCount.Companion.moneyCountHours(ari[0])));
                    }
                    if (startDate == finishDate) {
                        String[] ar = ArrayListForTv.getArray().get(startDate).split("_");
                        String[] ari = ar[0].split("-");
                        totalHours = ari[0];
                        totalMoney = String.valueOf(Integer.parseInt(MoneyCount.Companion.moneyCountHours(ari[0])));
                    }
                }
                pickedDatesSize = finishDate - startDate + 1;
            }
        }

        public static String getHoursAll(){
            return totalHours;
        }

        public static int getPickedDatesSize(){
            return pickedDatesSize;
        }

        public static String getTotalMoney(){
            return totalMoney;
        }
    }

    public static class AvarageMoney {
        private static int avarageMoney;

        public static void init(){
            avarageMoney = Integer.parseInt(TotalMoney.getTotalMoney()) / TotalMoney.getPickedDatesSize();
        }

        public static String getAvarageMoney(){
            return String.valueOf(avarageMoney);
        }
    }

    public static class TotalHours{
        private static int totalHours;

        public static void init(){
            String[] ar = TotalMoney.getHoursAll().split("=");

            String resultString = "";
            for (int i = 0; i < ar.length; i++) {
                resultString = addHours(resultString, ar[i]);
            }
            totalHours = HoursClass.convertFromHourToInt(resultString);

        }

        public static String addHours(String first, String second){

            int intResult = HoursClass.convertFromHourToInt(first) + HoursClass.convertFromHourToInt(second);

            return HoursClass.convertFromIntToHour(intResult);
        }


        public static String getTotalHours(){
            return HoursClass.convertFromIntToHour(totalHours);
        }
    }
}
