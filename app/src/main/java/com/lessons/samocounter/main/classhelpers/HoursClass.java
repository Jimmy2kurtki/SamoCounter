package com.lessons.samocounter.main.classhelpers;

public class HoursClass {
    public static String convertFromIntToHour(int minutes) {
        int hours = minutes / 60;
        int remainingMinutes = minutes % 60;

        return hours + ":" + (remainingMinutes < 10 ? "0" : "") + remainingMinutes;
    }

    public static int convertFromHourToInt(String time) {
        if (time.equals("")){
            return 0;
        } else {
            String[] parts = time.split(":");
            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);

            return hours * 60 + minutes;
        }
    }

    public static String addHours(String startTime) {
        int totalMinutes = convertFromHourToInt(startTime) + 15;
        if (totalMinutes > 9*60){
            totalMinutes = 9*60;
        }
        return convertFromIntToHour(totalMinutes);
    }

    public static String subtractHours(String startTime) {
        int totalMinutes = convertFromHourToInt(startTime) - 15;
        if (totalMinutes < 0) {
            totalMinutes += 24 * 60;
        }
        return convertFromIntToHour(totalMinutes);
    }
}