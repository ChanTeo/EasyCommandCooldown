package me.easyplugins.commandcooldown;

import me.easyplugins.commandcooldown.enumerator.EasyMessage;
import me.easyplugins.commandcooldown.handle.CooldownCommand;
import me.easyplugins.commandcooldown.util.EasyUtil;
import java.io.File;
import java.util.*;

public class EasyConfig {

    private final Set<CooldownCommand> commands = new HashSet<>();
    private final Map<EasyMessage, String> configMessages = new HashMap<>();
    private Boolean useStatistics;

    //Internal messages for console
    private final String internalPrefix = "&2Easy&7Command &2>> &f";
    private final String commandsLoaded = internalPrefix +"{commands} have been loaded";
    private final String reloadConfig = internalPrefix + "&7Reloading config.yml...";
    private final String configNotFound = internalPrefix + "&7Config not found. Creating Default...";
    private final String pluginEnabled = internalPrefix
            + "&7created by&2 " + Main.PLUGIN.getDescription().getAuthors().stream().findFirst()
            + " &7version &2"+Main.PLUGIN.getDescription().getVersion()
            + "&7, enabled Enjoy!";
    private final String commandUsage = internalPrefix + "&7Command Usage: &2/easycc reload&f";

    public void init(){
        Main.PLUGIN.getConfig().options().copyDefaults(true);
        useStatistics = Main.PLUGIN.getConfig().getBoolean("general.usestatistics",true);
        Main.PLUGIN.saveConfig();
        commands.clear();
        loadCollections();
    }

    public void reload(){
        System.out.println(reloadConfig);
        File file = new File(Main.PLUGIN.getDataFolder() + "/config.yml");
        if (!file.exists()) {
            System.out.print(configNotFound);
            Main.PLUGIN.saveDefaultConfig();

        }
        Main.PLUGIN.reloadConfig();
        init();
        System.out.print("&7Done!");
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
        if(Main.PLUGIN.getConfig().getConfigurationSection("command")!=null){
            Main.PLUGIN.getConfig().getConfigurationSection("command").getKeys(false).forEach(command->{
                commands.add(new CooldownCommand(command));
            });
        }

        if(Main.PLUGIN.getConfig().getConfigurationSection("message") != null){
            Arrays.stream(EasyMessage.values()).forEach(easyMessage-> this.configMessages.put(easyMessage,Main.PLUGIN.getConfig().getString(easyMessage.getIndex(),easyMessage.getDefault())));
        }else{
            System.out.println();
        }
    }

    public String getInternalPrefix() {
        return EasyUtil.colorize(internalPrefix);
    }

    String getPluginEnabled() {
        return EasyUtil.colorize(pluginEnabled);
    }

    public String getCommandUsage() {
        return EasyUtil.colorize(commandUsage);
    }

    public String getCommandsLoaded() {
        return EasyUtil.colorize(commandsLoaded);
    }

    public Boolean getUseStatistics() {
        return useStatistics;
    }
}
