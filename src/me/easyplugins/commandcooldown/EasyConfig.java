package me.easyplugins.commandcooldown;

import me.easyplugins.commandcooldown.enumerator.EasyMessage;
import me.easyplugins.commandcooldown.handle.CooldownCommand;
import me.easyplugins.commandcooldown.util.EasyUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;
import java.sql.SQLOutput;
import java.util.*;

public class EasyConfig {

    private final Set<CooldownCommand> commands = new HashSet<>();
    private Map<EasyMessage, String> configMessages = new HashMap<>();
    private Boolean useStatistics;

    //Internal messages for console
    private final String internalPrefix = "&7[&2Easy&7CC]&7";
    private final String commandsLoaded = internalPrefix +"%commands% have been loaded";
    private final String reloadConfig = internalPrefix + "&7Reloading config.yml...";
    private final String configNotFound = internalPrefix + "&7Config not found. Creating Default...";
    private final String pluginEnabled = internalPrefix
            + "&7created by&2 ChanTeo"
            + " &7version &2"+Main.PLUGIN.getDescription().getVersion()
            + "&7, enabled Enjoy!";
    private final String commandUsage = internalPrefix + "§7Command Usage: §2/easycc reload§f";

    public void init(){
        Main.PLUGIN.getConfig().options().copyDefaults(true);
        useStatistics = Main.PLUGIN.getConfig().getBoolean("general.usestatistics",true);
        commands.clear();
        configMessages.clear();
        Main.PLUGIN.saveConfig();
        loadCollections();
    }

    public void reload(){
        Bukkit.getConsoleSender().sendMessage(EasyUtil.colorize(reloadConfig));
        File file = new File(Main.PLUGIN.getDataFolder() + "/config.yml");
        if (!file.exists()) {
            Bukkit.getConsoleSender().sendMessage(EasyUtil.colorize(configNotFound));
            Main.PLUGIN.saveDefaultConfig();
        }
        Main.PLUGIN.reloadConfig();
        init();
        Bukkit.getConsoleSender().sendMessage(EasyUtil.colorize(internalPrefix+"Done!"));
    }

    public void save(){
        Main.PLUGIN.saveConfig();
    }

    public Set<CooldownCommand> getCommands() {
        return commands;
    }

    public Map<EasyMessage, String> getConfigMessages() {
        return configMessages;
    }

    public String getMessage(EasyMessage message){
        return EasyUtil.colorize(configMessages.get(message));
    }

    private void loadCollections(){
        ConfigurationSection messageSection = Main.PLUGIN.getConfig().getConfigurationSection("message");
        if (messageSection != null) {
            for (EasyMessage message : EasyMessage.values()) {
                this.configMessages.put(message, messageSection.getString(message.getIndex(), message.getDefault()));
            }
        }


        if(Main.PLUGIN.getConfig().getConfigurationSection("command")!=null){
            Main.PLUGIN.getConfig().getConfigurationSection("command").getKeys(false).forEach(command->{
                commands.add(new CooldownCommand(command));
            });
        }


    }

    public String getInternalPrefix() {
        return EasyUtil.colorize(internalPrefix);
    }

    String getPluginEnabled() {
        return pluginEnabled;
    }

    public String getCommandUsage() {
        return commandUsage;
    }

    public String getCommandsLoaded() {
        return commandsLoaded;
    }

    public Boolean getUseStatistics() {
        return useStatistics;
    }
}
