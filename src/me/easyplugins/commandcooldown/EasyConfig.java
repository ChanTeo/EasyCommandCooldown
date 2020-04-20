package me.easyplugins.commandcooldown;

import me.easyplugins.commandcooldown.handle.CooldownCommand;
import me.easyplugins.commandcooldown.handle.EasyMessage;
import me.easyplugins.commandcooldown.util.EasyUtil;
import org.bukkit.Bukkit;

import java.io.File;
import java.util.*;

public class EasyConfig {

    private final Set<CooldownCommand> commands = new HashSet<>();
    private List<EasyMessage> messages = new ArrayList<>();
    private Boolean useStatistics;
    private int lastestExecution = 0;

    //Internal messages for console
    private final String internalPrefix = "&7[&2Easy&7CC]&7";
    private final String commandsLoaded = internalPrefix +"%commands% have been loaded";
    private final String reloadConfig = internalPrefix + "&7Reloading config.yml...";
    private final String configNotFound = internalPrefix + "&7Config not found. Creating Default...";
    private final String pluginEnabled = internalPrefix + " &7version &2"+Main.PLUGIN.getDescription().getVersion() + "&7 enabled.";
    private final String commandUsage = internalPrefix + "§7Command Usage: §2/easycc reload§f";

    public void init(){
        Main.PLUGIN.getConfig().options().copyDefaults(true);
        useStatistics = Main.PLUGIN.getConfig().getBoolean("general.usestatistics",true);
        commands.clear();
        messages.clear();
        Main.PLUGIN.saveConfig();
        loadCollections();
        lastestExecution = commands.stream().map(CooldownCommand::getExecution).mapToInt(Integer::parseInt).summaryStatistics().getMax();
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

    public List<EasyMessage> getMessages() {
        return messages;
    }

    public String getMessage(String ident){
        EasyMessage easyMessage = messages.stream().filter(message->message.getIdentifier().equals(ident)).findFirst().orElse(null);
        return easyMessage == null ? "" : easyMessage.getMessage();
    }

    private void loadCollections(){
        if(EasyMessage.getSection()!=null){
            EasyMessage.getSection().getKeys(false).forEach(key->{
                this.messages.add(new EasyMessage(key));
            });
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

    public int getLastestExecution() {
        return lastestExecution;
    }
}
