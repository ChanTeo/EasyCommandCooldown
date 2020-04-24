package me.easyplugins.commandcooldown.handle;

import me.easyplugins.commandcooldown.EasyCommandCooldown;
import org.bukkit.configuration.ConfigurationSection;

import java.util.TreeMap;

public class CooldownCommand {

    private String identifier;
    private String bypass;
    private static TreeMap<Integer, String> cooldowns = new TreeMap<>();
    private int highestCooldown = 0;


    public CooldownCommand(String identifier){
        this.identifier = identifier;
        final ConfigurationSection commandSection = EasyCommandCooldown.PLUGIN.getConfig().getConfigurationSection("command." + identifier);
        final ConfigurationSection cooldownSection = commandSection.getConfigurationSection("cooldowns");
        bypass = commandSection.getString("bypass");
        cooldownSection.getKeys(false)
                .forEach(cooldown->{
                    if(Integer.parseInt(cooldown) > highestCooldown) highestCooldown = Integer.parseInt(cooldown);
                    cooldowns.put(Integer.valueOf(cooldown),cooldownSection.getString(cooldown));
                });
    }

    public CooldownCommand get(String identifier){
        return EasyCommandCooldown.PLUGIN.getMainConfig().getCommands().stream().filter(command->command.getIdentifier().equals(identifier)).findFirst().orElse(null);
    }


    public String getIdentifier() {
        return identifier;
    }

    public String getBypass() {
        return bypass;
    }

    public TreeMap<Integer, String> getCooldowns() {
        return cooldowns;
    }

    /**
     * Get the highest
     * @return
     */
    public int getHighestCooldown() {
        return highestCooldown;
    }

}
