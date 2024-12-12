
package com.lessons.samocounter;

import android.content.Context;
import android.content.SharedPreferences;

public class GlobalDataHolder {

    private static final String PREFERENCES_NAME = "MyAppPreferences";
    private SharedPreferences sharedPreferences;

    public GlobalDataHolder(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public void saveData(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getSavedData(String key) {
        return sharedPreferences.getString(key, null);
    }
}