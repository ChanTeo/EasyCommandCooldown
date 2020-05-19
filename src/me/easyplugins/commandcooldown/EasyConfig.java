package me.easyplugins.commandcooldown;

import me.easyplugins.commandcooldown.handle.CooldownCommand;
import me.easyplugins.commandcooldown.util.EasyUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;
import java.util.*;

public class EasyConfig {

    private final Set<CooldownCommand> commands = new HashSet<>();
    private Boolean useStatistics;
    private Map<String, String> messages = new HashMap<>();
    private Map<String, String> permissions = new HashMap();

    //Internal messages for console
    private final String internalPrefix = "&7[&2Easy&7CC]&7";
    private final String commandsLoaded = internalPrefix +"%commands% have been loaded";
    private final String reloadConfig = internalPrefix + "&7Reloading config.yml...";
    private final String configNotFound = internalPrefix + "&7Config not found. Creating Default...";
    private final String pluginEnabled = internalPrefix + " &7version &2"+ EasyCommandCooldown.PLUGIN.getDescription().getVersion() + "&7 enabled.";

    public void init(){
        EasyCommandCooldown.PLUGIN.getConfig().options().copyDefaults(true);
        useStatistics = EasyCommandCooldown.PLUGIN.getConfig().getBoolean("general.usestatistics",true);
        commands.clear();
        EasyCommandCooldown.PLUGIN.saveConfig();
        loadCollections();
    }

    public void reload(){
        Bukkit.getConsoleSender().sendMessage(EasyUtil.colorize(reloadConfig));
        File file = new File(EasyCommandCooldown.PLUGIN.getDataFolder() + "/config.yml");
        if (!file.exists()) {
            Bukkit.getConsoleSender().sendMessage(EasyUtil.colorize(configNotFound));
            EasyCommandCooldown.PLUGIN.saveDefaultConfig();
        }
        EasyCommandCooldown.PLUGIN.reloadConfig();
        init();
        Bukkit.getConsoleSender().sendMessage(EasyUtil.colorize(internalPrefix+"Done!"));
    }

    public void save(){
        EasyCommandCooldown.PLUGIN.saveConfig();
    }

    public Set<CooldownCommand> getCommands() {
        return commands;
    }

    /**
     * Mapping configsections into colleactions
     */
    private void loadCollections(){
        //Commands
        ConfigurationSection commandSection = EasyCommandCooldown.PLUGIN.getConfig().getConfigurationSection("command");
        if(commandSection != null) commandSection.getKeys(false).forEach(command-> commands.add(new CooldownCommand(command)));

        //Messages
        ConfigurationSection messageSection = EasyCommandCooldown.PLUGIN.getConfig().getConfigurationSection("message");
        if(messageSection != null) messageSection.getKeys(false).forEach(message->messages.put(message,messageSection.getString(message)));

        //Permissions
        ConfigurationSection permissionSection = EasyCommandCooldown.PLUGIN.getConfig().getConfigurationSection("permission");
        if(permissionSection != null) permissionSection.getKeys(false).forEach(permission->permissions.put(permission,permissionSection.getString(permission)));
    }

    public String getInternalPrefix() {
        return EasyUtil.colorize(internalPrefix);
    }

    public String getPluginEnabled() {
        return pluginEnabled;
    }

    public String getCommandsLoaded() {
        return commandsLoaded;
    }

    public Boolean getUseStatistics() {
        return useStatistics;
    }

    public String getPermission(String identifier){
        return permissions.get(identifier);
    }

    public String getMessage(String identifier){
        String message = messages.get(identifier);
        return message != null ? EasyUtil.colorize(message) : "";
    }

}
