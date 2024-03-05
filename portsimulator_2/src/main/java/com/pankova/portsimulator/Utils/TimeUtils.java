package com.pankova.portsimulator.Utils;

import java.util.concurrent.TimeUnit;

public class TimeUtils {
    
    public static final int MS_PER_HOUR = 60 * 60 * 1000;

    public static long[] convertMillisToDaysHoursMinutes(long millis) {
        long days = TimeUnit.MILLISECONDS.toDays(millis);
        long hours = TimeUnit.MILLISECONDS.toHours(millis) % 24;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60;
        return new long[] {days, hours, minutes, seconds};
    }

    public static String formatTime(long[] time) {
        return String.format("%02d:%02d:%02d", time[0] * 24 + time[1], time[2], time[3]);
    }
}
