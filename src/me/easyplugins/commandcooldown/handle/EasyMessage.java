package me.easyplugins.commandcooldown.handle;

import me.easyplugins.commandcooldown.Main;
import me.easyplugins.commandcooldown.util.EasyUtil;
import org.bukkit.configuration.ConfigurationSection;

public class EasyMessage {

    private String identifier;
    private String message;

    public EasyMessage(String identifier){
        this.identifier = identifier;
        this.message = getSection().getString(identifier);
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getMessage() {
        return EasyUtil.colorize(message);
    }

    public static ConfigurationSection getSection(){
        return Main.PLUGIN.getConfig().getConfigurationSection("message");
    }

}
