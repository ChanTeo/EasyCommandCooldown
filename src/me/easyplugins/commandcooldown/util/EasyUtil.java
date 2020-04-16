package me.easyplugins.commandcooldown.util;

import me.easyplugins.commandcooldown.enumerator.EasyTimeFormat;

public class EasyUtil {

    public static String formatTime(String message, long timeinMillis, EasyTimeFormat timeFormat){
        switch(timeFormat){
            case MILLIS:
                return message.replace("%time%",timeinMillis + " ms").replace("%timeformatted%",timeinMillis+" ms");
            case SECONDS:
                return message.replace("%time%",getMulti(timeinMillis/1000,"sec","sec")).replace("%timeformatted%",getMulti(timeinMillis%1000,"sec","sec"));
            case MINUTES:
                return message.replace("%time%",getMulti((timeinMillis/1000)/60,"min","mins")).replace("%timeformatted%",getMulti((timeinMillis%1000)%60,"min","mins"));
            case HOURS:
                return message.replace("%time%",getMulti((timeinMillis/1000)/3600,"hr","hrs")).replace("%timeformatted%",getMulti((timeinMillis%1000)%3600,"hr","hrs"));
            case HMS:
                if(message.contains("%timeformatted%")){
                    StringBuilder sb = new StringBuilder();
                    long[] times = getRemainingTimeFormatted(timeinMillis);
                    sb.append(getMulti(times[2],"hr","hrs"));
                    if(times[2]>0) sb.append(" ");
                    sb.append(getMulti(times[1],"min","mins"));
                    if(times[1]>0) sb.append(" ");
                    sb.append(getMulti(times[0],"sec","sec"));
                    return message.replace("%timeformatted%",sb.toString());
                }
                return formatTime(message,timeinMillis,EasyTimeFormat.SECONDS);
        }


        return "";
    }

    private static long[] getRemainingTimeFormatted(long timeinMillis) {

        long[] times = new long[3];

        long duration = timeinMillis;
        long hours = (duration / 3600);
        duration = duration - (hours * 3600);
        long minutes = (duration / 60);
        long seconds = duration - (minutes * 60);

        times[0] = seconds;
        times[1] = minutes;
        times[2] = hours;

        return times;
    }

    private static String getMulti(long value, String single, String multi){
        if(value == 0) return "";
        if(value > 1) return value + " " + multi;
        return value + " " + single;
    }

}
