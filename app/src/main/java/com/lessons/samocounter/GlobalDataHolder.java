package com.lessons.samocounter;

import android.content.Context;
import android.content.SharedPreferences;

public class GlobalDataHolder {
    // Хранение экземпляра SharedPreferences
    private static final String PREFERENCES_NAME = "MyAppPreferences";
    private SharedPreferences sharedPreferences;

    // Конструктор, принимающий Context
    public GlobalDataHolder(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    // Метод для сохранения данных
    public void saveData(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply(); // Или editor.commit() для синхронного сохранения
    }

    // Метод для получения сохраненных данных
    public boolean getSavedData(String key) {
        return sharedPreferences.getBoolean(key, false); // По умолчанию возвращаем false, если ключ отсутствует
    }
}