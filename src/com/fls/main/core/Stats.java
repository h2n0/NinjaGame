package com.fls.main.core;

public class Stats {

    public static Stats instance = new Stats();

    public int deaths = 0;
    public int jumps = 0;
    public int time = 0;

    public static void reset() {
        Stats.instance = new Stats();
    }

    public String getTimeString() {
        int seconds = time / 60;
        int minutes = seconds / 60;
        seconds %= 60;
        String str = minutes + ":";
        if (seconds < 10) str += "0";
        str += seconds;
        return str;
    }
}
