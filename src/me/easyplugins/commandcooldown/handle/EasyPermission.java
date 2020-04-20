package me.easyplugins.commandcooldown.handle;

import me.easyplugins.commandcooldown.Main;

public class EasyPermission {

    public static String get(String identifier){
        return Main.PLUGIN.getConfig().getConfigurationSection("permission").getString(identifier);
    }

}
