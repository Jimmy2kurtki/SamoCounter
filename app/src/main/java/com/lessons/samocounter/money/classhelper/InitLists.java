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
                ArrayListForSpinner.Start.addData(string);
                ArrayListForTv.addData(string);
            }
            ArrayListForSpinner.Finish.copyAndReverse(ArrayListForSpinner.Start.getArray());
        }
    }
}
