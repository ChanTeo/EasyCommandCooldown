package me.easyplugins.commandcooldown.util;

import me.easyplugins.commandcooldown.enumerator.EasyTimeFormat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;

public class EasyUtil {

    /**
     * @param message Text to format (include %time% or %timeformatted%)
     * @param timeinMillis Time (Duration) in milliseconds
     * @param timeFormat Rounded down to each Format. HMS for full format.
     * @return
     */
    public static String formatTime(String message, long timeinMillis, EasyTimeFormat timeFormat){
        switch(timeFormat){
            case MILLIS:
                return message.replace("%time%",timeinMillis + " ms").replace("%timeformatted%",timeinMillis+" ms");
            case SECONDS:
                return message.replace("%time%",getMulti(timeinMillis/1000,"sec","sec",false)).replace("%timeformatted%",getMulti(timeinMillis%1000,"sec","sec",false));
            case MINUTES:
                return message.replace("%time%",getMulti((timeinMillis/1000)/60,"min","mins",false)).replace("%timeformatted%",getMulti((timeinMillis%1000)%60,"min","mins",false));
            case HOURS:
                return message.replace("%time%",getMulti((timeinMillis/1000)/3600,"hr","hrs",false)).replace("%timeformatted%",getMulti((timeinMillis%1000)%3600,"hr","hrs",false));
            case HMS:
                if(message.contains("%timeformatted%")){
                    StringBuilder sb = new StringBuilder();
                    long[] times = getRemainingTimeFormatted(timeinMillis);
                    sb.append(getMulti(times[2],"hr","hrs",true));
                    if(times[2]>0) sb.append(" ");
                    sb.append(getMulti(times[1],"min","mins",true));
                    if(times[1]>0) sb.append(" ");
                    sb.append(getMulti(times[0],"sec","sec",false));
                    return message.replace("%timeformatted%",sb.toString());
                }
                return formatTime(message,timeinMillis,EasyTimeFormat.SECONDS);
        }


        return "";
    }

    public static String colorize(String text){
        return ChatColor.translateAlternateColorCodes('&',text);
    }


    private static long[] getRemainingTimeFormatted(long timeinMillis) {

        long[] times = new long[3];

        long duration = timeinMillis/1000;
        long hours = (duration / 3600);
        duration = duration - (hours * 3600);
        long minutes = (duration / 60);
        long seconds = duration - (minutes * 60);

        times[0] = seconds;
        times[1] = minutes;
        times[2] = hours;

        return times;
    }

    private static String getMulti(long value, String single, String multi, boolean blankZeo){
        if(value == 0 && blankZeo) return "";
        if(value > 1) return value + " " + multi;
        return value + " " + single;
    }

    public static void registerCommand(EasyCommand easyCommand){
        try{
            Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register(easyCommand.getName(), easyCommand);

        }catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e){
            e.printStackTrace();
        }
    }

}
