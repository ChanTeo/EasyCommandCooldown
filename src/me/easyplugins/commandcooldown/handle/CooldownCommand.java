package me.easyplugins.commandcooldown.handle;

import me.easyplugins.commandcooldown.Main;
import org.bukkit.configuration.ConfigurationSection;

import java.util.TreeMap;

public class CooldownCommand {

    private String identifier;
    private String execution;
    private String bypass;
    private static TreeMap<Integer, String> cooldowns = new TreeMap<>();
    private int highestCooldown = 0;
    private ConfigurationSection commandSection;
    private ConfigurationSection cooldownSection;


    public CooldownCommand(String identifier){
        this.identifier = identifier;
        this.commandSection = Main.PLUGIN.getConfig().getConfigurationSection("command."+identifier);
        this.cooldownSection = commandSection.getConfigurationSection("cooldowns");
        execution = commandSection.getString("execution");
        bypass = commandSection.getString("bypass");
        cooldownSection.getKeys(false)
                .forEach(cooldown->{
                    if(Integer.parseInt(cooldown) > highestCooldown) highestCooldown = Integer.parseInt(cooldown);
                    cooldowns.put(Integer.valueOf(cooldown),cooldownSection.getString(cooldown));
                });
    }

    public static CooldownCommand get(String identifier){
        return Main.PLUGIN.getMainConfig().getCommands().stream().filter(command->command.getIdentifier().equals(identifier)).findFirst().orElse(null);
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

    public TreeMap<Integer, String> getCooldowns() {
        return cooldowns;
    }

    public int getHighestCooldown() {
        return highestCooldown;
    }

    public ConfigurationSection getCommandSection() {
        return commandSection;
    }

    public ConfigurationSection getCooldownSection() {
        return cooldownSection;
    }

    public static ConfigurationSection getCommandSection(String identifier){
        return Main.PLUGIN.getConfig().getConfigurationSection("command."+identifier);
    }

}
