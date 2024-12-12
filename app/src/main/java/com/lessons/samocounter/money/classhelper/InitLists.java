package com.lessons.samocounter.money.classhelper;

import static com.lessons.samocounter.MyConstKt.KEY_HOURS;

import android.content.Context;

import com.lessons.samocounter.GlobalDataHolder;
import com.lessons.samocounter.money.classhelper.lists.ArrayListForSpinner;
import com.lessons.samocounter.money.classhelper.texts.ArrayListForTv;

public class InitLists {
    public static void initAll(Context context){
        GlobalDataHolder globalDataHolder = new GlobalDataHolder(context);
        if(globalDataHolder.getSavedData(KEY_HOURS) != null) {
            String[] ar = globalDataHolder.getSavedData(KEY_HOURS).split("_");
            for (String string : ar) {
                String string0 = new String(string);
                ArrayListForSpinner.Start start = new ArrayListForSpinner.Start();
                start.addData(string0);

                String string1 = new String(string);
                ArrayListForTv arrayListForTv = new ArrayListForTv();
                arrayListForTv.addData(string1);
            }
        }
    }
}
