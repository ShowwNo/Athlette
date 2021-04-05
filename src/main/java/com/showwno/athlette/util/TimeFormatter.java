package com.showwno.athlette.util;

public class TimeFormatter {
    public static String format(long millis) {
        long minute = (millis / (1000 * 60));
        long second = (millis / 1000)  % 60;
        long millisSec = millis % 100;
        return minute+"分 "+ second +"."+ millisSec +"秒";
    }
}