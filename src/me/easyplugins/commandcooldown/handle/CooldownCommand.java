package me.easyplugins.commandcooldown.handle;

import me.easyplugins.commandcooldown.Main;

import java.util.Map;
import java.util.Set;

public class CooldownCommand {

    private String identifier;
    private String execution;
    private String bypass;
    private Map<Integer, String> cooldowns;
    private int highestCooldown = 0;


    public CooldownCommand(String identifier){
        this.identifier = identifier;
        execution = Main.PLUGIN.getConfig().getString("command."+identifier+".execution");
        Main.PLUGIN.getConfig().getConfigurationSection("command."+identifier+".cooldowns").getKeys(false)
                .forEach(cooldown->{
                    if(Integer.parseInt(cooldown) > highestCooldown) highestCooldown = Integer.parseInt(cooldown);
                    cooldowns.put(Integer.valueOf(cooldown),Main.PLUGIN.getConfig().getString("command."+identifier+".cooldowns."+cooldown));
                });
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getExecution() {
        return execution;
    }

    public String getBypass() {
        return bypass;
    }

    public Map<Integer, String> getCooldowns() {
        return cooldowns;
    }

    public int getHighestCooldown() {
        return highestCooldown;
    }
}
