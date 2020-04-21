package me.easyplugins.commandcooldown;

import me.easyplugins.commandcooldown.handle.CooldownCommand;
import me.easyplugins.commandcooldown.util.EasyUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EasyConfig {

    private final Set<CooldownCommand> commands = new HashSet<>();
    private Boolean useStatistics;
    private int latestExecution = 0;
    private Map<String, String> messages = new HashMap<>();
    private Map<String, String> permissions = new HashMap();

    //Internal messages for console
    private final String internalPrefix = "&7[&2Easy&7CC]&7";
    private final String commandsLoaded = internalPrefix +"%commands% have been loaded";
    private final String reloadConfig = internalPrefix + "&7Reloading config.yml...";
    private final String configNotFound = internalPrefix + "&7Config not found. Creating Default...";
    private final String pluginEnabled = internalPrefix + " &7version &2"+Main.PLUGIN.getDescription().getVersion() + "&7 enabled.";

    public void init(){
        Main.PLUGIN.getConfig().options().copyDefaults(true);
        useStatistics = Main.PLUGIN.getConfig().getBoolean("general.usestatistics",true);
        commands.clear();
        Main.PLUGIN.saveConfig();
        loadCollections();
        latestExecution = commands.stream().map(CooldownCommand::getExecution).mapToInt(Integer::parseInt).summaryStatistics().getMax();
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


    private void loadCollections(){

        //Commands
        ConfigurationSection commandSection = Main.PLUGIN.getConfig().getConfigurationSection("command");
        if(commandSection != null) commandSection.getKeys(false).forEach(command-> commands.add(new CooldownCommand(command)));

        //Messages
        ConfigurationSection messageSection = Main.PLUGIN.getConfig().getConfigurationSection("message");
        if(messageSection != null) messageSection.getKeys(false).forEach(message->messages.put(message,messageSection.getString(message)));

        //Permissions
        ConfigurationSection permissionSection = Main.PLUGIN.getConfig().getConfigurationSection("permission");
        if(permissionSection != null) permissionSection.getKeys(false).forEach(permission->messages.put(permission,messageSection.getString(permission)));

    }

    public String getInternalPrefix() {
        return EasyUtil.colorize(internalPrefix);
    }

    String getPluginEnabled() {
        return pluginEnabled;
    }

    public String getCommandsLoaded() {
        return commandsLoaded;
    }

    public Boolean getUseStatistics() {
        return useStatistics;
    }

    public int getLatestExecution() {
        return latestExecution;
    }

    public void setLatestExecution(int latestExecution) {
        this.latestExecution = latestExecution;
    }

    public String getPermission(String identifier){
        return permissions.get(identifier);
    }

    public String getMessage(String identifier){
        String message = messages.get(identifier);
        return message != null ? EasyUtil.colorize(message) : "";
    }

}
