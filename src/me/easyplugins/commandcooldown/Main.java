package me.easyplugins.commandcooldown;

import me.easyplugins.commandcooldown.command.BaseCommand;
import me.easyplugins.commandcooldown.handle.PlayerCooldown;
import me.easyplugins.commandcooldown.listener.PlayerPreCommandListener;
import me.easyplugins.commandcooldown.metric.Metrics;
import me.easyplugins.commandcooldown.util.EasyUtil;
import org.bukkit.Bukkit;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    public static Main PLUGIN;
    public static List<PlayerCooldown> COOLDOWNS = new ArrayList<>();
    private EasyConfig config;

    @Override
    public void onEnable() {
        PLUGIN = this;
        config = new EasyConfig();
        config.init();
        Bukkit.getConsoleSender().sendMessage(EasyUtil.colorize(config.getPluginEnabled()));
        if(config.getUseStatistics()) new Metrics(this);
        registerListener();
        registerCommands();
    }

    public EasyConfig getMainConfig() {
        return config;
    }

    private void registerListener(){
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerPreCommandListener(), this);
    }

    private void registerCommands(){
        EasyUtil.registerCommand(new BaseCommand());
    }


}
