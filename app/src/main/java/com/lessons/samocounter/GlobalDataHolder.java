//
//package com.lessons.samocounter;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//
//public class GlobalDataHolder {
//
//    private static final String PREFERENCES_NAME = "MyAppPreferences";
//    private SharedPreferences sharedPreferences;
//
//
//    public GlobalDataHolder(Context context) {
//        this.sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
//    }
//
//    public void saveData(String key, boolean value) {
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean(key, value);
//        editor.apply();
//    }
//
//    public boolean getSavedData(String key) {
//        return sharedPreferences.getBoolean(key, false);
//    }
//}