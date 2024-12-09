package com.lessons.samocounter.main.classhelpers;

import com.lessons.samocounter.VariableData;

public class ClearNumberSim {
    public static String clear(String numberSim){

        VariableData variableData = new VariableData();
        numberSim = numberSim.toUpperCase();
        numberSim = numberSim.replaceAll("\\s+", "");
        numberSim = numberSim.replaceAll("EASY", "");
        numberSim = numberSim.replaceAll("NORM", "");
        numberSim = numberSim.replaceAll("HARD", "");
        numberSim = numberSim.replaceAll(variableData.getDateText(), "");
        if (numberSim.isEmpty()) {
            numberSim = "-----";
        }
        return numberSim;
    }
}
