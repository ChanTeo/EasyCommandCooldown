package me.easyplugins.commandcooldown;

import me.easyplugins.commandcooldown.enumerator.EasyMessage;
import me.easyplugins.commandcooldown.handle.CooldownCommand;

import java.io.File;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class EasyConfig {

    private Set<CooldownCommand> commands;
    private Map<EasyMessage, String> configMessages;
    private Boolean useStatistics;

    //Internal messages for console
    private final String internalPrefix = "&2Easy&7Command &2>> &f";
    private final String commandsLoaded = "{commands} have been loaded";
    private final String reloadConfig = internalPrefix + "&7Reloading config.yml...";
    private final String configNotFound = internalPrefix + "&7Config not found. Creating Default...";
    private final String pluginEnabled = internalPrefix
            + "&7created by&2 " + Main.PLUGIN.getDescription().getAuthors().stream().findFirst()
            + " &7version &2"+Main.PLUGIN.getDescription().getVersion()
            + "&7, enabled Enjoy!";

    public void init(){
        Main.PLUGIN.getConfig().options().copyDefaults(true);
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
        return configMessages.get(message);
    }

    private void loadCollections(){
        if(Main.PLUGIN.getConfig().getConfigurationSection("command")!=null){
            Main.PLUGIN.getConfig().getConfigurationSection("command").getKeys(false).forEach(command->{
                commands.add(new CooldownCommand(command));
            });
        }

        if(Main.PLUGIN.getConfig().getConfigurationSection("message") != null){
            Arrays.stream(EasyMessage.values()).forEach(easyMessage->{
                this.configMessages.put(easyMessage,Main.PLUGIN.getConfig().getString(easyMessage.getIndex(),easyMessage.getDefault()));
            });
        }else{
            System.out.println();
        }
    }

    public String getInternalPrefix() {
        return internalPrefix;
    }

    public String getPluginEnabled() {
        return pluginEnabled;
    }
}
