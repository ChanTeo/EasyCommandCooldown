package me.easyplugins.commandcooldown;

import me.easyplugins.commandcooldown.handle.PlayerCooldown;
import me.easyplugins.commandcooldown.listener.PlayerPreCommandListener;
import me.easyplugins.commandcooldown.metric.Metrics;
import org.bukkit.Bukkit;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public class Main extends JavaPlugin {

    public static Main PLUGIN;
    public static Set<PlayerCooldown> COOLDOWNS;
    private EasyConfig config;
    private final int PluginID = 7163;

    @Override
    public void onEnable() {
        PLUGIN = this;
        config = new EasyConfig();
        config.init();
        System.out.println(config.getPluginEnabled());
        Metrics metrics = new Metrics(this,PluginID);
    }

    public EasyConfig getMainConfig() {
        return config;
    }

    private void registerListener(){
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerPreCommandListener(), this);
    }


}
